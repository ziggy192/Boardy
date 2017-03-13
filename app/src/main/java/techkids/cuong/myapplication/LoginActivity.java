package techkids.cuong.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.cuong.myapplication.events.LoginEvent;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.bt_login)
    LoginButton btLogin;

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        callbackManager = CallbackManager.Factory.create();
        btLogin.setReadPermissions("email");

        // Callback registration
        btLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
//                EventBus.getDefault().post(new LoginEvent());
            }

            @Override
            public void onCancel() {
//                EventBus.getDefault().post(new LoginEvent());
            }

            @Override
            public void onError(FacebookException exception) {
//                EventBus.getDefault().post(new LoginEvent());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
