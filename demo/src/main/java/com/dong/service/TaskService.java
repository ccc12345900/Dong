package com.dong.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dong.domain.Task;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2023-03-02
 */
public interface TaskService extends IService<Task> {
    Integer getAllCount();
//    List<TaskVo> getRecently();
}
