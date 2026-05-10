package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.entity.SysUser;
import com.example.apartmentmanagement.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private SysUserMapper userMapper;

    // 用户身份认证与登录接口
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody SysUser loginUser) {
        Map<String, Object> result = new HashMap<>();

        // 根据用户名查询数据库记录
        SysUser dbUser = userMapper.getByUsername(loginUser.getUsername());

        // 校验用户有效性及密码匹配度
        if (dbUser != null && dbUser.getPassword().equals(loginUser.getPassword())) {
            result.put("success", true);
            result.put("message", "登录成功");
            result.put("token", "token-" + dbUser.getId()); // 生成基础授权令牌
            result.put("user", dbUser); // 封装并返回完整的用户信息对象
        } else {
            result.put("success", false);
            result.put("message", "账号或密码错误");
        }
        return result;
    }

    // 新用户注册接口
    @PostMapping("/register")
    public String register(@RequestBody SysUser user) {
        // 校验用户名唯一性
        if (userMapper.getByUsername(user.getUsername()) != null) {
            return "账号已存在，请直接登录";
        }
        user.setRole(1); // 默认初始化账号角色为普通租客
        userMapper.insertTenant(user);
        return "注册成功，请登录";
    }

    // 获取全部租客信息列表
    @GetMapping
    public List<SysUser> getTenants() {
        return userMapper.getAllTenants();
    }

    // 新增租客信息记录
    @PostMapping
    public String add(@RequestBody SysUser user) {
        userMapper.insertTenant(user);
        return "添加成功";
    }

    // 更新指定租客信息
    @PutMapping
    public String update(@RequestBody SysUser user) {
        userMapper.updateTenant(user);
        return "修改成功";
    }

    // 删除指定租客记录
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        userMapper.deleteUserById(id);
        return "删除成功";
    }
}