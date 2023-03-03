package com.dong.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.consts.TaskStatus;
import com.dong.domain.*;
import com.dong.mapper.*;
import com.dong.service.TaskService;
import com.dong.vo.BidVo;
import com.dong.vo.TaskVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployerMapper employerMapper;

//    @Autowired
//    private TaskCategoryMapper taskCategoryMapper;

    @Autowired
    private TaskSkillMapper taskSkillMapper;

    @Autowired
    private BidMapper bidMapper;
    @Override
    public Integer getAllCount() {
        // 查询出所有任务
        QueryWrapper<Task> taskQueryWrapper = new QueryWrapper<>();
        taskQueryWrapper.or().ne("taskStatus", TaskStatus.UNCHECK).ne("taskStatus", TaskStatus.CHECK_FAIL);
        List<Task> tasks = taskMapper.selectList(taskQueryWrapper);
        // 三元表达式返回任务总数，如果任务集合不为空，则返回集合大小也就是任务总数，如果任务集合是空的，则返回 0
        return tasks != null ? tasks.size() : 0;
    }

//    @Override
//    public List<TaskVo> getRecently() {
//        // 设置分页查询条件，第一个条件是第几页，因为只查 5 条，所以是 0 ，代表第 0 页，5 是要查询的条数
//        PageHelper.startPage(1, 5);
//
//        // 构建查询条件,查询任务状态为 0 的任务，0 代表还未中标的任务
//        QueryWrapper<Task> taskQueryWrapper = new QueryWrapper<>();
//        taskQueryWrapper.or().eq("taskStatus", TaskStatus.NO_BIT);
//        // 根据创建时间，倒叙查询
//        taskQueryWrapper.orderByDesc("createTime");
//        // 分页查询，查询出前 5 条
//        PageInfo<Task> taskPageInfo = new PageInfo<>(taskMapper.selectList(taskQueryWrapper));
//
//        // 获取查询结果
//        List<Task> tasks = taskPageInfo.getList();
//
//        // Task 转换为 TaskVo 视图展示类
//        List<TaskVo> taskVos = tasksToTaskVos(tasks);
//
//        return taskVos;
//    }

//    private List<TaskVo> tasksToTaskVos(List<Task> tasks) {
//        List<TaskVo> taskVos = new ArrayList<>();
//        for (Task task : tasks) {
//            TaskVo taskVo = new TaskVo();
//            // 调用 Spring 提供的工具类复制相同的属性
//            BeanUtils.copyProperties(task, taskVo);
//
//            // 查询雇主信息
//            Employer employer = employerMapper.selectById(task.getEmployerId());
//            taskVo.setEmployer(employer);
//
//            // 查询出任务分类
//            TaskCategory taskCategory = taskCategoryMapper.selectById(task.getCategoryId());
//            taskVo.setTaskCategory(taskCategory);
//
//            // 查询出任务完成雇员信息
//            String s = String.valueOf(task.getTaskStatus());
//            if (Byte.valueOf(s) != TaskStatus.NO_BIT) {
//                Employee employee = employeeMapper.selectById(task.getEmployeeId());
//                taskVo.setEmployee(employee);
//            }
//
//            // 计算多久以前发布
//            // 先计算差多少秒，调用 Hutool 的日期计算工具类
//            String beforeTime = taskBeforeTime(task);
//            taskVo.setBeforeTime(beforeTime);
//
//            // 查询任务所需技能标签,技能与标签是一对多的关系，因为任务和雇员都对应技能表，所以采用第三张表来映射关系
//            List<TaskSkill> skills = findTaskSkills(task);
//            taskVo.setSkills(skills);
//
//            // 查询该任务所有竞标者
//            List<BidVo> bidVos = findTaskBids(task);
//            taskVo.setBidVos(bidVos);
//
//            // 计算平均竞标价格
//            // 总竞标价格
//            Double totalPrice = 0.0;
//            Double avgPrice = 0.0;
//            if (bidVos.size() != 0) {
//                for (BidVo bidVo : bidVos) {
//                    totalPrice += bidVo.getBidPrice();
//                }
//                avgPrice = totalPrice / bidVos.size();
//            }
//            taskVo.setAvgPrice(avgPrice);
//
//            // 如果任务属于未完成状态，计算到期时间和剩余完成时间
//            if (task.getTaskStatus().equals(TaskStatus.BIT)) {
//                // 计算到期时间
//                // 获取该任务中标雇员
//                Long employeeId = task.getEmployeeId();
//                // 查询任务中标信息，查询到期时间
//                QueryWrapper<Bid> bidQueryWrapper = new QueryWrapper<>();
//                bidQueryWrapper.eq("employeeId", employeeId).eq("taskId", task.getId());
//                Bid bid = bidMapper.selectOne(bidQueryWrapper);
//                // 获取到期时间
//                Date expireTime = bid.getDeliveryTime();
//                taskVo.setExpireTime(expireTime);
//            }
//
//            // 添加到集合中
//            taskVos.add(taskVo);
//        }
//        return taskVos;
//    }

    /**
     * 查询任务所有竞标者信息
     *
     * @param task 任务
     * @return
     */
    private List<BidVo> findTaskBids(Task task) {
        QueryWrapper<Bid> bidQueryWrapper = new QueryWrapper<>();
        // 根据任务 ID，查询出所有竞标信息
        bidQueryWrapper.eq("taskId", task.getId());
        List<Bid> bids = bidMapper.selectList(bidQueryWrapper);
        List<BidVo> bidVos = new ArrayList<>();
        for (Bid bid : bids) {
            BidVo bidVo = new BidVo();
            // 相同属性进行复制
            BeanUtils.copyProperties(bid, bidVo);
            // 根据投标雇员ID查询出雇员信息
            Employee currEmployee = employeeMapper.selectById(bid.getEmployeeId());
            bidVo.setEmployee(currEmployee);
            // 复制完值添加到集合中
            bidVos.add(bidVo);
        }
        return bidVos;
    }

    /**
     * 查询任务所需要的技能
     *
     * @param task
     * @return
     */
    private List<TaskSkill> findTaskSkills(Task task) {
        QueryWrapper<TaskSkill> taskSkillQueryWrapper = new QueryWrapper<>();
        taskSkillQueryWrapper.eq("taskId", task.getId());
        // 查询出所有的任务与技能对应关系，再根据技能ID，查询出技能信息
        List<TaskSkill> taskSkills = taskSkillMapper.selectList(taskSkillQueryWrapper);
        return taskSkills;
    }

    /**
     * 计算任务多久以前发布
     *
     * @param task
     * @return
     */
    private String taskBeforeTime(Task task) {
        String beforeTime = "";
        Date createTime = task.getCreateTime();
        long ms = DateUtil.between(createTime, new Date(), DateUnit.MS);
        // 如果相差秒数大于 60，计算分钟相差数
        if (ms > 60) {
            // 计算相差分钟数，如果分钟数大于 60 计算小时
            long minute = DateUtil.between(createTime, new Date(), DateUnit.MINUTE);
            if (minute > 60) {
                // 计算相差小时数，如果小时大于 24 计算天数
                long hour = DateUtil.between(createTime, new Date(), DateUnit.HOUR);
                if (hour > 24) {
                    // 计算相差天数，如果天数大于 31 计算月数
                    long day = DateUtil.between(createTime, new Date(), DateUnit.DAY);
                    if (day > 31) {
                        // 计算相差月数，如果月数大于 12 计算年数
                        long month = DateUtil.betweenMonth(createTime, new Date(), true);
                        if (month > 12) {
                            long year = DateUtil.betweenYear(createTime, new Date(), true);
                            beforeTime = year + "年前";
                        } else {
                            beforeTime = month + "月前";
                        }
                    } else {
                        beforeTime = day + "天前";
                    }
                } else {
                    beforeTime = hour + "小时前";
                }
            } else {
                beforeTime = minute + "分钟前";
            }
        } else {
            beforeTime = ms + "秒前";
        }
        return beforeTime;
    }
}
