package com.oocl.ita.web.domain.po.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;


/**
 * 用户对象 sys_user
 *

 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "user")
@Data
//@GenericGenerator(name = "uuid2", strategy = "com.common.utils.IDUtils")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户昵称
     */
    private String name;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 密码
     */
    @Column(updatable = false)
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    @Column(columnDefinition = "char(1)")
    private String status;

    private Integer cumulatedPoint;

    public User() {

    }

    public static boolean isAdmin(String email) {
        return StringUtils.isNotEmpty(email) && "admin".equals(email);
    }


    @JsonIgnore
    @JsonProperty
    public String getPassword() {
        return password;
    }
}
