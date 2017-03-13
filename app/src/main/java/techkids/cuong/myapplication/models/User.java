package techkids.cuong.myapplication.models;

import com.facebook.Profile;

/**
 * Created by Cuong on 2/18/2017.
 */

public class User {

    public static String userName;

    private static Profile profile;

    public static void setProfile(Profile profile) {
        User.profile = profile;
    }

    public static Profile getProfile() {
        return profile;
    }
}
