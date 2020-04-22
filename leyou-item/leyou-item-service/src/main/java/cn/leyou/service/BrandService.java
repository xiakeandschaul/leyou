package cn.leyou.service;

import cn.leyou.item.pojo.Brand;
import cn.leyou.item.pojo.dto.BrandDto;
import cn.leyou.utils.PageResult;

import java.util.List;

public interface BrandService {
    PageResult<Brand> queryBrand(String key, Integer page, Integer rows, String sortBy, Boolean desc);

    void addBrand(BrandDto brandDto, List<Long> cids);

    void updateBrand(BrandDto brandDto, List<Long> cids);

    void delete(long id);

    BrandDto queryBrandById(Long id);

    List<BrandDto> queryBrandByCategoryId(Long id);

}
