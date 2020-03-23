package cn.leyou.controller;

import cn.leyou.item.pojo.Category;
import cn.leyou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/of/parent")
    public ResponseEntity<List<Category>> queryCategoryByPId(@RequestParam(value = "pid") Long pid) {
        if (pid.longValue() < 0 || pid == null) {
            return ResponseEntity.badRequest().build();
        }
        List<Category> categories = categoryService.queryCategoryByPId(pid);
        if (CollectionUtils.isEmpty(categories)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addCategory(@RequestBody Category category) {
        category.setId(null);
        categoryService.addCategory(category);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/delete")
    public ResponseEntity<Void> deleteCategory(@RequestParam Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }
}
