package cn.leyou.mapper;

import cn.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {
    int insertBrandAndCategory(@Param("bid") Long id, @Param("cids") List<Long> cids);
}
