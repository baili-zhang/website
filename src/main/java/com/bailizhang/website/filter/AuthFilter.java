package com.bailizhang.website.filter;

import com.bailizhang.lynxdb.client.connection.LynxDbConnection;
import com.bailizhang.website.entity.UserSession;
import com.bailizhang.website.result.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Component
@WebFilter(filterName = "AuthFilter", urlPatterns = "")
public class AuthFilter implements Filter {
    private final List<String> allowPattern = new ArrayList<>();
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private final LynxDbConnection lynxDbConnection;

    public AuthFilter(LynxDbConnection connection) {
        lynxDbConnection = connection;
    }

    public void init(FilterConfig config) {
        allowPattern.add("/");
        allowPattern.add("/login");
        allowPattern.add("/401");
        allowPattern.add("/static/*");
    }

    public void destroy() {

    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String uri = httpRequest.getRequestURI();
        if(allowPattern.stream().anyMatch(pattern -> pathMatcher.match(pattern, uri))) {
            chain.doFilter(request, response);
            return;
        }

        Cookie[] cookies = httpRequest.getCookies();
        UserSession userSession = new UserSession();
        for(Cookie cookie : cookies) {
            switch (cookie.getName()) {
                case UserSession.USERNAME_COOKIE -> userSession.setUsername(cookie.getValue());
                case UserSession.SESSION_ID_COOKIE -> userSession.setSessionId(cookie.getValue());
            }
        }

        String username = userSession.getUsername();
        String sessionId = userSession.getSessionId();

        if(StringUtils.hasText(username) && StringUtils.hasText(sessionId)) {
            UserSession findUserSession = lynxDbConnection.find(username, UserSession.class);
            if(userSession.equals(findUserSession)) {
                chain.doFilter(request, response);
                return;
            }
        }

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String contentType = httpRequest.getHeader("Content-Type");
        contentType = contentType == null ? "" : contentType;

        switch (contentType) {
            case "application/json" -> {
                httpResponse.setStatus(401);
                httpResponse.setHeader("Content-Type", "application/json");

                PrintWriter writer = httpResponse.getWriter();

                ObjectMapper mapper = new ObjectMapper();
                Result result = new Result(false);

                writer.print(mapper.writeValueAsString(result));
                writer.flush();
            }

            default -> httpResponse.sendRedirect("/401");
        }
    }
}
