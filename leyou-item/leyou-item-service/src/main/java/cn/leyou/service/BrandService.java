package cn.leyou.service;

import cn.leyou.item.pojo.Brand;
import cn.leyou.utils.PageResult;

public interface BrandService {
    PageResult<Brand> queryBrand(String key, Integer page, Integer rows, String sortBy, Boolean desc);
}
