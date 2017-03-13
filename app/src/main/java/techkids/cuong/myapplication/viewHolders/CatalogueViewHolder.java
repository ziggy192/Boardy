package techkids.cuong.myapplication.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import techkids.cuong.myapplication.R;
import techkids.cuong.myapplication.activities.BoardGameDetailActivity;
import techkids.cuong.myapplication.models.BoardGame;
import techkids.cuong.myapplication.models.Paragraph;
import techkids.cuong.myapplication.models.QuestionAndAnswer;

/**
 * Created by Cuong on 2/18/2017.
 */
public class CatalogueViewHolder extends RecyclerView.ViewHolder{

    private static final int IMAGE_WIDTH = 150;
    private static final int IMAGE_HEIGHT = 150;

    public CatalogueViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    BoardGame boardGame;

    @BindView(R.id.iv_boardgame)
    ImageView ivBoardGame;

    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.tv_players)
    TextView tvPlayers;

    @BindView(R.id.tv_playing_time)
    TextView tvPlayingTime;

    public void bind(BoardGame boardGame) {
        this.boardGame = boardGame;

        Picasso.with(ivBoardGame.getContext())
                .load(boardGame.getThumbUrl())
                .resize(IMAGE_WIDTH,IMAGE_HEIGHT)
                .centerCrop()
                .placeholder(R.drawable.default_placeholder)
                .error(R.drawable.default_placeholder)
                .into(ivBoardGame);

        tvName.setText(boardGame.getName());

        tvPlayingTime.setText(String.format("%d min",boardGame.getPlayingTime()));

        tvPlayers.setText(String.format("%d - %d", boardGame.getMinPlayer(), boardGame.getMaxPlayer()));
    }

    @OnClick(R.id.cv_container)
    public void changeDetailFragment() {
        //todo debugging
//        EventBus.getDefault().post(new ChangeFragmentEvent(new BoardGameInformationFragment(), true, position));

//        BoardGame boardGame = BoardGame.boardGamesList.get(position);

        EventBus.getDefault().post(new BoardGameDetailActivity.ToDetailActivityEvent(boardGame.getId()));

    }
}
