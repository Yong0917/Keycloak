package com.test.keycloak.controller;



import com.test.keycloak.api.KeycloakApi;
import com.test.keycloak.vo.*;
import org.json.simple.parser.ParseException;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class userManageController {

    private static final Logger log = LoggerFactory.getLogger(userManageController.class);

    private KeycloakApi keycloakApi;

    @Autowired
    public userManageController(KeycloakApi keycloakApi){
        this.keycloakApi = keycloakApi;
    }


    //사용자 리스트(admin)
    @GetMapping(value = "/getUserList")
    public Object getUserList(){

        Object result = keycloakApi.userList();

        return result;
    }



    //사용자생성(admin)
    @PostMapping(value = "/registerUser")
    public String getRegisterUser(@Validated userCreateForm param, BindingResult bindingResult){

        //데이터 검증
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "error";
        }

        String result = keycloakApi.createUser(param);

        return result;
    }


    //사용자 수정(admin)
    @PutMapping(value = "/modifyUser")
    public String getModifyUser(@Validated userUpdateForm param, BindingResult bindingResult){

        //데이터 검증
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "error";
        }

        String result = keycloakApi.modifyUser(param);

        return result;
    }

    //사용자 삭제(admin)
    @DeleteMapping(value = "/deleteUser")
    public String getDeleteUser(@RequestParam(value = "id[]") List<String> listId){

        String totalResult = null;

        for(String id : listId){
            String result = keycloakApi.deleteUser(id);

            if(result == "SUCCESS"){
                totalResult = "SUCCESS";
                continue;
            }
            else{
                totalResult = "ERROR";
                break;
            }
        }
        return totalResult;

    }

    //사용자 중복 체크(admin)
    @GetMapping(value = "/duplicateCheck")
    public String getDuplicateCheck(UserVO param) throws ParseException {

        String result = keycloakApi.duplicateCheck(param);

        return result;
    }


    //내정보 리스트
    @GetMapping(value = "/getUserInfo")
    public Object getUserInfo(KeycloakAuthenticationToken authentication){

        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
        AccessToken token = account.getKeycloakSecurityContext().getToken();

        String uuid =  authentication.getPrincipal().toString();        //로그인 id 값
        String userName = token.getPreferredUsername();         //로그인 id

        Object result = keycloakApi.userInfoList(uuid,userName);

        return result;
    }

    //내정보수정
    @PutMapping(value = "/modifyInfo")
    public String getModifyInfo(@Validated @ModelAttribute("userInfo") myInfoForm param, BindingResult bindingResult, KeycloakAuthenticationToken authentication){

        //데이터 검증
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "error";
        }

        String uuid =  authentication.getPrincipal().toString();       ////로그인 id 값
        String result = keycloakApi.ModifyInfo(param, uuid);     //SUCCESS or ERROR or alreadyEmail

        return result;
    }


    //내 비밀번호 변경
    @PutMapping(value = "/modifyPassword")
    public String getModifyPassword(@Validated @ModelAttribute("userInfo") pwInfoForm param, BindingResult bindingResult, KeycloakAuthenticationToken authentication){

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "error";
        }

        String uuid =  authentication.getPrincipal().toString();        //로그인 id 값
        String result = keycloakApi.ModifyPassword(param, uuid);

        return result;
    }


}
