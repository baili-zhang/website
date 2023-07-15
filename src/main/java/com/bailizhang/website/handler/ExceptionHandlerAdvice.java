package com.bailizhang.website.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {
    @ExceptionHandler({ Throwable.class })
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView processException(Throwable throwable) {
        log.error("Catch exception: ", throwable);

        ModelAndView view = new ModelAndView();
        view.addObject("className", throwable.getMessage());
        view.setViewName("httpError/500");
        return view;
    }
}
