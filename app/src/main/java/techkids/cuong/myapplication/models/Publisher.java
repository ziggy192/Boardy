package techkids.cuong.myapplication.models;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by Cuong on 2/18/2017.
 */

public class Publisher extends RealmObject implements Serializable {

    private String name;

    private String thumb;

    public Publisher() {
    }

    public Publisher(String name, String thumb) {
        this.name = name;
        this.thumb = thumb;
    }

    public String getName() {
        return name;
    }

    public String getThumb() {
        return thumb;
    }
}
