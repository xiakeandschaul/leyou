package cn.leyou.service.impl;

import cn.leyou.enums.ExceptionEnum;
import cn.leyou.exception.LyException;
import cn.leyou.item.pojo.Category;
import cn.leyou.mapper.CategoryMapper;
import cn.leyou.service.CategoryService;
import cn.leyou.utils.BeanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<Category> queryCategoryByPId(Long pid) {
        Category category = new Category();
        category.setParentId(pid);
        return categoryMapper.select(category);
    }

    /***
     * description: 根据分类id集合查询分类
     * create time: 2020/3/26 20:04
     * @param cids:分类集合id
     * @return java.util.List<cn.leyou.item.pojo.Category>
     */
    @Override
    public List<Category> queryCategoryBycids(List<Long> cids) {
        List<Category> categories = categoryMapper.selectByIdList(cids);
        if (CollectionUtils.isEmpty(categories)) {
            throw new LyException(ExceptionEnum.DATA_NOT_FOUND);
        }
        return categories;
    }

    /**
     * description: TODO
     * create time: 2020/3/23 1:30
     *
     * @param category
     * @return void
     */
    @Override
    public void addCategory(@RequestBody Category category) {
        int count = categoryMapper.insertSelective(category);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String time = dateFormat.format(date);
        category.setCreateTime(time);
        if (count != 1) {
            throw new LyException(ExceptionEnum.DATA_INSERT_ERROR);
        }
    }

    @Override
    public void deleteCategory(Long id) {
        int count = categoryMapper.deleteByPrimaryKey(id);
        if (count != 1) {
            throw new LyException(ExceptionEnum.DATA_DELETE_ERROR);
        }
    }

    @Override
    public List<Category> queryCategoryByBrand(Long bid) {
        List<Category> categories = categoryMapper.queryCategoryByBrand(bid);
        if (CollectionUtils.isEmpty(categories)) {
            throw new LyException(ExceptionEnum.DATA_NOT_FOUND);
        }
        return null;
    }
}
