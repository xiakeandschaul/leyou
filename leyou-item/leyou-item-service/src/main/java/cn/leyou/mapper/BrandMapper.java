package cn.leyou.mapper;

import cn.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {
    int insertBrandAndCategory(@Param("bid") Long id, @Param("cids") List<Long> cids);

    @Delete("DELETE from tb_category_brand WHERE brand_id = #{bid}")
    void deleteBrandAndCategory(Long bid);

    @Select("select b.id, b.name, b.letter, b.image from tb_brand b,tb_category_brand cb " +
            "where b.id=cb.brand_id and cb.category_id = #{id}")
    List<Brand> queryBrandByCategoryId(Long id);
}
