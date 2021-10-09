package com.test.keycloak.controller;



import com.test.keycloak.api.KeycloakApi;
import com.test.keycloak.vo.UserVO;
import org.json.simple.parser.ParseException;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
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
    public Object getUserList(Model model, UserVO param, KeycloakAuthenticationToken authentication){

        String uuid =  authentication.getPrincipal().toString();
        Object result = keycloakApi.userList(param, uuid);

        return result;
    }



    //사용자생성(admin)
    @PostMapping(value = "/registerUser")
    public String getRegisterUser(Model model, UserVO param, KeycloakAuthenticationToken authentication){

        String uuid =  authentication.getPrincipal().toString();    //로그인 id 값

        String result = keycloakApi.createUser(param, uuid);

        return result;
    }


    //사용자 수정(admin)
    @PutMapping(value = "/modifyUser")
    public String getModifyUser(Model model, UserVO param){

        String result = keycloakApi.modifyUser(param);

//        log.info(String.valueOf(token));        //token 아이디
//        log.info(authentication.getPrincipal().toString()); //userid  ex)74799a65-f455-4cbb-910a-c17a500e36e2
//        log.info(token.getPreferredUsername());     //로그인Id   ex)user3
//        log.info(token.getGivenName());     //lastName
//        log.info(token.getFamilyName());    //firstName
//        log.info(token.getEmail());         //이메일

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
    public Object getUserInfo(Model model, UserVO param, KeycloakAuthenticationToken authentication){

        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
        AccessToken token = account.getKeycloakSecurityContext().getToken();

        String uuid =  authentication.getPrincipal().toString();        //로그인 id 값
        String userName = token.getPreferredUsername();         //로그인 id

        Object result = keycloakApi.userInfoList(param, uuid, userName);

        return result;
    }

    //내정보수정
    @PutMapping(value = "/modifyInfo")
    public String getModifyInfo(Model model, UserVO param, KeycloakAuthenticationToken authentication){

        String uuid =  authentication.getPrincipal().toString();       ////로그인 id 값

        String result = keycloakApi.ModifyInfo(param, uuid);     //SUCCESS or ERROR or alreadyEmail

        return result;
    }


    //내 비밀번호 변경
    @PutMapping(value = "/modifyPassword")
    public String getModifyPassword(Model model, UserVO param,KeycloakAuthenticationToken authentication){

        String uuid =  authentication.getPrincipal().toString();        //로그인 id 값

        String result = keycloakApi.ModifyPassword(param, uuid);

        return result;
    }


}
