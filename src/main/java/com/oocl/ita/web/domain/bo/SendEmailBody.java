package com.oocl.ita.web.domain.bo;

import lombok.Data;
import lombok.Getter;

@Data
public class SendEmailBody {
    /**
     * 接收邮箱
     */
    private String email;
}
