package techkids.cuong.myapplication.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.cuong.myapplication.R;
import techkids.cuong.myapplication.models.CardDetail;
import techkids.cuong.myapplication.viewHolders.CategoryViewHolder;

/**
 * Created by Nghia on 2/18/2017.
 */

public class CardDetailAdapter extends RecyclerView.Adapter<CardDetailViewHolder> {

    List<CardDetail> cardDetails;
    private RecyclerView mRecyclerView;
    private CardDetailClickListener listener;

    public CardDetailAdapter(List<CardDetail> cardDetails,RecyclerView mRecyclerView) {
        this.cardDetails = cardDetails;
        this.mRecyclerView = mRecyclerView;
    }

    public void setOnCardDetailClickedListener(CardDetailClickListener listener){
        this.listener = listener;
    }



    @Override
    public CardDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_card_detail, parent, false);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = mRecyclerView.getChildAdapterPosition(v);
                CardDetail cardDetail = cardDetails.get(itemPosition);
                if (listener != null) {
                    listener.onClick(cardDetail);
                }
            }
        });
        CardDetailViewHolder viewHolder = new CardDetailViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CardDetailViewHolder holder, int position) {
        holder.bind(cardDetails.get(position));

    }


    @Override
    public int getItemCount() {
        return cardDetails.size();
    }



    public static interface CardDetailClickListener{
        void onClick(CardDetail cardDetail);
    }



}
class CardDetailViewHolder extends RecyclerView.ViewHolder {

    private static final int IMAGE_WIDTH = 150;
    private static final int IMAGE_HEIGHT = 150;
    @BindView(R.id.imv_card)
    ImageView imvCard;
    Context context;

    public CardDetailViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }

    public void bind(CardDetail cardDetail){
        Picasso.with(context).load(cardDetail.getImageUrl())
                .resize(IMAGE_WIDTH,IMAGE_HEIGHT)
                .centerCrop()
                .placeholder(R.drawable.default_placeholder)
                .error(R.drawable.default_placeholder)
                .into(imvCard);
    }
}


