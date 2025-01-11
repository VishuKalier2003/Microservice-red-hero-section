package site.webred.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import site.webred.model.TwoHero;
import site.webred.service.PaneService;
import site.webred.service.TwoService;

@RestController
@RequestMapping("two-hero")
public class TwoControl {
    @Autowired
    private TwoService twoService;
    @Autowired
    private PaneService paneService;

    @PostMapping("/create")
    public ResponseEntity<String> createTwoSectionHero(@RequestParam String tag) {
        return ResponseEntity.ok().body(paneService.addPane(twoService.createTwoHeroSection(tag)));
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateTwoHeroSection(@RequestBody TwoHero twoHero) {
        return ResponseEntity.ok().body(twoService.updateTwoHero(twoHero));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTwoSectionHero() {
        return ResponseEntity.ok().body(twoService.getAllHero());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteEntry(@RequestParam String dataKey) {
        return ResponseEntity.ok().body(twoService.deleteTwoHero(dataKey));
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Object> deleteEntries() {
        return ResponseEntity.ok().body(twoService.emptyTwoHero());
    }

    @GetMapping("/color")
    public ResponseEntity<Object> getColorValue(@RequestParam String dataKey) {
        return ResponseEntity.ok().body(twoService.findColorSwitchValue(dataKey));
    }
}
