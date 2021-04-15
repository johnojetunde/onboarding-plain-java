package com.telesoftas.onboarding.app.fixtures;

    import com.telesoftas.onboarding.app.model.LibrarianApiRequestModel;

public class LibrarianApiRequestModelFixture {

    public static LibrarianApiRequestModel valid(){
        return  LibrarianApiRequestModel.builder()
            .firstname("John")
            .lastname("Ojetunde")
            .email("hello@hello.com")
            .password("khdjfhd526789jjjdkkjoif")
            .build();
    }
}
