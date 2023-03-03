package com.dong.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author admin
 * @since 2023-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Employee implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 雇员ID
     */
      private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像地址
     */
    private String headImg;

    /**
     * 标语
     */
    private String tagline;

    /**
     * 个人简介
     */
    private String profile;

    /**
     * 主页被浏览次数
     */
    private Integer browseCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
