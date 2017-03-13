package techkids.cuong.myapplication.events;

import android.support.v4.app.Fragment;

/**
 * Created by Cuong on 1/5/2017.
 */
public class ChangeFragmentEvent {
    private Fragment fragment;

    private boolean addToBackStack;

    private int position;

    public ChangeFragmentEvent(Fragment fragment, boolean addToBackStack, int position) {
        this.fragment = fragment;
        this.addToBackStack = addToBackStack;
        this.position = position;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public boolean isAddToBackStack() {
        return addToBackStack;
    }

    public int getPosition() {
        return position;
    }
}
