package com.springboot.final_project.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.springboot.final_project.Entity.User;
import com.springboot.final_project.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.HashMap;
import java.util.Map;

import static com.google.zxing.client.j2se.MatrixToImageWriter.toBufferedImage;

@Service
public class wxService {

    @Autowired
    private UserMapper userMapper;

    public String adminLogin() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",20000);
        jsonObject.put("data","admin-token");
        return jsonObject.toJSONString();
    }

    public String adminInfo() {
        JSONObject data = new JSONObject();

        data.put("roles","admin");
        data.put("introduction","I am a super administrator");
        data.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        data.put("name","Super Admin");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",20000);
        jsonObject.put("data",data);
        return jsonObject.toJSONString();
    }


    public String getOpenid(String code) {
        QueryWrapper<User> wrapper = new QueryWrapper();
        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString("https://api.weixin.qq.com/sns/jscode2session?appid=wx3e5955768b640056&secret=02878d1ff0518b5893a35c4227fce5e0&js_code={code}&grant_type=authorization_code").build()
                .expand(code)
                .encode();
        String WX_URL = uriComponents.toString();

        RequestEntity<Void> requestEntity = RequestEntity
                .get(URI.create(WX_URL))
                .build();
        ResponseEntity<String> exchange = new RestTemplate().exchange(requestEntity, String.class);
        String body = exchange.getBody();
        JSONObject jsonObject = JSON.parseObject(body);
        JSONObject result = new JSONObject();
        String openid = jsonObject.getString("openid");
        System.out.println("！！！！！！！");
        System.out.println(openid);
        System.out.println(jsonObject);

        result.put("openid",openid);
        User user = userMapper.selectOne(wrapper.eq("openid", openid));
        if(user==null) result.put("registerSign",false);
        else result.put("registerSign",true);
        return result.toJSONString();
    }

    public String getQRcode(int id) throws WriterException, IOException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        User user = userMapper.selectById(id);

        String json_user = JSON.toJSONString(user);

        BitMatrix bitMatrix = qrCodeWriter.encode(json_user, BarcodeFormat.QR_CODE,300,300,hints);
        //MatrixToImageWriter.writeToPath(bitMatrix,"png", Path.of("C:\\zimomo\\WeChatProjects\\test.png"));
        // 实现二：生成二维码图片并将图片转为二进制传递给前台
        // 1、读取文件转换为字节数组
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BufferedImage image = toBufferedImage(bitMatrix);

        ImageIO.write(image, "png", out);
        byte[] bytes = out.toByteArray();

        // 2、将字节数组转为二进制
        Encoder encoder = Base64.getEncoder();
        String binary = encoder.encodeToString(bytes);
        return binary;
    }

    public String getInfo(int id) {
        //User user=new User(6,"test","test",1,"test","https://wx.qlogo.cn/mmopen/vi_32/x2pm3wR1Q1fsJ3T1NEfqiaa6ej5o5s8BDDiaIQOr58R5Cic3SAxJC0SyjPQWUjLKB03Jxm2N3SicGxYVfRaC8QHljQ/132","test",3,3,false);
        User user = userMapper.selectById(id);
        JSONObject result = new JSONObject();
        result.put("info",user);
        return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
    }


}
