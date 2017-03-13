package techkids.cuong.myapplication.models;

import android.os.Parcel;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by Nghia on 2/18/2017.
 */

public class BoardgameSuggestion implements SearchSuggestion {

    @Override
    public String getBody() {
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

}

    @Override
    //todo debugging
    public String toString() {
        return "ahihi";
    }
}
