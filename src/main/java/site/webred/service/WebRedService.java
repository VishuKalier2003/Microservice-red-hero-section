package site.webred.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import site.webred.global.Converter;
import site.webred.model.OneHero;
import site.webred.model.TwoHero;
import site.webred.model.WebRed;
import site.webred.repo.WebRedRepo;

@Service
public class WebRedService {
    @Autowired
    private WebRedRepo webRedRepo;
    @Autowired
    private Converter converter;        // Converting objects to WebRed instances

    // Creating the data for One Image Hero Section
    public String createOneHero(OneHero oneHero) {
        WebRed webred = new WebRed();
        String dataKey = oneHero.getDataKey(), heroLink = oneHero.getHeroLink(), heroTitle = oneHero.getHeroTitle();
        String heroButton = oneHero.getHeroButton(), heroContent = oneHero.getHeroContent(), tag = oneHero.getTag();
        boolean switchValue = oneHero.isColorSwitch();
        converter.defineOneHero(webred, dataKey, heroLink, heroTitle, heroContent, heroButton, tag, switchValue);
        webRedRepo.save(webred);
        return dataKey;
    }

    // Creating the data for Two Image Hero Section
    public String createTwoHero(TwoHero twoHero) {
        WebRed webRed = new WebRed();
        String dataKey = twoHero.getDataKey(), heroLink1 = twoHero.getHeroLink1(), heroLink2 = twoHero.getHeroLink2();
        String heroTitle = twoHero.getHeroTitle(), heroContent = twoHero.getHeroContent(), heroButton = twoHero.getHeroButton();
        String tag = twoHero.getTag();
        boolean switchValue = twoHero.isColorSwitch();
        converter.defineTwoHero(webRed, dataKey, heroLink1, heroLink2, heroTitle, heroContent, heroButton, tag, switchValue);
        webRedRepo.save(webRed);
        return dataKey;
    }

    public String clearTagEntries(Set<String> tags) {
        List<WebRed> list = showAll();
        List<WebRed> removed = new ArrayList<>();
        for(WebRed data : list)
            if(tags.contains(data.getTag()))
                removed.add(data);
        list.removeAll(removed);
        webRedRepo.saveAll(list);
        return "Entry cleared";
    }

    public void deleteFromWebred(String dataKey) {
        webRedRepo.deleteById(dataKey); // Deleting an entry
        return;
    }

    public List<WebRed> showAll() { // Extracting the entire template
        return webRedRepo.findAll();
    }

    public String deleteAll() { // Clearing the entire template
        webRedRepo.deleteAll();
        return "All data deleted from Template";
    }
}
