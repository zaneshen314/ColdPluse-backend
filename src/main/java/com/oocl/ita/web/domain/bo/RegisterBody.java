package com.oocl.ita.web.domain.bo;

import com.oocl.ita.web.domain.po.User;
import lombok.Data;

@Data
public class RegisterBody {

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
    private String password;

    public User toEntity() {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setStatus("0");
        user.setCumulatedPoint(0);
        return user;
    }
}
