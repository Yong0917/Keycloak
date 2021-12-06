package com.test.keycloak.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class userUpdateForm {

    @NotBlank           //빈 값 허용 X
    private String id;

    @NotBlank           //빈 값 허용 X
    private String firstName;

    @NotBlank           //빈 값 허용 X
    private String lastName;

    @NotBlank           //빈 값 허용 X
    @Email              //Email형식
    private String email;

    @NotBlank           //빈 값 허용 X
    private String useEnable;


}
