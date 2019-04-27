package com.house.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class HouseUserReq {
    private Long houseId;
    private Long userId;
    private Integer bindType;
    private boolean unBind;
}
