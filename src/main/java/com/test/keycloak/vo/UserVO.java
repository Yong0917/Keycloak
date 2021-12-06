package com.test.keycloak.vo;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class UserVO {

    private String choice;

    @NotBlank       //빈 값 허용 X
    private String id;

    private String password;
    private String name;
    private String email;
    private String useEnable;
    private String regDate;
    private String firstName;
    private String lastName;

    @NotBlank       //빈 값 허용 X
    private List<UserVO> deleteList;

    private int StatusCode;
    private String Status;

    public UserVO(){

    }

    public UserVO(String id, String firstName,String lastName, String email, String useEnable,String regDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.useEnable = useEnable;
        this.regDate = regDate;
    }

    public UserVO(String id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

}
