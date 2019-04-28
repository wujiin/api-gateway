package com.house.api.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.ToString;

/**
 * @Description 百科实体
 * @Author wujin
 **/
@Data
@ToString
public class Blog implements Serializable {
    private Integer id;
    private String tags;
    private String content;
    private String title;
    private Date createTime;
    private String digest;
    private List<String> tagList = Lists.newArrayList();
}
