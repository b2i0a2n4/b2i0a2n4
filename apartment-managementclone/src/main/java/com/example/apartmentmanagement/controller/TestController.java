package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.entity.SysUser;
import com.example.apartmentmanagement.service.SysUserService;
import com.example.apartmentmanagement.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin
public class TestController {

    @Autowired
    private SysUserService sysUserService;

    // 注入 Redis 模板类处理缓存操作
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 用户认证登录接口
    @GetMapping("/login")
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> result = new HashMap<>();
        SysUser user = sysUserService.findUser(username);

        // 校验用户信息与密码匹配度
        if (user != null && user.getPassword().equals(password)) {
            // 鉴权通过，生成 JWT 令牌
            String token = JwtUtils.createToken(username);

            // 将令牌存入 Redis 缓存并设置 12 小时有效时长
            stringRedisTemplate.opsForValue().set("token:" + username, token, 12, TimeUnit.HOURS);

            // 封装成功响应数据
            result.put("success", true);
            result.put("message", "登录成功");
            result.put("token", token); // 返回授权令牌
            result.put("user", user);
        } else {
            // 封装失败响应数据
            result.put("success", false);
            result.put("message", "账号或密码错误");
        }
        return result;
    }
}