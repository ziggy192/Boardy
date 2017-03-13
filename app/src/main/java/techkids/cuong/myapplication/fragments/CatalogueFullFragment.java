package techkids.cuong.myapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.cuong.myapplication.R;
import techkids.cuong.myapplication.adapters.CatalogueAdapter;
import techkids.cuong.myapplication.models.BoardGame;


/**
 * A simple {@link Fragment} subclass.
 */
public class CatalogueFullFragment extends Fragment {


    @BindView(R.id.rv_catalogue)
    RecyclerView rvCatalogue;

    @BindView(R.id.tv_catalogue)
    TextView tvCatalogue;

    private List<BoardGame> list;

    private CatalogueAdapter adapter;

    public CatalogueFullFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(sticky = true)
    public void onCatalogueEvent(CatalogueFullEvent event) {

        String title = event.getTitle();

        list = event.getBoardGames();
        adapter = new CatalogueAdapter(list);

        rvCatalogue.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvCatalogue.setAdapter(adapter);

        tvCatalogue.setText(event.getTitle());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_catalogue_full, container, false);

        ButterKnife.bind(this, view);
        return view;
    }

    public static class CatalogueFullEvent{

        private String title;

        private List<BoardGame> boardGames;

        public CatalogueFullEvent(String title, List<BoardGame> boardGames) {
            this.title = title;
            this.boardGames = boardGames;
        }

        public String getTitle() {
            return title;
        }

        public List<BoardGame> getBoardGames() {
            return boardGames;
        }
    }
}
