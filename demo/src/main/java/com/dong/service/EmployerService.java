package com.dong.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.domain.Employer;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2023-03-02
 */
public interface EmployerService extends IService<Employer> {
    Employer login(String username, String password);

    Integer getAllCount();
}
