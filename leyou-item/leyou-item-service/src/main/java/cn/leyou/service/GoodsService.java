package cn.leyou.service;



import cn.leyou.item.pojo.dto.BrandDto;
import cn.leyou.item.pojo.dto.SkuDTO;
import cn.leyou.item.pojo.dto.SpuDTO;
import cn.leyou.item.pojo.dto.SpuDetailDTO;

import java.util.List;

public interface GoodsService {
    List<BrandDto> queryBrandByCategoryId(Long id);

    void add(SpuDTO spuDTO);

    void downGoods(Long id, boolean saleable);

    SpuDetailDTO query(Long id);

    List<SkuDTO> querySkuBySpuId(Long spuId);
}
