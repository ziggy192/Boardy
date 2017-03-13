package techkids.cuong.myapplication.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Cuong on 1/5/2017.
 */
public class Paragraph implements Serializable{
    public static final String TEXT_TYPE = "text";
    public static final String IMAGE_TYPE = "image";
    public static final String COMBINE_TYPE = "combine";

    private String type;

    private String title;

    private String content;

    private String imageUrl;

    private String[] tags;

    public Paragraph(String type, String title, String content, String imageUrl, String[] tags) {
        this.type = type;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.tags = tags;
//        this.tags = Arrays.asList(tags);
//        this.tags = new String[] {"*"};
//        for (String tag : tags) {
//            this.tags[this.tags.length] = new String();
//            this.tags[this.tags.length] = tag;
//        }
    }

    public static Paragraph createText(String title, String content, String[] tags) {
        return new Paragraph(TEXT_TYPE, title, content, null, tags);
    }

    public static Paragraph createImage(String imageUrl, String[] tags) {
        return new Paragraph(IMAGE_TYPE, null, null, imageUrl, tags);
    }

    public static Paragraph createCombine(String title, String content, String imageUrl, String[] tags) {
        return new Paragraph(COMBINE_TYPE, title, content, imageUrl, tags);
    }

    public String[] getTags() {
        return tags;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public static List<Paragraph> list = new ArrayList<>();

    @Override
    public String toString() {
        return "TutorialBlock{" +
                "type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", tags=" + Arrays.toString(tags) +
                '}';
    }
}
