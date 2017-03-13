package techkids.cuong.myapplication.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import techkids.cuong.myapplication.R;
import techkids.cuong.myapplication.models.BoardGame;
import techkids.cuong.myapplication.models.Paragraph;
import techkids.cuong.myapplication.viewHolders.CombineViewHolder;
import techkids.cuong.myapplication.viewHolders.ImageViewHolder;
import techkids.cuong.myapplication.viewHolders.TextViewHolder;


/**
 * Created by Cuong on 1/2/2017.
 */
public class ParagraphAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == 1) {

            View view = inflater.inflate(R.layout.block_text, parent, false);

            TextViewHolder viewHolder = new TextViewHolder(view);

            return viewHolder;

        } else if (viewType == 0){

            View view = inflater.inflate(R.layout.block_image, parent, false);

            ImageViewHolder viewHolder = new ImageViewHolder(view);

            return viewHolder;

        } else {

            View view = inflater.inflate(R.layout.block_combine, parent, false);

            CombineViewHolder viewHolder = new CombineViewHolder(view);

            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Paragraph tutorialBlock = Paragraph.list.get(position);

        if (holder.getItemViewType() == 0) {
            ImageViewHolder imageViewHolder = (ImageViewHolder) holder;
            imageViewHolder.bind(tutorialBlock);
        }
        else if (holder.getItemViewType() == 1){
            TextViewHolder textViewHolder = (TextViewHolder) holder;
            textViewHolder.bind(tutorialBlock);
        } else {
            CombineViewHolder combineViewHolder = (CombineViewHolder) holder;
            combineViewHolder.bind(tutorialBlock);
        }
    }

    @Override
    public int getItemCount() {
        return Paragraph.list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (Paragraph.list.get(position).getType().equals(Paragraph.TEXT_TYPE)) {
            return 1;
        } else if (Paragraph.list.get(position).getType().equals(Paragraph.IMAGE_TYPE)) {
            return 0;
        } else {
            return 2;
        }
    }
}
