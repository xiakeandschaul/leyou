package cn.leyou.service.impl;

import cn.leyou.enums.ExceptionEnum;
import cn.leyou.exception.LyException;
import cn.leyou.item.pojo.Category;
import cn.leyou.item.pojo.Spu;
import cn.leyou.item.pojo.dto.SpuDTO;
import cn.leyou.mapper.SpuMapper;
import cn.leyou.service.BrandService;
import cn.leyou.service.CategoryService;
import cn.leyou.service.SpuService;
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
import java.util.stream.Collectors;

/**
 * @author ：xiaKe
 * @date ：Created in 2020/3/26 19:19
 * @description：
 */
@Service
public class SpuServiceImpl  implements SpuService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandService brandService;

    @Override
    public PageResult<SpuDTO> pageQuery(String key, Boolean saleable, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("name", "%" + key + "%");
        }
        if (saleable != null) {
            criteria.andEqualTo("saleable",saleable);
        }
//        按照默认时间排序
        example.setOrderByClause("update_time desc");
        List<Spu> spus = spuMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(spus)) {
            throw new LyException(ExceptionEnum.DATA_NOT_FOUND);
        }
        PageInfo<Spu> spuPageInfo = new PageInfo<>(spus);
        List<SpuDTO> spuDTOS = BeanHelper.copyWithCollection(spus, SpuDTO.class);
        spuDTOS.forEach(spuDTO -> {
            String categoryName = categoryService.queryCategoryBycids(spuDTO.getCategoryIds())
                    .stream()
                    .map(Category::getName)
                    .collect(Collectors.joining("/"));
            String brandName = brandService.queryBrandById(spuDTO.getBrandId())
                    .getName();
            spuDTO.setCategoryName(categoryName);
            spuDTO.setBrandName(brandName);
        });
        return new PageResult<SpuDTO>(spuPageInfo.getTotal(), spuDTOS);
    }

}
