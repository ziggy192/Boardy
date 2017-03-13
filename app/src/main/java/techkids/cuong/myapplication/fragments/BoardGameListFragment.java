package techkids.cuong.myapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.cuong.myapplication.R;
import techkids.cuong.myapplication.adapters.BoardGameItemAdapter;
import techkids.cuong.myapplication.events.HideToolbarEvent;
import techkids.cuong.myapplication.events.SearchEvent;
import techkids.cuong.myapplication.models.BoardGame;


/**
 * A simple {@link Fragment} subclass.
 */
public class BoardGameListFragment extends Fragment {


    //todo this is no longer being used
    @BindView(R.id.rv_board_game_list)
    RecyclerView rvBoardGameList;

    private BoardGameItemAdapter adapter;

    public BoardGameListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board_game_list, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().post(new HideToolbarEvent(false));
        setupUI();

        super.onStart();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
//        EventBus.getDefault().post(new HideToolbarEvent(false, true));
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void setupUI() {

        BoardGame.boardGamesList = Arrays.asList(BoardGame.boardGameArray);

        adapter = new BoardGameItemAdapter();


        rvBoardGameList.setAdapter(adapter);

        rvBoardGameList.setLayoutManager(new StaggeredGridLayoutManager(1, 0));

    }

    @Subscribe
    public void searchResult(SearchEvent searchEvent) {

        String text = searchEvent.getWord();

        ArrayList<BoardGame> list = new ArrayList<BoardGame>();
                list.clear();
                for (BoardGame boardGame : BoardGame.boardGameArray) {
                    String subString = boardGame.getName().substring(0, text.length()).toLowerCase();
                    if (subString.equals(text)) {
                        list.add(boardGame);
                    }
                }

                BoardGame.boardGamesList = list;

                adapter.notifyDataSetChanged();
    }

}
