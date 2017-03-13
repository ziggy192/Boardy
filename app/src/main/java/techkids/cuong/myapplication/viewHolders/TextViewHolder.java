package techkids.cuong.myapplication.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import techkids.cuong.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.cuong.myapplication.models.Paragraph;

/**
 * Created by Cuong on 1/2/2017.
 */
public class TextViewHolder extends RecyclerView.ViewHolder{

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_content)
    TextView tvContent;

    public TextViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Paragraph paragraph) {
        tvTitle.setText( paragraph.getTitle());

        tvContent.setText(paragraph.getContent());
    }

}
