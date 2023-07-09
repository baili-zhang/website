package com.bailizhang.website.utils;

import java.util.UUID;

public interface IdUtils {
    static String generateId() {
        return System.currentTimeMillis() + UUID.randomUUID().toString();
    }
}
