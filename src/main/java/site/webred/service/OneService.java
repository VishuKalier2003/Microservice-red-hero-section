package site.webred.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import site.webred.model.OneHero;
import site.webred.repo.OneRepo;
import site.webred.utility.ResponseData;

@Service
public class OneService {
    @Autowired
    private OneRepo oneRepo;
    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        OneHero.index = 1;
    }

    public void reset() {
        OneHero.index = 1;
    }

    public String generateKey() {
        // Generating the dataKey
        String instant = OneHero.index+"OneHero"+Instant.now();
        instant = instant.replace('-', 'X');
        instant = instant.replace('.', 'X');
        instant = instant.replace(':', 'X');
        OneHero.index++; // Incrementing navbar index
        return instant;
    }

    public ResponseData createOneHeroSection(String tag) {
        OneHero oneHero = new OneHero();
        oneHero.setDataKey(generateKey()); oneHero.setHeroLink(""); oneHero.setHeroTitle("");
        oneHero.setHeroButton(""); oneHero.setHeroContent("");
        oneHero.setColorSwitch(false); oneHero.setTag(tag); oneRepo.save(oneHero);
        final String ControllerForTemplate = String.format("http://localhost:8081/template/create/one-hero");
        restTemplate.postForEntity(ControllerForTemplate, oneHero, String.class);
        return new ResponseData(oneHero.getDataKey(), oneHero.getTag(), oneHero.getCustomName());
    }

    public OneHero getOneHeroData(String dataKey) {
        return oneRepo.findById(dataKey).orElse(null);
    }

    public String updateOneHero(OneHero oneHero) {
        String uuid = oneHero.getDataKey();
        OneHero previous = oneRepo.findById(uuid).orElseThrow(() -> new IllegalArgumentException("Navbar not found"));
        if (oneHero.getHeroLink() == null || oneHero.getHeroLink().isEmpty())
            oneHero.setHeroLink(previous.getHeroLink());
        if (oneHero.getHeroTitle() == null || oneHero.getHeroTitle().isEmpty())
            oneHero.setHeroTitle(previous.getHeroTitle());
        if (oneHero.getHeroContent() == null || oneHero.getHeroContent().isEmpty())
            oneHero.setHeroContent(previous.getHeroContent());
        if (oneHero.getHeroButton() == null || oneHero.getHeroButton().isEmpty())
            oneHero.setHeroButton(previous.getHeroButton());
        if (oneHero.getCustomName() == null || oneHero.getCustomName().isEmpty())
            oneHero.setCustomName(previous.getCustomName());
        // Since component will be created then updated, the dataKey of the component
        oneRepo.save(oneHero);
        final String ControllerForTemplate = String.format("http://localhost:8081/template/create/one-hero");
        restTemplate.postForEntity(ControllerForTemplate, oneHero, String.class);
        return oneHero.getDataKey();
    }

    public String deleteOneHero(String dataKey) {
        // Deleting from both webred and oneHero repo
        oneRepo.deleteById(dataKey);
        final String ControllerForTemplate = String.format("http://localhost:8081/template/delete/clear?dataKey=%s",dataKey);
        restTemplate.delete(ControllerForTemplate);
        return dataKey;
    }

    public String emptyOneHero() {
        oneRepo.deleteAll();
        return "All Respective Tags deleted";
    }

    public List<OneHero> getAllOneHero() {
        return oneRepo.findAll();
    }

    public Boolean findColorSwitchValue(String dataKey) {
        if (getOneHeroData(dataKey) != null)
            return oneRepo.findById(dataKey).get().isColorSwitch();
        return null;
    }
}
