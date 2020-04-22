package cn.leyou.service;

import cn.leyou.item.pojo.dto.SpuDTO;
import cn.leyou.utils.PageResult;

public interface SpuService {
    PageResult<SpuDTO> pageQuery(String key, Boolean saleable, Integer page, Integer rows);

}
