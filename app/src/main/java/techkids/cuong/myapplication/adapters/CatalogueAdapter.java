package techkids.cuong.myapplication.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import techkids.cuong.myapplication.R;
import techkids.cuong.myapplication.models.BoardGame;
import techkids.cuong.myapplication.viewHolders.CatalogueViewHolder;

/**
 * Created by Cuong on 2/18/2017.
 */

public class CatalogueAdapter extends RecyclerView.Adapter<CatalogueViewHolder> {

    private List<BoardGame> list;

    public CatalogueAdapter(List<BoardGame> list) {
        this.list = list;
    }

    @Override
    public CatalogueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.item_boardgame_list, parent, false);
        CatalogueViewHolder viewHolder = new CatalogueViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CatalogueViewHolder holder, int position) {

        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<BoardGame> list) {
        this.list = list;
    }
}
