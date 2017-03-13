//package techkids.cuong.myapplication.fragments;
//
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.StaggeredGridLayoutManager;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.WebSettings;
//import android.widget.TextView;
//
//import com.miguelcatalan.materialsearchview.MaterialSearchView;
//
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//
//import java.util.Arrays;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import techkids.cuong.myapplication.R;
//import techkids.cuong.myapplication.adapters.CardDetailAdapter;
//import techkids.cuong.myapplication.adapters.ParagraphAdapter;
//import techkids.cuong.myapplication.events.HideToolbarEvent;
//import techkids.cuong.myapplication.models.BoardGame;
//import techkids.cuong.myapplication.models.CardDetail;
//import us.feras.mdv.MarkdownView;
//
//
///**
// * A simple {@link Fragment} subclass.
// */
//public class BoardGamesRulesFragment extends Fragment implements MaterialSearchView.OnQueryTextListener,
//        MaterialSearchView.SearchViewListener {
//
//
////    @BindView(R.id.rv_board_game_rules)
////    RecyclerView rvRules;
//
////    @BindView(R.id.msv)
////    MaterialSearchView msv;
//
////    private ParagraphAdapter adapter;
//
//    @BindView(R.id.markdown_view)
//    MarkdownView markdownView;
//
//    @BindView(R.id.rv_cards)
//    RecyclerView recyclerViewCars;
//    @BindView(R.id.tv_card_detail)
//    TextView tvCardDetail;
//    @BindView(R.id.tv_card_name)
//    TextView tvCardName;
//
//    CardDetailAdapter adapter;
//
//    BoardGame boardgame;
//
//    public BoardGamesRulesFragment() {
//        // Required empty public constructor
//    }
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        EventBus.getDefault().unregister(this);
//    }
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_board_games_rules, container, false);
//        ButterKnife.bind(this, view);
//        boardgame = BoardGame.boardGamesList.get(0);
//        setupUI();
//        return view;
//    }
//
//
//    private void setupUI() {
//
//        markdownView.loadMarkdown(boardgame.getRules(), "file:///android_asset/classic.css");
//
//        WebSettings webSettings = markdownView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//
//        adapter = new CardDetailAdapter(Arrays.asList(CardDetail.cardDetails), recyclerViewCars);
//        adapter.setOnCardDetailClickedListener(new CardDetailAdapter.CardDetailClickListener() {
//            @Override
//            public void onClick(CardDetail cardDetail) {
//                tvCardName.setText(cardDetail.getCardName());
//                tvCardDetail.setText("    "+cardDetail.getContent());
//            }
//        });
//        recyclerViewCars.setAdapter(adapter);
//        recyclerViewCars.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
//
//
////        markdownView.loadMarkdown();
////        adapter = new ParagraphAdapter();
////        rvRules.setLayoutManager(new LinearLayoutManager(getContext()));
////        rvRules.setAdapter(adapter);
//    }
//
//    @Subscribe
//    public void onSearch(RuleSearchEvent event) {
//        String querry = event.getQuerry();
//
//    }
//
//    @Override
//    public boolean onQueryTextSubmit(String query) {
//        if (markdownView != null) {
//            markdownView.findAllAsync(query);
//        }
//
//        return false;
//    }
//
//    @Override
//    public boolean onQueryTextChange(String newText) {
//        return false;
//    }
//
//    @Override
//    public void onSearchViewShown() {
//
//    }
//
//    @Override
//    public void onSearchViewClosed() {
//        if (markdownView != null) {
//            markdownView.clearMatches();
//        }
//
//    }
//
//
//    public static class RuleSearchEvent {
//        private String querry;
//
//        public RuleSearchEvent(String querry) {
//            this.querry = querry;
//        }
//
//        public String getQuerry() {
//            return querry;
//        }
//    }
//
//
//}
