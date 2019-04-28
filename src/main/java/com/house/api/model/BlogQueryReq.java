package com.house.api.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description 百科查询实体
 * @Author wujin
 **/
@Data
@ToString
@AllArgsConstructor
public class BlogQueryReq implements Serializable {
    private Blog blog;
    private Integer limit;
    private Integer offset;
}
