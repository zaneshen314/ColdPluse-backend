package com.oocl.ita.web.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回对象
 */

@Data
@NoArgsConstructor
public class RespBean<T> {

    private long code;
    private String message;
    private T data;

    /**
     * 成功返回结果
     * @return
     */
    public static <T> RespBean<T> success() {
        return new RespBean<>(200L, "操作成功", null);
    }

    /**
     * 成功返回结果
     * @return
     */
    public static <T> RespBean<T> success(T data) {
        return new RespBean<>(200L, "操作成功", data);
    }

    /**
     * 成功返回结果
     *
     * @param message
     * @return
     */
    public static <T> RespBean<T> success(String message, T data) {
        return new RespBean<>(200L, message, data);
    }


    /**
     * 失败返回结果
     *
     * @param message
     * @return
     */
    public static <T> RespBean<T> error(String message) {
        return new RespBean<>(500L, message, null);
    }

    /**
     * 失败返回结果
     *
     * @param message
     * @param data
     * @return
     */
    public static <T> RespBean<T> error(String message, T data) {
        return new RespBean<>(500L, message, data);
    }

    /**
     * 失败返回结果
     *
     * @param message
     * @param code
     * @return
     */
    public static <T> RespBean<T> error(int code, String message) {
        return new RespBean<>(code, message, null);
    }

    public RespBean(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}

