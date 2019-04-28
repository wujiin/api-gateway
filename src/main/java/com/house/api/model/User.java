package com.house.api.model;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@Data
@ToString
public class User {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private String aboutme;
    private String passwd;
    private String confirmPasswd;
    private Integer type;
    private Date createTime;
    private Integer enable;
    private String avatar;

    @JsonIgnore
    private transient  MultipartFile avatarFile;
    private String newPassword;
    private String key;
    private Long agencyId;
    private String token;
    private String enableUrl;
    private String agencyName;
}
