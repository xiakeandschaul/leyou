package cn.leyou.controller;

import cn.leyou.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author ：xiaKe
 * @date ：Created in 2020/3/23 19:49
 * @description：
 */
@RestController
@RequestMapping
public class UploadController {
    @Autowired
    private UploadService uploadService;

    @PostMapping("/image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(uploadService.uploadImage(file));
    }

    @GetMapping("/signature")
    public ResponseEntity<Map<String, Object>> getAliSignature() {
        Map<String, Object> map = uploadService.getSignature();
        return ResponseEntity.ok(map);
    }
}
