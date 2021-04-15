package com.telesoftas.onboarding.domain.librarian.model;

import com.telesoftas.onboarding.domain.librarian.validator.ProfileGroup;
import com.telesoftas.onboarding.domain.librarian.validator.RegistrationGroup;
import com.telesoftas.onboarding.domain.librarian.validator.UniqueEmail;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
public class Librarian {

    private Long id;

    @NonNull
    @NotBlank(message = "Firstname is required", groups = ProfileGroup.class)
    private String firstname;

    @NonNull
    @NotBlank(message = "Lastname is required", groups = ProfileGroup.class)
    private String lastname;

    @NonNull
    @NotBlank(message = "Email is required", groups = ProfileGroup.class)
    @Email(message = "Email is invalid", groups = ProfileGroup.class)
    @UniqueEmail(message = "Librarian with this email exists", groups = RegistrationGroup.class)
    private String email;

    @NonNull
    @Size(min = 8, message = "Password must be at least 8 characters", groups = ProfileGroup.class)
    private String password;

    private LocalDateTime timeCreated;

    private LocalDateTime timeModified;
}
