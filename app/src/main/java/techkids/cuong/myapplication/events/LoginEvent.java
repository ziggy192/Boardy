package techkids.cuong.myapplication.events;

import com.facebook.GraphResponse;

/**
 * Created by Cuong on 2/11/2017.
 */

public class LoginEvent {

    private GraphResponse graphResponse;

    public LoginEvent(GraphResponse graphResponse) {
        this.graphResponse = graphResponse;
    }

    public GraphResponse getGraphResponse() {
        return graphResponse;
    }
}
