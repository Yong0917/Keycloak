package com.test.keycloak.vo;

import java.util.List;

public class UserVO {

    private String choice;
    private String id;
    private String password;
    private String name;
    private String email;
    private String useEnable;
    private String regDate;
    private String firstName;
    private String lastName;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }


    public String getUseEnable() {
        return useEnable;
    }

    public void setUseEnable(String useEnable) {
        this.useEnable = useEnable;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public List<UserVO> getDeleteList() {
        return deleteList;
    }

    public void setDeleteList(List<UserVO> deleteList) {
        this.deleteList = deleteList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }
}
