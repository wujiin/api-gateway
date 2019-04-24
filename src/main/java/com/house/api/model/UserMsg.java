package com.house.api.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class UserMsg {
    private Long id;
    private String msg;
    private Long userId;
    private Date createTime;
    private Long agentId;
    private Long houseId;
    private String email;
    private String userName;
}
