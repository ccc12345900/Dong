package com.dong.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

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
public class Task implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 任务ID
     */
      private Long id;

    /**
     * 任务分类ID
     */
    private Long categoryId;

    /**
     * 任务发布雇主ID
     */
    private Long employerId;

    /**
     * 任务标题
     */
    private String taskTitle;

    /**
     * 任务简介
     */
    private String taskProfile;

    /**
     * 任务描述
     */
    private String taskDesc;

    /**
     * 最低预算价格
     */
    private Double feesLow;

    /**
     * 最高预算价格
     */
    private Double feesHigh;

    /**
     * 任务附件地址
     */
    private String feesFile;

    /**
     * 附件文件名称
     */
    private String filename;

    /**
     * 完成任务雇员ID
     */
    private Long employeeId;

    /**
     * 任务成交价格
     */
    private Double taskPrice;

    /**
     * 任务状态
     */
    private Integer taskStatus;

    /**
     * 成交时间
     */
    private LocalDateTime closeTime;

    /**
     * 中标时间
     */
    private LocalDateTime bidTime;

    /**
     * 创建时间
     */
    private Date createTime;


}
