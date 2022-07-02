package com.itheima.reggie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Category;
import com.itheima.reggie.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 * 菜品及套餐分类 前端控制器
 * </p>
 *
 * @author baomidou
 * @since 2022-06-29
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody Category category){
        categoryService.save(category);
        return R.success("添加成功");
    }
    @GetMapping("page")
    public R<Page> page(Integer page,Integer pageSize){

        Page<Category> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        Page<Category> page1 = categoryService.page(pageInfo, queryWrapper);
        return R.success(page1);
    }
}
