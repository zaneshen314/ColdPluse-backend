package com.oocl.ita.web.domain.bo;

import com.oocl.ita.web.domain.po.SysUser;
import lombok.Data;

@Data
public class RegisterBody {

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
    private String sex;

    /**
     * 密码
     */
    private String password;

    public SysUser toEntity() {
        SysUser sysUser = new SysUser();
        sysUser.setNickName(nickName);
        sysUser.setEmail(email);
        sysUser.setPhonenumber(phonenumber);
        sysUser.setSex(sex);
        sysUser.setPassword(password);
        sysUser.setStatus("0");
        return sysUser;
    }
}
