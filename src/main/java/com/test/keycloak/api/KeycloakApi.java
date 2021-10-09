package com.test.keycloak.api;

import com.fasterxml.jackson.jaxrs.json.annotation.JSONP;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.test.keycloak.vo.UserVO;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class KeycloakApi {


    @Value("${auth.server}")
    private String server;

    @Value("${keycloak.auth-server-url}")
    private String serverUrl;

    @Value("${auth.id}")
    private String authId;

    @Value("${auth.password}")
    private String authPassword;

    @Value("${auth.realm}")
    private String authRealm;

    @Value("${auth.clientId}")
    private String authClientId;

//    private static final Logger log = LoggerFactory.getLogger(KeycloakApi.class);
    private static final Logger log = LoggerFactory.getLogger(KeycloakApi.class);
    //사용자 리스트(admin)
    public Object userList(UserVO param, String uuid) {

        String token = getAccessToken();

        String authServerUrl2 = server;
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        Map<String, Object> modifyInfo = new HashMap<>();

        Gson var = new Gson();
        String json = var.toJson(modifyInfo);

        HttpEntity<String> entity = new HttpEntity<>(json,headers);

        try {
            ResponseEntity<String> test = restTemplate.exchange(authServerUrl2, HttpMethod.GET,entity,String.class);
            return test;
        } catch (RestClientException e) {
            log.error(e.getMessage());
            return "ERROR";
        }
    }

    //사용자 등록(admin)
    public String createUser(UserVO param, String uuid) {

        String token = getAccessToken();

        String authServerUrl2 = server;
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        JsonObject password = new JsonObject();
        password.addProperty("temporary","false");
        password.addProperty("type","password");
        password.addProperty("value","1234");       //추후 수정(입력 값 받아야함)

        JsonArray passwordArray = new JsonArray();
        passwordArray.add(password);        //passwordArray 설정

        Map<String, Object> registerUser = new HashMap<>();
        registerUser.put("username", param.getId());
        registerUser.put("firstName", param.getFirstName());
        registerUser.put("lastName", param.getLastName());
        registerUser.put("email",param.getEmail());
        if(param.getUseEnable().equals("enable"))
            registerUser.put("enabled", "true");
        else
            registerUser.put("enabled", "false");
        registerUser.put("credentials",passwordArray);

        Gson var = new Gson();
        String json = var.toJson(registerUser);

        HttpEntity<String> entity = new HttpEntity<>(json,headers);

        try {
            ResponseEntity<String> test = restTemplate.exchange(authServerUrl2, HttpMethod.POST,entity,String.class);
            return "SUCCESS";
        } catch (RestClientException e) {
            log.error(e.getMessage());
            if(e.getMessage().contains("409"))
                return "alreadyEmail";
            else
                return e.getMessage();
        }

    }

    //사용자 수정(admin)
    public String modifyUser(UserVO param) {

        String token = getAccessToken();

        String authServerUrl2 = server +  "/" + param.getId();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        Map<String, Object> modifyUser = new HashMap<>();
        modifyUser.put("firstName", param.getFirstName());
        modifyUser.put("lastName", param.getLastName());
        modifyUser.put("email", param.getEmail());
        if(param.getUseEnable().equals("enable"))
            modifyUser.put("enabled", "true");
        else
            modifyUser.put("enabled", "false");

        Gson var = new Gson();
        String json = var.toJson(modifyUser);

        HttpEntity<String> entity = new HttpEntity<>(json,headers);

        try {
            ResponseEntity<String> test = restTemplate.exchange(authServerUrl2, HttpMethod.PUT,entity,String.class);
            return "SUCCESS";
        } catch (RestClientException e) {
            log.error(e.getMessage());
            if(e.getMessage().contains("409"))
                return "alreadyEmail";
            else
                return e.getMessage();
        }

    }

    //사용자 삭제(admin)
    public String deleteUser(String id) {

        String token = getAccessToken();

        String authServerUrl2 = server + "/" + id;
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        Map<String, Object> modifyUser = new HashMap<>();

        Gson var = new Gson();
        String json = var.toJson(modifyUser);

        HttpEntity<String> entity = new HttpEntity<>(json,headers);

        try {
            ResponseEntity<String> test = restTemplate.exchange(authServerUrl2, HttpMethod.DELETE,entity,String.class);
            return "SUCCESS";
        } catch (RestClientException e) {
            log.error(e.getMessage());
            return "ERROR";
        }

    }

    //사용자 중복체크(admin)
    public String duplicateCheck(UserVO param) throws ParseException {

        String token = getAccessToken();

        String authServerUrl2 = server;
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        Map<String, Object> duplicateUser = new HashMap<>();
        Gson var = new Gson();
        String json = var.toJson(duplicateUser);

        HttpEntity<String> entity = new HttpEntity<>(json,headers);
        JSONParser jsonparser2 = new JSONParser();

        try {

            String result = "SUCCESS";

            String test = restTemplate.exchange(authServerUrl2, HttpMethod.GET,entity,String.class).getBody();
            JSONArray arr = (JSONArray) jsonparser2.parse(test);
            for(int i = 0; i < arr.toArray().length; i++){
                JSONObject obj = (JSONObject) arr.get(i);
                if(param.getId().equals(obj.get("username"))){
                    result = "Duplicate";
                    break;
                }
            }
            return result;

        } catch (RestClientException e) {
            log.error(e.getMessage());
            if(e.getMessage().contains("409"))
                return "alreadyId";
            else
                return e.getMessage();
        }
    }

    //내 정보 리스트
    public Object userInfoList(UserVO param, String uuid, String userName) {

        String token = getAccessToken();

        String authServerUrl2 = server + "/" + uuid;
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        Map<String, Object> modifyInfo = new HashMap<>();
        modifyInfo.put("search", userName);

        Gson var = new Gson();
        String json = var.toJson(modifyInfo);

        HttpEntity<String> entity = new HttpEntity<>(json,headers);

        try {
            ResponseEntity<String> test = restTemplate.exchange(authServerUrl2, HttpMethod.GET,entity,String.class);
            return test;
        } catch (RestClientException e) {
            log.error(e.getMessage());
            return "ERROR";
        }

    }


    //내정보 수정
    public String ModifyInfo(UserVO param, String uuid){

        String token = getAccessToken();

        String authServerUrl2 = server + "/" + uuid;
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        Map<String, Object> modifyInfo = new HashMap<>();

        modifyInfo.put("firstName", param.getFirstName());
        modifyInfo.put("lastName", param.getLastName());
        modifyInfo.put("email", param.getEmail());

        Gson var = new Gson();
        String json = var.toJson(modifyInfo);

        HttpEntity<String> entity = new HttpEntity<>(json,headers);

        try {
            ResponseEntity<String> test = restTemplate.exchange(authServerUrl2, HttpMethod.PUT,entity,String.class);
            return "SUCCESS";
        } catch (RestClientException e) {
            log.error(e.getMessage());
            if(e.getMessage().contains("409"))
                return "alreadyEmail";
            else
                return e.getMessage();

        }
    }

    //내 비밀번호 변경
    public String ModifyPassword(UserVO param, String uuid) {

        String token = getAccessToken();

        String authServerUrl2 = server + "/" + uuid + "/reset-password";
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + token);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        Map<String, Object> modifyPassword = new HashMap<>();
        modifyPassword.put("temporary", "false");       //다음에 로그인할때 비밀번호 변경안해도됨
        modifyPassword.put("type", "PASSWORD");
        modifyPassword.put("value", param.getPassword());

        Gson var = new Gson();
        String json = var.toJson(modifyPassword);

        HttpEntity<String> entity = new HttpEntity<>(json,headers);

        try {
            ResponseEntity<String> test = restTemplate.exchange(authServerUrl2, HttpMethod.PUT,entity,String.class);

            return "SUCCESS";
        } catch (RestClientException e) {
            log.error(e.getMessage());
            return "ERROR";
        }

    }

    //token 값
    public String getAccessToken(){

        Keycloak keycloak = KeycloakBuilder.builder().serverUrl(serverUrl)
                .grantType(OAuth2Constants.PASSWORD).realm(authRealm).clientId(authClientId)
                .username(authId).password(authPassword)
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build()).build();

        String token = keycloak.tokenManager().getAccessTokenString();

//        auth.server=http://localhost:8080/auth/admin/realms/igloo/users
//        auth.id=ssyong917
//        auth.password=6966
//        auth.realm=master
//        auth.clientId=admin-cli

        return token;
    }


    //    public static ResponseEntity<?> createUser(UserVO param, AccessToken token, String uuid) {
//
//        Keycloak keycloak = KeycloakBuilder.builder().serverUrl(authServerUrl)
//                .grantType(OAuth2Constants.PASSWORD).realm("master").clientId("admin-cli")
//                .username("ssyong917").password("6966")
//                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build()).build();
//
//        keycloak.tokenManager().getAccessToken();
//
//        log.info(String.valueOf(keycloak.tokenManager().getAccessToken()));
//
//        UserRepresentation user = new UserRepresentation();
//        log.info(param.getEmail());
//        log.info(param.getUseEnable());
//
//        if(param.getUseEnable().equals("enable"))
//            user.setEnabled(true);
//        else
//            user.setEnabled(false);
//
//        user.setUsername(param.getId());
//        user.setFirstName(param.getFirstName());
//        user.setLastName(param.getLastName());
//        user.setEmail(param.getEmail());
//
//
//        // Get realm
//        RealmResource realmResource = keycloak.realm(realm);
//        UsersResource usersRessource = realmResource.users();
////        UsersResource usersRessourcex = realmResource.update(user);
//
//
//        Response response = usersRessource.create(user);
//
//
//        log.info(String.valueOf(response));
//
//        param.setRegDate(String.valueOf(response.getDate()));
//
//        param.setStatusCode(response.getStatus());
//        param.setStatus(response.getStatusInfo().toString());
//        param.setPassword("1234");            //초기비밀번호 1234로 초기화
//
//        if (response.getStatus() == 201) {
//
//            String userId = CreatedResponseUtil.getCreatedId(response);
//
//            log.info("Created userId {}", userId);
//
//
//            // create password credential
//            CredentialRepresentation passwordCred = new CredentialRepresentation();
//            passwordCred.setTemporary(false);
//            passwordCred.setType(CredentialRepresentation.PASSWORD);
//            passwordCred.setValue(param.getPassword());
//
//            UserResource userResource = usersRessource.get(userId);
//
//            //비밀번호 reset
//            userResource.resetPassword(passwordCred);
//
//            // Get realm role User
//            RoleRepresentation realmRoleUser = realmResource.roles().get(role).toRepresentation();
//
//            // Assign realm role User to user
//            userResource.roles().realmLevel().add(Arrays.asList(realmRoleUser));
//        }
//
//        log.info(String.valueOf(ResponseEntity.ok(param)));
//        return ResponseEntity.ok(param);
//    }

}
