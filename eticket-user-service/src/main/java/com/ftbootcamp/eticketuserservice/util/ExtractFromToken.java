package com.ftbootcamp.eticketuserservice.util;

import com.ftbootcamp.eticketuserservice.exception.ExceptionMessages;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExtractFromToken {

    private static final JwtUtil jwtUtil = new JwtUtil();

    public static String email(String token){
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        String email;
        try {
            email = jwtUtil.extractEmail(token);
        } catch (Exception e) {
            throw new RuntimeException(ExceptionMessages.CAN_NOT_ACCESS_THIS_PROFILE);
        }

        return email;
    }
}
