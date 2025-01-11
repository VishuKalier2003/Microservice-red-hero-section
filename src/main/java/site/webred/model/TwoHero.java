package site.webred.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document("TwoImageHero")
public class TwoHero {
    @Id
    private String dataKey;
    @Nullable
    private String heroLink1, heroLink2, heroTitle, heroContent, heroButton, customName, tag;
    private boolean colorSwitch;
    public static int index;
}
