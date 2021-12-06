package com.test.keycloak.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class myInfoForm {

    @NotBlank           //빈값 허용 X
    private String id;

    @NotBlank           //빈값 허용 X
    private String firstName;

    @NotBlank           //빈값 허용 X
    private String lastName;

    @NotBlank           //빈값 허용 X
    @Email              //Email형식으로
    private String email;

}
