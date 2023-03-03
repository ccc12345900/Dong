package com.dong.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.domain.Employee;
import com.dong.mapper.EmployeeMapper;
import com.dong.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2023-03-02
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;


    @Override
    public Employee login(String username, String password) {
        //填写查询条件
        QueryWrapper<Employee> employeeQueryWrapper = new QueryWrapper<>();
        employeeQueryWrapper.eq("username",username);
        // 根据用户名获取用户信息
        Employee employee = employeeMapper.selectOne(employeeQueryWrapper);


        // 验证密码是否正确,密码一致登录成功
        if (employee != null && employee.getPassword().equals(password)) {
            System.out.println("登录成功");
            return employee;
        }

        // 密码不一致，返回 null
        return null;
    }

    @Override
    public Integer getAllCount() {
        List<Employee> employees = employeeMapper.selectList(null);
        return employees != null ? employees.size() : 0;
    }
}
