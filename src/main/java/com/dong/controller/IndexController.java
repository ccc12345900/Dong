package com.dong.controller;

import com.dong.domain.Employee;
import com.dong.domain.Employer;
import com.dong.service.EmployeeService;
import com.dong.service.EmployerService;
import com.dong.service.TaskCategoryService;
import com.dong.service.TaskService;
import com.dong.vo.TaskCategoryVo;
import com.dong.vo.TaskVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 橙宝cc
 * @date 2023/3/2 - 23:08
 */
@Controller
public class IndexController {

    @Resource
    private TaskCategoryService taskCategoryService;

    @Resource
    private TaskService taskService;

    @Resource
    private EmployerService employerService;

    @Resource
    private EmployeeService employeeService;


//    @GetMapping({"", "/index"})
//    public String index(Model model) {
//
//        // 查询所有任务分类, 为什么要用 Vo 对象，因为首页展示的热门分类中需要显示，该分类的任务数量，所以需要创建一个 Vo 对象，来包含分类任务数量信息
//        List<TaskCategoryVo> taskCategoryVos = taskCategoryService.getAll();
//
//        // 查询任务发布总数
//        Integer taskCount = taskService.getAllCount();
//
//        // 查询自由职业者(雇员）总数
//        Integer employeeCount = employeeService.getAllCount();
//
//        // 雇主数量
//        Integer employerCount = employerService.getAllCount();
//
//        // 查询热门分类,分类状态为 1 则为人们分类
//        List<TaskCategoryVo> popularCategories = taskCategoryService.getPopular();
//
//        // 查询近期任务（查询 5 条）,根据创建时间来查询
//        List<TaskVo> recentTaskVos =  taskService.getRecently();
//
//        // 添加到 Model 域对象中，供页面展示
//        model.addAttribute("taskCategories", taskCategoryVos);
//        model.addAttribute("taskCount", taskCount);
//        model.addAttribute("employeeCount", employeeCount);
//        model.addAttribute("employerCount", employerCount);
//        model.addAttribute("popularCategories", popularCategories);
//        model.addAttribute("recentTasks", recentTaskVos);
//
//        return "index";
//    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    /**
     * 用户登录，包括雇员和雇主，通过传过来的 accountType 判断
     *
     * @return
     */
    @PostMapping("login")
    public String login(Integer accountType, String username, String password, RedirectAttributes redirectAttributes, HttpSession session) {
        // 如果传过来的 accountType 为 0 则为雇员登录
        if (accountType == 0) {
            // 雇员登录
            Employee employee = employeeService.login(username, password);
            // 如果雇员不为 null 登录成功
            if (employee != null) {
                // 将登录信息放入 session 中
                session.setAttribute("employee", employee);
                // 注销雇主登录信息
                session.removeAttribute("employer");
                // 重定向到首页
                return "redirect:/index";
            }

            // 登录失败
            else {
                redirectAttributes.addFlashAttribute("msg", "用户名或密码错误");
                // 登录失败，重定向到登录页
                return "redirect:/login";
            }
        }

        // 如果 accountType != 0 则为雇主登录
        else {
            // 雇主登录
            Employer employer = employerService.login(username, password);
            // 如果雇主不为 null 登录成功
            if (employer != null) {
                // 将登录信息放入 session 中
                session.setAttribute("employer", employer);
                // 注销雇员登录信息
                session.removeAttribute("employee");
                // 重定向到首页
                return "redirect:/index";
            }

            // 登录失败
            else {
                redirectAttributes.addFlashAttribute("msg", "用户名或密码错误");
                // 登录失败，重定向到登录页
                return "redirect:/login";
            }
        }
    }
}
