package com.itheima.reggie.service.impl;

import com.itheima.reggie.entity.Category;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.reggie.mapper.CategoryMapper;
import com.itheima.reggie.service.CategoryService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜品及套餐分类 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2022-06-29
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
