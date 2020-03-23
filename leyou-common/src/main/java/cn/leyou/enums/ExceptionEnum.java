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
    DATA_INSERT_ERROR(500, "数据插入失败"),
    DATA_DELETE_ERROR(500, "数据删除失败"),
    IMAGE_UPLOAD_ERROR(500, "图片保存失败"),
    UPLOAD_IMAGE_FAIL(500, "图片上传失败"),
    INVALID_FILE_TYPE(403, "图片格式不正确");
    private int status;
    private String message;

    ExceptionEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
