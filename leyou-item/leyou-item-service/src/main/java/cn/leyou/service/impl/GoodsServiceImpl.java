package cn.leyou.service.impl;


import cn.leyou.enums.ExceptionEnum;
import cn.leyou.exception.LyException;
import cn.leyou.item.pojo.Sku;
import cn.leyou.item.pojo.Spu;
import cn.leyou.item.pojo.SpuDetail;
import cn.leyou.item.pojo.dto.BrandDto;
import cn.leyou.item.pojo.dto.SkuDTO;
import cn.leyou.item.pojo.dto.SpuDTO;
import cn.leyou.item.pojo.dto.SpuDetailDTO;
import cn.leyou.mapper.SkuMapper;
import cn.leyou.mapper.SpuDetailMapper;
import cn.leyou.mapper.SpuMapper;
import cn.leyou.service.BrandService;
import cn.leyou.service.GoodsService;
import cn.leyou.utils.BeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：xiaKe
 * @date ：Created in 2020/4/7 20:05
 * @description：
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private BrandService brandService;

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Override
    public List<BrandDto> queryBrandByCategoryId(Long id) {
        return brandService.queryBrandByCategoryId(id);
    }

    @Transactional
    @Override
    public void add(SpuDTO spuDTO) {
        Spu spu = BeanHelper.copyProperties(spuDTO, Spu.class);
        spu.setId(null);
        spu.setCreateTime(null);
        spu.setSaleable(null);
        int count = spuMapper.insertSelective(spu);
        if (1 != count) {
            throw new LyException(ExceptionEnum.DATA_INSERT_ERROR);
        }
        SpuDetailDTO spuDetailDTO = spuDTO.getSpuDetail();
        SpuDetail spuDetail = BeanHelper.copyProperties(spuDetailDTO, SpuDetail.class);
        spuDetail.setSpuId(spu.getId());
        count = spuDetailMapper.insertSelective(spuDetail);
        if (1 != count) {
            throw new LyException(ExceptionEnum.DATA_INSERT_ERROR);
        }
        List<SkuDTO> skuDTOs= spuDTO.getSkus();
        List<Sku> skus = BeanHelper.copyWithCollection(skuDTOs, Sku.class);
        skus.forEach(sku -> sku.setSpuId(spu.getId()));
        count = skuMapper.insertList(skus);
        if (skus.size() != count) {
            throw new LyException(ExceptionEnum.DATA_INSERT_ERROR);
        }
    }

    @Transactional
    @Override
    public void downGoods(Long id, boolean saleable) {
        Spu spu = new Spu();
        spu.setId(id);
        spu.setSaleable(saleable);
        int count = spuMapper.updateByPrimaryKeySelective(spu);
        if (1 != count) {
            throw new LyException(ExceptionEnum.DATA_UPLOAD_ERROR);
        }
        Sku sku = new Sku();
        sku.setEnable(spu.getSaleable());
        Example example = new Example(Sku.class);
        example.createCriteria().andEqualTo("spuId", spu.getId());
        count = skuMapper.updateByExampleSelective(sku, example);
        int size = skuMapper.selectCountByExample(example);
        if (size != count) {
            throw new LyException(ExceptionEnum.DATA_UPLOAD_ERROR);
        }
    }

    @Override
    public SpuDetailDTO query(Long id) {
        SpuDetail spuDetail = spuDetailMapper.selectByPrimaryKey(id);
        if (null == spuDetail) {
            throw new LyException(ExceptionEnum.DATA_NOT_FOUND);
        }
        return BeanHelper.copyProperties(spuDetail,SpuDetailDTO.class);
    }

    @Override
    public List<SkuDTO> querySkuBySpuId(Long spuId) {
        Sku sku = new Sku();
        sku.setSpuId(spuId);
        List<Sku> skus = skuMapper.select(sku);
        if (CollectionUtils.isEmpty(skus)) {
            throw new LyException(ExceptionEnum.DATA_NOT_FOUND);
        }
        return BeanHelper.copyWithCollection(skus,SkuDTO.class);
    }
}
