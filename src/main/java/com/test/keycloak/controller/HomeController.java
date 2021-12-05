package com.test.keycloak.controller;

import com.test.keycloak.api.KeycloakApi;
import com.test.keycloak.vo.UserVO;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {


    @Autowired
    private KeycloakApi keycloakApi;


    @GetMapping("/")
    public String Home(HttpSession session){

        String id = (String) session.getAttribute("id");

        if(id==null || "".equals(id)){          //Session이 없으면 id에 빈값 설정
            session.setAttribute("id","");
        }

        return "main";
    }

    @GetMapping("/main")
    public String Main(Model model, HttpSession session, KeycloakAuthenticationToken authentication){

        UserVO result = userSet(authentication, session);       //로그인한 session 설정

        model.addAttribute("userInfo",result);

        return "main";
    }


    @GetMapping("/user_manage")
    public String userManage(Model model, KeycloakAuthenticationToken authentication,HttpSession session){

        UserVO result = userSet(authentication, session); //로그인한 session 설정

        model.addAttribute("userInfo",result);

        return "user_manage";
    }

    /*@GetMapping("/system_manage")
    public String systemManage(){
        return "system_manage";
    }*/

    @GetMapping("/my_info")
    public String myInfo(Model model, KeycloakAuthenticationToken authentication,HttpSession session){

        UserVO result = userSet(authentication, session); //로그인한 session 설정

        model.addAttribute("userInfo",result);

        return "my_info";

    }

    @GetMapping("/pw_manage")
    public String pwManage(Model model, KeycloakAuthenticationToken authentication, HttpSession session){

        UserVO result = userSet(authentication, session); //로그인한 session 설정

        model.addAttribute("userInfo",result);

        return "pw_manage";

    }


    private UserVO userSet(KeycloakAuthenticationToken authentication, HttpSession session){

        SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
        AccessToken token = account.getKeycloakSecurityContext().getToken();

        String uuid =  authentication.getPrincipal().toString();        //로그인 id 값
        String userName = token.getPreferredUsername();         //로그인 id

        UserVO result = keycloakApi.userInfoList(uuid, userName);

        session.setAttribute("id", result.getId());

        return result;
    }

//    SimpleKeycloakAccount account = (SimpleKeycloakAccount) authentication.getDetails();
//    AccessToken token = account.getKeycloakSecurityContext().getToken();
//
//    UserVO userVO = new UserVO();
//        userVO.setId(token.getPreferredUsername());
//        userVO.setFirstName(token.getFamilyName());
//        userVO.setLastName(token.getGivenName());
//        userVO.setEmail(token.getEmail());
//
//    String userName = token.getPreferredUsername();
//
//        session.setAttribute("id", userName);

}
