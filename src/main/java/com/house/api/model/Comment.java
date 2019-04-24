package com.house.api.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @Description 评论实体
 * @Author wujin
 **/
@Data
@ToString
public class Comment {
    private Long id;
    private String content;
    private Long houseId;
    private Date createTime;
    private Integer blogId;
    private Integer type;
    private Long userId;
    private String userName;
    private String avatar;
}
