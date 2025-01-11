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

import site.webred.model.OneHero;
import site.webred.service.OneService;
import site.webred.utility.ResponseData;

@RestController
@RequestMapping("one-hero")
public class OneControl {
    @Autowired
    private OneService oneService;
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/create")
    public ResponseEntity<?> createOneHero(@RequestParam String tag) {
        ResponseData paneInput = oneService.createOneHeroSection(tag);
        final String ControllerForDOM = String.format("http://localhost:8081/dom/create/primary?dataKey=%s&tag=%s",paneInput.getDataKey(),paneInput.getTag());
        return ResponseEntity.ok().body(restTemplate.postForEntity(ControllerForDOM, null, String.class));
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateOneHero(@RequestBody OneHero oneHero) {
        return ResponseEntity.ok().body(oneService.updateOneHero(oneHero));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteOneHero(@RequestParam String dataKey) {
        restTemplate.delete(String.format("http://localhost:8081/dom/delete/nodeByEntry?dataKey=%s",dataKey));
        return ResponseEntity.ok().body(oneService.deleteOneHero(dataKey));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllHero() {
        return ResponseEntity.ok().body(oneService.getAllOneHero());
    }

    @GetMapping("/color")
    public ResponseEntity<?> getColorSwitchValue(@RequestParam String dataKey) {
        return ResponseEntity.ok().body(oneService.findColorSwitchValue(dataKey));
    }
}
