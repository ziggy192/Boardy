package techkids.cuong.myapplication.events;

/**
 * Created by Cuong on 1/6/2017.
 */
public class HideToolbarEvent {

    private boolean hideToolbar;

//    private boolean hideBottomNavi;

    public HideToolbarEvent(boolean hideToolbar) {
        this.hideToolbar = hideToolbar;
//        this.hideBottomNavi = hideBottomNavi;
    }

//    public boolean isHideBottomNavi() {
//        return hideBottomNavi;
//    }

    public boolean isHideToolbar() {
        return hideToolbar;
    }
}
