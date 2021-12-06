package com.test.keycloak.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class pwInfoForm {

    @NotBlank(message = "아이디는 필수값 입니다")     //빈값 허용 X 기본 Message 설정
    private String id;

    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}",        //정규식을 이용해서 제한가능
            message = "8글자 이상, 16글자 이하, 특수문자 포함해야 됩니다")     //기본메시지 설정
    private String password;

}
