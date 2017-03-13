package techkids.cuong.myapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import techkids.cuong.myapplication.R;
import techkids.cuong.myapplication.activities.BoardGameDetailActivity;
import techkids.cuong.myapplication.models.BoardGame;

/**
 * Created by Nghia on 3/1/2017.
 */

public class BoardGameResultAdapter extends RecyclerView.Adapter<BoardGameResultViewHolder>  {

    List<BoardGame> boardGames;

    public BoardGameResultAdapter(List<BoardGame> boardGames) {
        this.boardGames = boardGames;
    }

    @Override
    public BoardGameResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_boardgame_search_result, parent,false);
        return new BoardGameResultViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BoardGameResultViewHolder holder, int position) {
        holder.bind(boardGames.get(position));
    }

    @Override
    public int getItemCount() {
        return boardGames.size();
    }
}

class BoardGameResultViewHolder extends RecyclerView.ViewHolder {

    private static final int IMAGE_WIDTH = 90;
    private static final int IMAGE_HEIGHT = 90;
    @BindView(R.id.iv_boardgame)
    ImageView imvBoardGame;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.tv_players)
    TextView tvPlayers;
    @BindView(R.id.tv_playing_time)
    TextView tvPlayingTime;

    private Context context;
    private String boardGameId;

    public BoardGameResultViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        context = itemView.getContext();
    }

    public void bind(BoardGame boardGame) {
        boardGameId = boardGame.getId();
        Picasso.with(context).load(boardGame.getThumbUrl())
                .resize(IMAGE_WIDTH,IMAGE_HEIGHT)
                .centerCrop()
                .placeholder(R.drawable.default_placeholder)
                .error(R.drawable.default_placeholder)
                .into(imvBoardGame);
        tvTitle.setText(boardGame.getName());
        tvDescription.setText(boardGame.getPublisher().getName());
        tvPlayingTime.setText(String.format("%d min",boardGame.getPlayingTime()));
        tvPlayers.setText(String.format("%d - %d", boardGame.getMinPlayer(), boardGame.getMaxPlayer()));
    }

    @OnClick(R.id.cv_container)
    public void changeDetailFragment() {
        EventBus.getDefault().post(new BoardGameDetailActivity.ToDetailActivityEvent(boardGameId));
    }


}
