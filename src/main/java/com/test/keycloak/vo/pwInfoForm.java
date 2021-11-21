package com.test.keycloak.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class pwInfoForm {

    @NotBlank(message = "아이디는 필수값 입니다")
    private String id;

    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}",
            message = "8글자 이상, 16글자 이하, 특수문자 포함해야 됩니다")
    private String password;

}
