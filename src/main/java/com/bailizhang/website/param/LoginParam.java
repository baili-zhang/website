package com.bailizhang.website.param;

import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class LoginParam {
    @NonNull
    private String username;
    @NonNull
    private String password;
}
