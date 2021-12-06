package com.test.keycloak.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class userCreateForm {

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

    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}",        //비밀번호 정규식
            message = "8글자 이상, 16글자 이하, 특수문자 포함해야 됩니다")     //기본메시지 설정
    private String password;

}
