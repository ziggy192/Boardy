package techkids.cuong.myapplication.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import techkids.cuong.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.cuong.myapplication.models.Paragraph;

/**
 * Created by Cuong on 1/2/2017.
 */
public class ImageViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.iv_boardgame)
    ImageView ivBoardGame;

    public ImageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Paragraph paragraph) {
        Picasso.with(ivBoardGame.getContext()).load(paragraph.getImageUrl()).into(ivBoardGame);
    }
}
