package techkids.cuong.myapplication.events;

/**
 * Created by Cuong on 1/5/2017.
 */
public class SearchEvent {

    String word;

    public SearchEvent(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }
}
