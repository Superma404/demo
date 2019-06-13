package com.study.demo.provider;

import com.alibaba.fastjson.JSON;
import com.study.demo.bean.User;
import com.study.demo.dto.AccesstokenDTO;
import com.study.demo.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

/**
 *
 */
import java.io.IOException;

@Component
public class GithubProvider {

    public String getAccessToken(AccesstokenDTO accesstokenDTO){
       MediaType mediaType
                = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accesstokenDTO));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                String token = string.split("&")[0].split("=")[1];
                return token;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
    }
    public GithubUser getUser(String accessToken){
        //438d9f94ecb38784c555cb9ebc78390fc9ba695b
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            GithubUser githubUser=JSON.parseObject(string,GithubUser.class);
            return githubUser;
        }catch (IOException e){

        }
        return null;
    }

}
