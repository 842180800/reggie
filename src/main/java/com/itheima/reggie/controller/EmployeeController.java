package com.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.reggie.common.R;
import com.itheima.reggie.entity.Employee;
import com.itheima.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@Slf4j
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest httpServletRequest, @RequestBody Employee employee){
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        QueryWrapper<Employee> wrapper = new QueryWrapper<Employee>();
        wrapper.eq("username",employee.getUsername());
        Employee one = employeeService.getOne(wrapper);
        if( one == null){
            return R.error("登入失败");
        }
        if(!one.getPassword().equals(password)){
            return R.error("登入失败，密码错误");
        }
        if(one.getStatus() == 0){
            return R.error("账号已禁用");
        }

        httpServletRequest.getSession().setAttribute("employee",one.getId());
        System.out.println(httpServletRequest.getSession().getAttribute("employee"));
        return R.success(one);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出登录");
    }

    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){

        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
        Long userId = (Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(userId);
//        employee.setUpdateUser(userId);
        log.info("新增员工，{}",employee);
        employeeService.save(employee);

        return R.success("新增员工成功");
    }
    @GetMapping("/page")
    public R<Page> page(Integer page,Integer pageSize, String name){
        log.info("..,{},{},{}",page,pageSize,name);

        Page<Employee> pageInfo = new Page<>(page,pageSize);

        QueryWrapper<Employee> wrapper = new QueryWrapper<>();
        wrapper.like(!StringUtils.isEmpty(name),"name",name);
        wrapper.orderByAsc("update_time");
        employeeService.page(pageInfo,wrapper);

        return R.success(pageInfo);
    }

    /**
     * 根据员工id修改信息
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){

        Long id = (Long)request.getSession().getAttribute("employee");
        System.out.println(id);
//        employee.setUpdateUser(id);
//        employee.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(employee);

        return R.success("修改完毕");
    }
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        if(employee != null){
            return R.success(employee);
        }
        return R.error("没有该员工");
    }
}
