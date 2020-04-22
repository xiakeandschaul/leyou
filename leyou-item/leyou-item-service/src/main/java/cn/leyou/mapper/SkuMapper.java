package cn.leyou.mapper;

import cn.leyou.item.pojo.Sku;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

public interface SkuMapper extends Mapper<Sku>, InsertListMapper<Sku> {
}
