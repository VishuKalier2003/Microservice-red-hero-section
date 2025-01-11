package site.webred.service;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import site.webred.model.TwoHero;
import site.webred.repo.TwoRepo;
import site.webred.utility.ResponseData;

@Service
public class TwoService {
    @Autowired
    private TwoRepo twoRepo;
    @Autowired
    private WebRedService webRedService;
    @Autowired
    private PaneService paneService;

    @PostConstruct
    public void init() {
        TwoHero.index = 1;
    }

    public void reset() {
        TwoHero.index = 1;
    }

    public String generateKey() {
        // Generating the dataKey
        String instant = TwoHero.index + "TwoHero" + Instant.now();
        instant = instant.replace('-', 'X');
        instant = instant.replace('.', 'X');
        instant = instant.replace(':', 'X');
        TwoHero.index++; // Incrementing navbar index
        return instant;
    }

    public ResponseData createTwoHeroSection(String tag) {
        TwoHero twoHero = new TwoHero();
        twoHero.setDataKey(generateKey());
        twoHero.setHeroLink1("");
        twoHero.setHeroLink2("");
        twoHero.setHeroTitle("");
        twoHero.setHeroButton("");
        twoHero.setHeroContent("");
        twoHero.setColorSwitch(false);
        twoHero.setTag(tag);
        twoRepo.save(twoHero);
        webRedService.createTwoHero(twoHero);
        // This response data will be used to create the entry in pane
        return new ResponseData(twoHero.getDataKey(), twoHero.getTag(), twoHero.getCustomName());
    }

    public TwoHero getTwoHeroData(String dataKey) {
        return twoRepo.findById(dataKey).orElse(null);
    }

    public String updateTwoHero(TwoHero twoHero) {
        String uuid = twoHero.getDataKey();
        TwoHero previous = twoRepo.findById(uuid).orElseThrow(() -> new IllegalArgumentException("Navbar not found"));
        if (twoHero.getHeroLink1() == null || twoHero.getHeroLink1().isEmpty())
            twoHero.setHeroLink1(previous.getHeroLink1());
        if (twoHero.getHeroLink2() == null || twoHero.getHeroLink2().isEmpty())
            twoHero.setHeroLink2(previous.getHeroLink2());
        if (twoHero.getHeroTitle() == null || twoHero.getHeroTitle().isEmpty())
            twoHero.setHeroTitle(previous.getHeroTitle());
        if (twoHero.getHeroContent() == null || twoHero.getHeroContent().isEmpty())
            twoHero.setHeroContent(previous.getHeroContent());
        if (twoHero.getHeroButton() == null || twoHero.getHeroButton().isEmpty())
            twoHero.setHeroButton(previous.getHeroButton());
        if (twoHero.getCustomName() == null || twoHero.getCustomName().isEmpty())
            twoHero.setCustomName(previous.getCustomName());
        // Since component will be created then updated, the dataKey of the component can be found in pane
        twoHero.setTag(paneService.getPaneTag(uuid)); // Setting the Navbar Black tag as specified
        twoRepo.save(twoHero); // Imp: Service coupling
        webRedService.createTwoHero(twoHero); // Updating the same navbar in template document
        return twoHero.getDataKey();
    }

    public String deleteTwoHero(String dataKey) {
        // Deleting the entry from pane, repo and template
        twoRepo.deleteById(dataKey);
        webRedService.deleteFromWebred(dataKey);
        paneService.removePane(dataKey);
        return dataKey;
    }

    public String emptyTwoHero() {
        twoRepo.deleteAll();
        webRedService.clearTagEntries(new HashSet<>(Arrays.asList("Contain", "Cover")));
        paneService.clearPaneTags(new HashSet<>(Arrays.asList("Contain", "Cover")));
        return "Two Hero emptied";
    }

    public List<TwoHero> getAllHero() {
        return twoRepo.findAll();
    }

    public Boolean findColorSwitchValue(String dataKey) {
        if(getTwoHeroData(dataKey) != null)
            return twoRepo.findById(dataKey).get().isColorSwitch();
        return null;
    }
}
