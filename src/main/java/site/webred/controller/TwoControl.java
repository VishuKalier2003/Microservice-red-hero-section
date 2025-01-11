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
import org.springframework.web.client.RestTemplate;

import site.webred.model.TwoHero;
import site.webred.service.TwoService;
import site.webred.utility.ResponseData;

@RestController
@RequestMapping("two-hero")
public class TwoControl {
    @Autowired
    private TwoService twoService;
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/create")
    public ResponseEntity<String> createTwoSectionHero(@RequestParam String tag) {
        ResponseData data = twoService.createTwoHeroSection(tag);
        final String ControllerForDOM = String.format("http://localhost:8081/dom/create/primary?dataKey=%s&tag=%s",data.getDataKey(),data.getTag());
        restTemplate.postForEntity(ControllerForDOM, null, String.class);
        return ResponseEntity.ok().body(data.getDataKey());
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
        restTemplate.delete(String.format("http://localhost:8081/dom/delete/nodeByEntry?dataKey=%s",dataKey));
        return ResponseEntity.ok().body(twoService.deleteTwoHero(dataKey));
    }

    @GetMapping("/color")
    public ResponseEntity<Object> getColorValue(@RequestParam String dataKey) {
        return ResponseEntity.ok().body(twoService.findColorSwitchValue(dataKey));
    }
}
