package techkids.cuong.myapplication.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import techkids.cuong.myapplication.R;
import techkids.cuong.myapplication.models.BoardGame;
import techkids.cuong.myapplication.viewHolders.BoardGameViewHolder;

/**
 * Created by Cuong on 1/5/2017.
 */
public class BoardGameItemAdapter extends RecyclerView.Adapter<BoardGameViewHolder> {

    @Override
    public BoardGameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.item_boardgame_list, parent, false);

        BoardGameViewHolder viewHolder = new BoardGameViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BoardGameViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return BoardGame.boardGamesList.size();
    }
}
