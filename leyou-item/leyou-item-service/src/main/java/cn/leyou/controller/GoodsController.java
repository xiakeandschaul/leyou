package cn.leyou.controller;


import cn.leyou.item.pojo.dto.SkuDTO;
import cn.leyou.item.pojo.dto.SpuDTO;
import cn.leyou.item.pojo.dto.SpuDetailDTO;
import cn.leyou.service.GoodsService;
import cn.leyou.service.SpuService;
import cn.leyou.utils.PageResult;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：xiaKe
 * @date ：Created in 2020/3/26 19:10
 * @description：
 */
@RestController
public class GoodsController {
    @Autowired
    private SpuService spuService;

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<SpuDTO>> pageQuery(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows
    ) {
        return ResponseEntity.ok(spuService.pageQuery(key, saleable, page, rows));
    }

    @PostMapping("/goods")
    public ResponseEntity<Void> add(@RequestBody SpuDTO spuDTO) {
        goodsService.add(spuDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/spu/saleable")
    public ResponseEntity<Void> downGoods(@RequestParam("id") Long id, @RequestParam("saleable") boolean saleable) {
        goodsService.downGoods(id, saleable);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/spu/detail")
    public ResponseEntity<SpuDetailDTO> query(@RequestParam("id") Long id) {
        return ResponseEntity.ok(goodsService.query(id));
    }

    @GetMapping("/sku/of/spu")
    public ResponseEntity<List<SkuDTO>> querySkuBySpuId(@RequestParam("id") Long spuId) {
        return ResponseEntity.ok(goodsService.querySkuBySpuId(spuId));
    }
}
