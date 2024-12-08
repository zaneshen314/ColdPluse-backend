package com.oocl.ita.web.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回对象
 */

@Data
@NoArgsConstructor
public class RespBean {

    private long code;
    private String message;
    private Object data;

    /**
     * 成功返回结果
     * @return
     */
    public static RespBean success() {
        return new RespBean(200L, "操作成功", null);
    }

    /**
     * 成功返回结果
     * @return
     */
    public static RespBean success(Object data) {
        return new RespBean(200L, "操作成功", data);
    }

    /**
     * 成功返回结果
     *
     * @param message
     * @return
     */
    public static RespBean success(String message, Object data) {
        return new RespBean(200L, message, data);
    }


    /**
     * 失败返回结果
     *
     * @param message
     * @return
     */
    public static RespBean error(String message) {
        return new RespBean(500L, message, null);
    }

    /**
     * 失败返回结果
     *
     * @param message
     * @param data
     * @return
     */
    public static RespBean error(String message, Object data) {
        return new RespBean(500L, message, data);
    }

    /**
     * 失败返回结果
     *
     * @param message
     * @param code
     * @return
     */
    public static RespBean error(int code, String message) {
        return new RespBean(code, message, null);
    }

    public RespBean(long code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}

