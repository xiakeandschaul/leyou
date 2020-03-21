package cn.leyou.utils;


import cn.leyou.enums.ExceptionEnum;
import cn.leyou.exception.LyException;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class BeanHelper {

    public static <T> T copyProperties(Object source, Class<T> target){
        try {
            T t = target.newInstance();
            BeanUtils.copyProperties(source, t);
            return t;
        } catch (Exception e) {
            throw new LyException(ExceptionEnum.DATA_TRANSFER_ERROR);
        }
    }

    public static <T> List<T> copyWithCollection(List<?> sourceList, Class<T> target){
        try {
            return sourceList.stream().map(s -> copyProperties(s, target)).collect(Collectors.toList());
        } catch (Exception e) {
            throw new LyException(ExceptionEnum.DATA_TRANSFER_ERROR);
        }
    }

    public static <T> Set<T> copyWithCollection(Set<?> sourceList, Class<T> target){
        try {
            return sourceList.stream().map(s -> copyProperties(s, target)).collect(Collectors.toSet());
        } catch (Exception e) {
            throw new LyException(ExceptionEnum.DATA_TRANSFER_ERROR);
        }
    }
}
