package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.entity.ApartmentPost;
import com.example.apartmentmanagement.mapper.PostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostMapper postMapper;

    // 查询全部留言记录列表
    @GetMapping("/list")
    public List<ApartmentPost> list() {
        return postMapper.getAllPosts();
    }

    // 新增社区留言记录
    @PostMapping("/add")
    public String add(@RequestBody ApartmentPost post) {
        postMapper.insertPost(post);
        return "发布成功";
    }

    // 删除指定留言记录
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        postMapper.deletePost(id);
        return "删除成功";
    }

    // 更新指定留言内容
    @PutMapping("/update")
    public String update(@RequestBody ApartmentPost post) {
        postMapper.updatePost(post);
        return "修改成功";
    }
}