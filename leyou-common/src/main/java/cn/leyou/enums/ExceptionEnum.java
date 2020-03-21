package cn.leyou.enums;

import lombok.Getter;

/**
 * @version V1.0
 * @author: 侠客
 * @date: 2019/10/11 21:07
 * @description:
 */
@Getter
public enum ExceptionEnum {
    DATA_TRANSFER_ERROR(500, "服务器异常"),
    DATA_INSERT_ERROR(500,"数据插入失败")
    ;
    private int status;
    private String message;

    ExceptionEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
