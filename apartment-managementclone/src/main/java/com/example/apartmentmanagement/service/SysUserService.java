package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.entity.SysUser;
import com.example.apartmentmanagement.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    // 根据用户名查询系统用户信息
    public SysUser findUser(String username) {
        return sysUserMapper.getByUsername(username);
    }
}