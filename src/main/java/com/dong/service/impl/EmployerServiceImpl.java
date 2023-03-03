package com.dong.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.domain.Employer;
import com.dong.mapper.EmployerMapper;
import com.dong.service.EmployerService;
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
public class EmployerServiceImpl extends ServiceImpl<EmployerMapper, Employer> implements EmployerService {

    @Autowired
    private EmployerMapper employerMapper;

    @Override
    public Employer login(String username, String password) {
        //填写查询条件
        QueryWrapper<Employer> employeeQueryWrapper = new QueryWrapper<>();
        employeeQueryWrapper.eq("username",username);
        // 根据用户名获取用户信息
        Employer employer = employerMapper.selectOne(employeeQueryWrapper);

        // 验证密码是否正确,密码一致登录成功
        if (employer != null && employer.getPassword().equals(password)) {
            System.out.println("雇主登录成功");
            return employer;
        }

        // 密码不一致，返回 null
        return null;
    }

    @Override
    public Integer getAllCount() {
        // 调用 employerMapper 查询所有雇主数据
        List<Employer> employers = employerMapper.selectList(null);
        // 三元表达式返回总数，如果 employers 不为空返回集合的数据，为空返回 0
        return employers != null ? employers.size() : 0;
    }
}
