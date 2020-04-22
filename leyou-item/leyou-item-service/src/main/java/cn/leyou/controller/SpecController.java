package cn.leyou.controller;


import cn.leyou.item.pojo.SpecGroup;
import cn.leyou.item.pojo.dto.SpecGroupDTO;
import cn.leyou.item.pojo.dto.SpecParamDTO;
import cn.leyou.service.SpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：xiaKe
 * @date ：Created in 2020/3/25 16:49
 * @description：
 */
@RestController
@RequestMapping("/spec")
public class SpecController {
    @Autowired
    private SpecService specService;

    @GetMapping("/groups/of/category")
    public ResponseEntity<List<SpecGroupDTO>> querySpecGroup(@RequestParam("id") Long cid) {
        List<SpecGroupDTO> specGroups = specService.querySpecGroup(cid);
        return ResponseEntity.ok(specGroups);
    }

    @PostMapping("/group")
    public ResponseEntity<Void> add(@RequestBody SpecGroupDTO specGroupDTO) {
        specService.add(specGroupDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/group")
    public ResponseEntity<Void> update(@RequestBody SpecGroupDTO specGroupDTO) {
        specService.update(specGroupDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/group/{gid}")
    public ResponseEntity<Void> delete(@PathVariable("gid") Long gid) {
        specService.delete(gid);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/params")
    public ResponseEntity<List<SpecParamDTO>> querySpecParams(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "searching",required = false)Boolean searching
            ) {

        List<SpecParamDTO> specParamDTOs = specService.querySpecParams(gid,cid,searching);
        return ResponseEntity.ok(specParamDTOs);
    }

    @PostMapping("/param")
    public ResponseEntity<Void> addParams(@RequestBody SpecParamDTO specParamDTO) {
        specService.addParams(specParamDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/param")
    public ResponseEntity<Void> updateParams(@RequestBody SpecParamDTO specParamDTO) {
        specService.updateParam(specParamDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/param/{id}")
    public ResponseEntity<Void> deleteParams(@PathVariable("id") Long id) {
        specService.deleteParam(id);
        return ResponseEntity.ok().build();
    }
}
