package techkids.cuong.myapplication.events;

/**
 * Created by Cuong on 1/5/2017.
 */
public class BackEvent {
    private boolean showToolbar;

    public BackEvent(boolean showToolbar) {
        this.showToolbar = showToolbar;
    }

    public boolean isShowToolbar() {
        return showToolbar;
    }
}
