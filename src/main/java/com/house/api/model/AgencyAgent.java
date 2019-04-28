package com.house.api.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description 机构联系人实体
 * @Author wujin
 **/
@Data
@ToString
public class AgencyAgent implements Serializable {
    private Integer id;
    private Long agentId;
    private Integer agencyId;
}
