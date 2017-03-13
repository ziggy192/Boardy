package techkids.cuong.myapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import techkids.cuong.myapplication.R;
import techkids.cuong.myapplication.adapters.CatalogueAdapter;
import techkids.cuong.myapplication.events.HideToolbarEvent;
import techkids.cuong.myapplication.utils.DBContext;
import techkids.cuong.myapplication.models.BoardGame;


/**
 * A simple {@link Fragment} subclass.
 */
public class BoardGameCatalogueFragment extends Fragment {

    @BindView(R.id.ll_coming_soon)
    LinearLayout llComingSoon;

    @BindView(R.id.rv_coming_soon)
    RecyclerView rvComingSoon;

    private CatalogueAdapter adapter;

    @BindView(R.id.ll_hot_games)
    LinearLayout llHotGames;

    @BindView(R.id.rv_hot_games)
    RecyclerView rvHotGames;

    private CatalogueAdapter hotGamesAdapter;

    @BindView(R.id.rv_family)
    RecyclerView rvFamily;

    private CatalogueAdapter familyAdapter;

    @BindView(R.id.rv_strategy)
    RecyclerView rvStrategy;

    private CatalogueAdapter strategyAdapter;

    private List<BoardGame> commingSoonList;

    private List<BoardGame> hotGamesList;

    private List<BoardGame> strategyGamesList;

    private List<BoardGame> familyGamesList;

    public BoardGameCatalogueFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board_game_catalogue, container, false);
        ButterKnife.bind(this, view);
        setupUI();
        EventBus.getDefault().post(new HideToolbarEvent(false));
        return view;
    }



    private void setupUI() {


        commingSoonList = DBContext.getInstance().getCommingSoonBoardGames();
//        for (int i = 1; i < BoardGame.boardGameArray.length; i++) {
//            commingSoonList.add(BoardGame.boardGameArray[i]);
//        }
        adapter = new CatalogueAdapter(commingSoonList);

        rvComingSoon.setAdapter(adapter);
        rvComingSoon.setLayoutManager(new StaggeredGridLayoutManager(1, 0));



        hotGamesList = DBContext.getInstance().getHotBoardGames();
//        hotGamesList.add(BoardGame.boardGameArray[0]);
        hotGamesAdapter = new CatalogueAdapter(hotGamesList);
        rvHotGames.setAdapter(hotGamesAdapter);
        rvHotGames.setLayoutManager(new StaggeredGridLayoutManager(1, 0));



        BoardGame.setUpList();

        strategyAdapter = new CatalogueAdapter(BoardGame.strategyList);

        familyAdapter = new CatalogueAdapter(BoardGame.familyList);

        rvStrategy.setAdapter(strategyAdapter);
        rvStrategy.setLayoutManager(new StaggeredGridLayoutManager(1, 0));

        rvFamily.setAdapter(familyAdapter);
        rvFamily.setLayoutManager(new StaggeredGridLayoutManager(1, 0));
    }

    @OnClick(R.id.ll_coming_soon)
    public void goToCatalogueFullFragment() {
//        EventBus.getDefault().post(new CatalogueFullFragment.CatalogueFullEvent("Coming soon",commingSoonList ));
        EventBus.getDefault().postSticky(new CatalogueFullFragment.CatalogueFullEvent("Coming soon", commingSoonList));
    }

    @OnClick(R.id.ll_hot_games)
    public void onHotGamesClick() {
//        EventBus.getDefault().post(new CatalogueFullFragment.CatalogueFullEvent("Hot games", hotGamesList));
        EventBus.getDefault().postSticky(new CatalogueFullFragment.CatalogueFullEvent("Hot games", hotGamesList));
    }

    @OnClick(R.id.ll_strategy_games)
    public void onStrategyClick() {
        EventBus.getDefault().postSticky(new CatalogueFullFragment.CatalogueFullEvent("Stragegy", BoardGame.strategyList));
    }

    @OnClick(R.id.ll_family)
    public void onFamilyClick() {
        EventBus.getDefault().postSticky(new CatalogueFullFragment.CatalogueFullEvent("Family", BoardGame.familyList));
    }
}
