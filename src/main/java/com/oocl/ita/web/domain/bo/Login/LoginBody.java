package com.oocl.ita.web.domain.bo.Login;

import lombok.Data;

/**
 * 用户登录对象
 */
@Data
public class LoginBody
{
    /**
     * 用户名
     */
    private String email;

    /**
     * 用户密码
     */
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
