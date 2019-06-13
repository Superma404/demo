package com.study.demo.controller;

import com.study.demo.bean.User;
import com.study.demo.dao.UserMapper;
import com.study.demo.dto.AccesstokenDTO;
import com.study.demo.dto.GithubUser;
import com.study.demo.provider.GithubProvider;
import com.study.demo.service.UserService;
import com.study.demo.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {


    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    private UserService userService;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redirectUrl;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccesstokenDTO accesstokenDTO = new AccesstokenDTO();
        accesstokenDTO.setCode(code);
        accesstokenDTO.setRedirect_url(redirectUrl);
        accesstokenDTO.setClient_secret(clientSecret);
        accesstokenDTO.setState(state);
        accesstokenDTO.setClient_id(clientId);
        String accesstoken = githubProvider.getAccessToken(accesstokenDTO);
        GithubUser githubUser = githubProvider.getUser(accesstoken);
        if (githubUser!=null){
            String token=UUID.randomUUID().toString();
            User user = new User();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            System.out.println(user);
            userService.insert(user);
                response.addCookie(new Cookie("token", token));
                request.getSession().setAttribute("user",user);
                return "redirect:/";
        }else{
            return "redirect:/";
        }

    }
}
