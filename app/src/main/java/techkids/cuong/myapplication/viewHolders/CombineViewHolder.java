package techkids.cuong.myapplication.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.cuong.myapplication.R;
import techkids.cuong.myapplication.models.Paragraph;

/**
 * Created by Cuong on 1/2/2017.
 */
public class CombineViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.iv_boardgame)
    ImageView ivBoardGame;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_content)
    TextView tvContent;

    public CombineViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Paragraph paragraph) {

        tvTitle.setText(paragraph.getTitle());

        Picasso.with(ivBoardGame.getContext()).load(paragraph.getImageUrl()).into(ivBoardGame);

        tvContent.setText(paragraph.getContent());
    }
}
