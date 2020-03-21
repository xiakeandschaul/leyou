package cn.leyou.service;

import cn.leyou.dto.BrandDto;
import cn.leyou.item.pojo.Brand;
import cn.leyou.utils.PageResult;

import java.util.List;

public interface BrandService {
    PageResult<Brand> queryBrand(String key, Integer page, Integer rows, String sortBy, Boolean desc);

    void addBrand(BrandDto brandDto, List<Long> cids);
}
