package techkids.cuong.myapplication.models;

import io.realm.RealmObject;

/**
 * Created by Nghia on 2/26/2017.
 */

public class AppCategory extends RealmObject
{
    private boolean commingSoon;
    private boolean hot;

    public AppCategory() {
    }

    public AppCategory(boolean commingSoon, boolean hot) {
        this.commingSoon = commingSoon;
        this.hot = hot;
    }

    public boolean isCommingSoon() {
        return commingSoon;
    }

    public boolean isHot() {
        return hot;
    }
}
