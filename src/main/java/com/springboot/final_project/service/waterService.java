package com.springboot.final_project.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tencentcloudapi.captcha.v20190722.CaptchaClient;
import com.tencentcloudapi.captcha.v20190722.models.DescribeCaptchaResultRequest;
import com.tencentcloudapi.captcha.v20190722.models.DescribeCaptchaResultResponse;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import org.springframework.stereotype.Service;

@Service
public class waterService
{
    public String waterCheck(String ticket, String randstr){
        try{

            Credential cred = new Credential("AKIDRcXGkw3cQxEI6cl92hvHZXA1YpzsHu4x", "DAr7GNFqBSffQyd3DF1ShgS2LVfgQu97");

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("captcha.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            CaptchaClient client = new CaptchaClient(cred, "ap-guangzhou", clientProfile);

            String params = "{\"CaptchaType\":9,\"Ticket\":\""+ticket+"\",\"UserIp\":\"113.119.66.47\",\"Randstr\":\""+randstr+"\",\"CaptchaAppId\":2032327562,\"AppSecretKey\":\"0wQW4tGwqIXrUqX-EfJaW0A**\"}";

            DescribeCaptchaResultRequest req = DescribeCaptchaResultRequest.fromJsonString(params, DescribeCaptchaResultRequest.class);

            DescribeCaptchaResultResponse resp = client.DescribeCaptchaResult(req);

            String resp_string = DescribeCaptchaResultResponse.toJsonString(resp);

            JSONObject resp_object = JSON.parseObject(resp_string);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",20000);
            jsonObject.put("data",resp_object);
            return jsonObject.toJSONString();

        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
            return null;
        }
    }
}