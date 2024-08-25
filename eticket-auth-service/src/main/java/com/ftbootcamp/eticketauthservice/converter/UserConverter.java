package com.ftbootcamp.eticketauthservice.converter;

import com.ftbootcamp.eticketauthservice.entity.abstracts.User;
import com.ftbootcamp.eticketauthservice.model.CustomUser;

public class UserConverter {

    public static CustomUser toCustomUser(User user){
        CustomUser customUser = CustomUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();

        return customUser;
    }
}
