package techkids.cuong.myapplication.models;

import io.realm.RealmObject;

/**
 * Created by Nghia on 2/25/2017.
 */

public class RealmString extends RealmObject {
    private String value;

    public RealmString() {
    }

    public RealmString(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
