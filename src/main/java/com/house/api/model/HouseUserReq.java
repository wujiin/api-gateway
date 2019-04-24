package com.house.api.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class HouseUserReq {
    private Long houseId;
    private Long userId;
    private Integer bindType;
    private boolean unBind;
}
