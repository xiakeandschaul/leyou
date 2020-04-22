package cn.leyou.service.impl;

import cn.leyou.enums.ExceptionEnum;
import cn.leyou.exception.LyException;
import cn.leyou.item.pojo.Brand;
import cn.leyou.item.pojo.dto.BrandDto;
import cn.leyou.mapper.BrandMapper;
import cn.leyou.service.BrandService;
import cn.leyou.utils.BeanHelper;
import cn.leyou.utils.PageResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public PageResult<Brand> queryBrand(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("name", "%" + key + "%").orEqualTo("letter", key);
        }
        PageHelper.startPage(page, rows);
        if (StringUtils.isNotBlank(sortBy)) {
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        }
        List<Brand> brands = brandMapper.selectByExample(example);
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

    /**
     * description: TODO
     * create time: 2020/3/21 17:06
     *
     * @param brandDto:
     * @param cids:
     * @return void
     */
    @Override
    @Transactional
    public void addBrand(BrandDto brandDto, List<Long> cids) {
        Brand brand = BeanHelper.copyProperties(brandDto, Brand.class);
        int count = brandMapper.insertSelective(brand);
        if (1 != count) {
            throw new LyException(ExceptionEnum.DATA_INSERT_ERROR);
        }
        //将cids插入到关联表中
        count = brandMapper.insertBrandAndCategory(brand.getId(),cids);
        if (cids.size() != count) {
            throw new LyException(ExceptionEnum.DATA_INSERT_ERROR);
        }
    }

    @Override
    @Transactional
    public void updateBrand(BrandDto brandDto, List<Long> cids) {
        Brand brand = BeanHelper.copyProperties(brandDto, Brand.class);
        int count = brandMapper.updateByPrimaryKeySelective(brand);
        if (count != 1) {
            throw new LyException(ExceptionEnum.DATA_UPLOAD_ERROR);
        }
//        先删除再增加
        brandMapper.deleteBrandAndCategory(brand.getId());
        count = brandMapper.insertBrandAndCategory(brand.getId(), cids);
        if (count != cids.size()) {
            throw new LyException(ExceptionEnum.DATA_UPLOAD_ERROR);
        }
    }

    @Override
    @Transactional
    public void delete(long id) {
        int count = brandMapper.deleteByPrimaryKey(id);
        if (count != 1) {
            throw new LyException(ExceptionEnum.DATA_DELETE_ERROR);
        }
        brandMapper.deleteBrandAndCategory(id);
    }

    @Override
    public BrandDto queryBrandById(Long id) {
        Brand brand = brandMapper.selectByPrimaryKey(id);
        BrandDto brandDto = BeanHelper.copyProperties(brand, BrandDto.class);
        return brandDto;
    }

    public List<BrandDto> queryBrandByCategoryId(Long id) {
        List<Brand> brands = brandMapper.queryBrandByCategoryId(id);
        if (CollectionUtils.isEmpty(brands)) {
            throw new LyException(ExceptionEnum.DATA_NOT_FOUND);
        }
        List<BrandDto> brandDtos = BeanHelper.copyWithCollection(brands, BrandDto.class);
        return brandDtos;
    }
}
