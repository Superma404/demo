package com.study.demo.controller;

import com.study.demo.dto.AccesstokenDTO;
import com.study.demo.dto.GithubUser;
import com.study.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {


    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redirectUrl;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state){
        AccesstokenDTO accesstokenDTO = new AccesstokenDTO();
        accesstokenDTO.setCode(code);
        accesstokenDTO.setRedirect_url(redirectUrl);
        accesstokenDTO.setClient_secret(clientSecret);
        accesstokenDTO.setState(state);
        accesstokenDTO.setClient_id(clientId);
        String accesstoken = githubProvider.getAccessToken(accesstokenDTO);
        GithubUser user = githubProvider.getUser(accesstoken);
        System.out.println(user.getName());
        return "index";
    }
}
