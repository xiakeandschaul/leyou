package cn.leyou.mapper;

import cn.leyou.item.pojo.Category;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface CategoryMapper extends Mapper<Category>, SelectByIdListMapper<Category,Long> {
    @Select("SELECT tc.id, tc.`name`, tc.parent_id, tc.is_parent, tc.sort " +
            "FROM tb_category_brand tcb LEFT JOIN tb_category tc ON " +
            "tcb.category_id = tc.id WHERE tcb.brand_id = #{bid}")
    List<Category> queryCategoryByBrand(Long bid);
}
