package site.webred.model;

import java.util.List;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.Nullable;

import lombok.Getter;
import lombok.Setter;
import site.webred.utility.ResponseData;

@Getter@Setter
@Document("pane")
public class KeyPane {
    @Id
    private String id;      // Generating Id automatically by mongodb
    @Nullable   // Response Data stored in sorted order by time
    private List<ResponseData> keyList;

    public KeyPane() {  // Constructor to invoke
        this.keyList = new ArrayList<>();
    }
}
