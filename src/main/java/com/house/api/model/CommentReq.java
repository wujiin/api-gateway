package com.house.api.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CommentReq {
    private Long userId;
    private Long houseId;
    private Integer blogId;
    private String content;
    private Integer type; //1-房产，2-博客百科
    private Integer size; //获取多少评论
}
