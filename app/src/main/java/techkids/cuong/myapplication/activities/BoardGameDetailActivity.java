package techkids.cuong.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import techkids.cuong.myapplication.events.BoardGameEvent;
//import techkids.cuong.myapplication.fragments.BoardGamesRulesFragment;
import techkids.cuong.myapplication.R;
import techkids.cuong.myapplication.events.BackEvent;
import techkids.cuong.myapplication.fragments.BoardGameInformationFragment;
import techkids.cuong.myapplication.fragments.QuestionAndAnswerFragment;
import techkids.cuong.myapplication.utils.DBContext;
import techkids.cuong.myapplication.models.BoardGame;

import static techkids.cuong.myapplication.activities.RuleActivity.PDF_FILE_NAME_KEY;
import static techkids.cuong.myapplication.activities.RuleActivity.SAMPLE_FILE;

public class BoardGameDetailActivity extends AppCompatActivity {

    @BindView(R.id.bottom_bar)
    BottomBar bb;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

//    @BindView(R.id.iv_boardgame)
//    ImageView ivBoardGame;

//    BoardGamesRulesFragment rulesFragment;

    private BoardGame boardGame;

//    @BindView(R.id.v_gap)
//    View vGap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_game_detail);
        ButterKnife.bind(this);
        String boardgameID = getIntent().getStringExtra(MainActivity.BOARDGAME_ID_KEY);
        boardGame = DBContext.getInstance().getBoardGameById(boardgameID);
//        BoardGame boardGame= (BoardGame) getIntent().getSerializableExtra(MainActivity.BOARDGAME_KEY);
//        BoardGame boardGame = BoardGame.boardGamesList.get(position);
//        toolbar.setTitle(boardGame.getName());
        toolbar.setTitle("Detail");
        EventBus.getDefault().postSticky(new BoardGameEvent(boardGame));

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addListener();
        getReferences();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        toolbar.setVisibility(View.GONE);
//        vGap.setVisibility(View.GONE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
//            case R.id.action_search:
//                searchView.setVisibility(View.VISIBLE);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.rules_menu, menu);
//        MenuItem item = menu.findItem(R.id.action_search);
//        searchView.setMenuItem(item);
        return super.onCreateOptionsMenu(menu);
    }


    private void addListener() {
        bb.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_information:
                        changeFragment(new BoardGameInformationFragment(), false);
                        return;
                    case R.id.tab_rules:
//                        changeFragment(rulesFragment, false);
                        return;
                    case R.id.tab_question:
                        changeFragment(new QuestionAndAnswerFragment(), false);
                        return;
                }
            }
        });

//        searchView.setOnQueryTextListener(rulesFragment);
//        searchView.setOnSearchViewListener(rulesFragment);
    }

    private void changeFragment(Fragment fragment, boolean addToBackStack) {

//        toolbar.setVisibility(View.VISIBLE);
//        vGap.setVisibility(View.VISIBLE);
        if (addToBackStack)
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .addToBackStack(null)
                    .commit();
        else
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fl_container, fragment)
                    .commit();
    }

//    @Subscribe
//    public void hideToolbar(HideToolbarEvent hideToolbarEvent) {
//        if (hideToolbarEvent.isHideToolbar()) {
//            toolbar.setVisibility(View.GONE);
////            vGap.setVisibility(View.GONE);
////            searchView.setVisibility(View.GONE);
////            searchView.setVisibility(View.GONE);
////            MenuItem item = (MenuItem) findViewById(R.id.action_search);
////            item.setIcon(null);
//        } else {
//            toolbar.setVisibility(View.VISIBLE);
////            vGap.setVisibility(View.VISIBLE);
////            searchView.setVisibility(View.VISIBLE);
////            searchView.setVisibility(View.VISIBLE);
//        }
//
//        if (hideToolbarEvent.isHideBottomNavi()) {
//            bb.setVisibility(View.GONE);
//        } else {
//            bb.setVisibility(View.VISIBLE);
//        }
//    }

    @OnClick(R.id.fab)
    public void toRuleActivity(){
        Intent intent = new Intent(BoardGameDetailActivity.this, RuleActivity_.class);
        //todo debugging
        intent.putExtra(PDF_FILE_NAME_KEY, SAMPLE_FILE);
        startActivity(intent);
    }


    @Subscribe
    public void backToMainActivity(BackEvent backEvent) {
        this.finish();
    }

    private void getReferences() {
//        rulesFragment = new BoardGamesRulesFragment();
    }

    public static class ToDetailActivityEvent {

        private String boardGameId;

        public ToDetailActivityEvent(String boardGameId) {
            this.boardGameId = boardGameId;
        }

        public String getBoardGameId() {
            return boardGameId;
        }
    }

}
