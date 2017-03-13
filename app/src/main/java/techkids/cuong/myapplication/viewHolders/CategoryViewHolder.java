package techkids.cuong.myapplication.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.cuong.myapplication.R;

/**
 * Created by Cuong on 2/6/2017.
 */
public class CategoryViewHolder extends RecyclerView.ViewHolder{


    @BindView(R.id.tv_category)
    TextView tvCategory;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(String category) {
        tvCategory.setText(category);
    }

}
