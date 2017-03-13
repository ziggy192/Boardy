package techkids.cuong.myapplication.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.cuong.myapplication.R;
import techkids.cuong.myapplication.adapters.BoardGameResultAdapter;
import techkids.cuong.myapplication.utils.DBContext;
import techkids.cuong.myapplication.models.BoardGame;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchResultFragment extends Fragment {

    private static final String TAG = SearchResultFragment.class.toString();
    @BindView(R.id.rv_board_game_result_list)
    RecyclerView recyclerView;
    private BoardGameResultAdapter adapter;
    private String searchKey = "";

    public SearchResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_result, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        List<BoardGame> boardGames = DBContext.getInstance().searchBoardgameByName(searchKey);
        Log.d(TAG, String.format("onViewCreated: %s", boardGames.toString()));
        adapter = new BoardGameResultAdapter(boardGames);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false));
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public static SearchResultFragment create(String searchKey){
        SearchResultFragment fragment = new SearchResultFragment();
        fragment.setSearchKey(searchKey);
        return fragment;
    }
}
