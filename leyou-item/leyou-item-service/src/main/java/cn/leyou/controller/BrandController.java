package cn.leyou.controller;

import cn.leyou.item.pojo.Brand;
import cn.leyou.item.pojo.dto.BrandDto;
import cn.leyou.service.BrandService;
import cn.leyou.utils.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("/page")
    public ResponseEntity<PageResult<Brand>> queryBrand(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", required = false) Boolean desc
    ) {
        PageResult<Brand> brandPageResult = brandService.queryBrand(key, page, rows, sortBy, desc);
        if (CollectionUtils.isEmpty(brandPageResult.getItems())) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(brandPageResult);
    }

    @PostMapping
    public ResponseEntity<Void> addBrand(
            BrandDto brandDto,
            @RequestParam(value = "cids", required = false) List<Long> cids
    ) {
        brandService.addBrand(brandDto, cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateBrand(BrandDto brandDto, @RequestParam("cids") List<Long> cids) {
        brandService.updateBrand(brandDto, cids);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/delete")
    public ResponseEntity<Void> delete(@RequestParam("id") Long id) {
        brandService.delete(id);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/of/category")
    public ResponseEntity<List<BrandDto>> queryBrandByCategoryId(@RequestParam("id") Long id) {
        return ResponseEntity.ok(brandService.queryBrandByCategoryId(id));
    }

}
