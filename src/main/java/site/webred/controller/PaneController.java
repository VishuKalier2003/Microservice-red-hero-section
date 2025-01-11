package site.webred.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import site.webred.service.PaneService;

@RestController
@RequestMapping("pane")
public class PaneController {
    @Autowired
    private PaneService paneService;

    @GetMapping("/all")
    public ResponseEntity<Object> printAllPane() {
        return ResponseEntity.ok().body(paneService.allPane());
    }

    @GetMapping("/get/tag")
    public ResponseEntity<Object> getPaneValue(@RequestParam String dataKey) {
        return ResponseEntity.ok().body(paneService.getPaneTag(dataKey));
    }

    @GetMapping("/search/tag")
    public ResponseEntity<Object> searchPaneValue(@RequestParam String dataKey) {
        return ResponseEntity.ok().body(paneService.searchPaneTag(dataKey));
    }
}
