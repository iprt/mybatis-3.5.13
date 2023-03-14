package org.apache.ibatis.examples;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

/**
 * Demo
 *
 * @author zhuzhenjie
 * @since 3/14/2023
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
public class DemoBean {
    private Long id;
    private String name;
    private Date createTime;
    private Date updateTime;
}
