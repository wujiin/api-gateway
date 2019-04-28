package com.house.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@AllArgsConstructor
public class HouseQueryReq implements Serializable {
    private House query;
    private Integer limit;
    private Integer offset;
}
