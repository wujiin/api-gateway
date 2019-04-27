package com.house.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class CommentReq {
    private Long userId;
    private Long houseId;
    private Integer blogId;
    private String content;
    private Integer type; //1-房产评论，2-博客百科评论
    private Integer size; //获取多少评论
}
