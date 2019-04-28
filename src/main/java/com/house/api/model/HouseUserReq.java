package com.house.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
public class HouseUserReq implements Serializable {
    private Long houseId;
    private Long userId;
    private Integer bindType;
    private boolean unBind;
}
