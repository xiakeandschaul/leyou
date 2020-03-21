package cn.leyou.exception;


import cn.leyou.enums.ExceptionEnum;
import lombok.Getter;

/**
 * @version V1.0
 * @author: 侠客
 * @date: 2019/10/11 21:10
 * @description:
 */
@Getter
public class LyException extends RuntimeException {
    private int status;

    public LyException(ExceptionEnum em, Throwable cause) {
        super(em.getMessage(), cause);
        this.status = em.getStatus();
    }

    public LyException(ExceptionEnum em) {
        super(em.getMessage());
        this.status = em.getStatus();
    }
}
