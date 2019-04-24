package com.house.api.model;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class Recommend {
    private Long id;
    private Long houseId;
    private Integer type;
    private Integer userId;
    private Integer district;
    private Date createTime;
}
