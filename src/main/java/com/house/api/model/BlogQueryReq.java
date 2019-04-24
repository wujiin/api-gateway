package com.house.api.model;


import lombok.Data;
import lombok.ToString;

/**
 * @Description 百科查询实体
 * @Author wujin
 **/
@Data
@ToString
public class BlogQueryReq {
    private Blog blog;
    private Integer limit;
    private Integer offset;
}
