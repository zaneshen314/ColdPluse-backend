package com.oocl.ita.web.domain.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;


/**
 * 用户对象 sys_user
 *

 */
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "sys_user")
@Data
//@GenericGenerator(name = "uuid2", strategy = "com.common.utils.IDUtils")
public class SysUser {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Id
    @Column(nullable = false, length = 19, columnDefinition = "char")
//    @GeneratedValue(strategy = GenerationType.AUTO)
    private String userId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phonenumber;

    /**
     * 用户性别
     */
    @Column(columnDefinition = "char(1)")
    private String sex;

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

    public SysUser() {

    }

    public static boolean isAdmin(String email) {
        return StringUtils.isNotEmpty(email) && "admin".equals(email);
    }


    @JsonIgnore
    @JsonProperty
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "userId='" + userId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", sex='" + sex + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
