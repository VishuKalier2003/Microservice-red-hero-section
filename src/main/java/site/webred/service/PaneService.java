package site.webred.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import site.webred.model.KeyPane;
import site.webred.repo.PaneRepo;
import site.webred.utility.ResponseData;

@Service
public class PaneService {
    @Autowired      // Dependency Injection - providing objects instead of declaring them
    private PaneRepo paneRepo;

    @PostConstruct  // Imp - Invoked as a Constructor
    public void init() {
        ensurePaneDocumentExists();
    }

    private void ensurePaneDocumentExists() {
        // Creating a pane document if it does not exists
        Optional<KeyPane> keyPane = paneRepo.findById("pane");
        if (keyPane.isEmpty()) {    // This stores the DOM of the website created
            KeyPane newKeyPane = new KeyPane();
            newKeyPane.setId("pane");
            newKeyPane.setKeyList(new ArrayList<>());
            paneRepo.save(newKeyPane);
        }
    }

    public void initializeAfterReset() {
        ensurePaneDocumentExists();
    }

    public String addPane(ResponseData responseData) {
        // Adding pane data
        KeyPane keyPane = paneRepo.findById("pane").orElseThrow(() -> new IllegalStateException("Pane document missing"));
        if (keyPane.getKeyList().stream().anyMatch(data -> data.getDataKey().equals(responseData.getDataKey())))
        // Imp - Exception when duplicate key found (although it will never happen since we are creating key using Instant class)
            throw new IllegalArgumentException("Pane with key already exists.");
        keyPane.getKeyList().add(new ResponseData(responseData.getDataKey(), responseData.getTag(), responseData.getCustomName()));
        paneRepo.save(keyPane);
        return responseData.getDataKey();
    }

    public String removePane(String key) {
        // Removing pane by a given dataKey
        KeyPane keyPane = paneRepo.findById("pane").orElseThrow(() -> new IllegalStateException("Pane document missing"));
        keyPane.getKeyList().removeIf(data -> data.getDataKey().equals(key));
        paneRepo.save(keyPane);
        return key;
    }

    public List<ResponseData> allPane() {
        // Extracting all panes as List of ResponseData
        return paneRepo.findById("pane").orElseThrow(() -> new IllegalStateException("Pane document missing")).getKeyList();
    }

    public String clearPane(String tag) {
        // Clearing the pane of a specified tag
        KeyPane keyPane = paneRepo.findById("pane").orElseThrow(() -> new IllegalStateException("Pane document missing"));
        List<ResponseData> toRemove = new ArrayList<>();
        // Create a separate list that stores the ResponseData entries to be removed
        for (ResponseData data : keyPane.getKeyList()) {
            if (data.getTag().equals(tag)) {
                toRemove.add(data);
            }
        }
        keyPane.getKeyList().removeAll(toRemove);   // Cannot modify a list in SpringBoot while iterating it
        paneRepo.save(keyPane);
        return "Pane cleared of tag " + tag;
    }

    public String clearPaneTags(Set<String> tags) {
        KeyPane keyPane = paneRepo.findById("pane").orElseThrow(() -> new IllegalStateException("Pane Document Missing"));
        List<ResponseData> toRemove = new ArrayList<>();
        for(ResponseData data : keyPane.getKeyList())
            if(tags.contains(data.getTag()))
                toRemove.add(data);
        keyPane.getKeyList().removeAll(toRemove);
        return "pane Tags removed";
    }

    public String getPaneTag(String dataKey) {
        // Getting the tag name by the dataKey in the pane list
        KeyPane keyPane = paneRepo.findById("pane").orElse(null);
        List<ResponseData> list = keyPane.getKeyList();
        for(ResponseData data : list) {
            if(data.getDataKey().equals(dataKey))
                return data.getTag();
        }
        return "Not found";
    }

    public boolean searchPaneTag(String dataKey) {
        KeyPane keyPane = paneRepo.findById("pane").orElse(null);
        List<ResponseData> list = keyPane.getKeyList();
        for(ResponseData data : list) {
            if(data.getDataKey().equals(dataKey))
                return true;
        }
        return false;
    }
}
