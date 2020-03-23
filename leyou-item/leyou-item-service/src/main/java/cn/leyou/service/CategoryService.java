package cn.leyou.service;

import cn.leyou.item.pojo.Category;

import java.util.List;

public interface CategoryService {
    List<Category> queryCategoryByPId(Long pid);

    void addCategory(Category category);

    void deleteCategory(Long id);
}
