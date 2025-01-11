package site.webred.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import site.webred.service.WebRedService;

@RestController
@RequestMapping("/template")
public class TemplateControl {
    @Autowired
    private WebRedService webRedService;

    @GetMapping("/all")
    private ResponseEntity<?> getAllData() {
        return ResponseEntity.ok().body(webRedService.showAll());
    }

    @DeleteMapping("/all")
    public ResponseEntity<?> clearData() {
        return ResponseEntity.ok().body(webRedService.deleteAll());
    }
}
