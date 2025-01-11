package site.webred.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("OneImageHero")
public class OneHero {
    // Imp- Hero section types with only One Image link
    @Id
    private String dataKey;
    @Nullable
    private String heroLink, heroTitle, heroContent, heroButton, customName;
    @Nullable
    private String tag;
    private boolean colorSwitch;
    public static int index;
}
