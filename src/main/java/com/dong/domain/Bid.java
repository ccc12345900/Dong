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
 * @since 2023-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Bid implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 投标ID
     */
      private Long id;

    /**
     * 任务ID
     */
    private Long taskId;

    /**
     * 职业者ID
     */
    private Long employeeId;

    /**
     * 投标价格
     */
    private Double bidPrice;

    /**
     * 交货时间描述，例如 1 天
     */
    private String deliveryDesc;

    /**
     * 交货时间
     */
    private Date deliveryTime;

    /**
     * 竞标状态
     */
    private Integer bidStatus;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


}
