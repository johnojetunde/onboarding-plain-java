package com.telesoftas.onboarding.app.fixtures;

import com.telesoftas.onboarding.app.model.LoginRequestModel;

public class LoginRequestModelFixture {

    public static LoginRequestModel valid() {
        return LoginRequestModel.builder()
            .email("hello@hello.com")
            .password("password@password")
            .build();
    }

}
