package techkids.cuong.myapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shockwave.pdfium.PdfDocument.Bookmark;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.cuong.myapplication.R;

/**
 * Created by Nghia on 3/9/2017.
 */

public class ContentMenuAdapter extends ArrayAdapter<Bookmark> {

    private static final String TAG = ContentMenuAdapter.class.toString();
    private ArrayList<Bookmark> mList;
    private Stack<ArrayList<Bookmark>> parentStackLists;

    public ContentMenuAdapter(Context context, int resource, int textViewResourceId, List<Bookmark> objects) {
        super(context,resource,textViewResourceId,objects);
        mList = new ArrayList<>(objects);
        parentStackLists = new Stack<>();
    }



    public void toChildren(List<Bookmark> childrenList) {

        parentStackLists.push(mList);
        mList = new ArrayList<>(childrenList);

        Log.d(TAG, "toChildren: parent peek size(must > 7) = "+parentStackLists.peek().size());
        super.clear();
        super.addAll(childrenList);
        super.notifyDataSetChanged();
        Log.d(TAG, "toChildren: parent peek size after clear(must > 7) = "+parentStackLists.peek().size());

    }

    public void backToParent(){
        mList =  parentStackLists.pop();

        super.clear();
        super.addAll(mList);
        super.notifyDataSetChanged();
        Log.d(TAG, "backToParent: mlist size after clear = "+mList.size());

    }


    private boolean hasPrevious() {
        return !parentStackLists.isEmpty();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view  = super.getView(position, convertView, parent);
        Bookmark bookmark = getItem(position);
        ContentMenuViewHolder viewHolder = new ContentMenuViewHolder(bookmark);
        if (position == 0 && hasPrevious()) {
            Log.d(TAG, "getView: hasprevious");

            viewHolder.bind(view, true);
        }
        else{
            viewHolder.bind(view, false);
        }
        return view;
    }


    public class ContentMenuViewHolder{
        @BindView(R.id.imv_btn_next)
        ImageView imvNext;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.imv_btn_back)
        ImageView imvBack;

        Bookmark bookmark;

        public ContentMenuViewHolder(Bookmark bookmark) {
            this.bookmark = bookmark;
        }

        public void bind(View view, boolean hasPrevious) {
            ButterKnife.bind(this, view);

            tvTitle.setText(bookmark.getTitle());
            if (bookmark.hasChildren()) {
                imvNext.setVisibility(View.VISIBLE);
                imvNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post( new ToChildrenContentMenuClicked(bookmark));
                    }
                });
            }else{
                imvNext.setVisibility(View.INVISIBLE);
                imvNext.setOnClickListener(null);
            }
            if (hasPrevious) {
                imvBack.setVisibility(View.VISIBLE);
                imvBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new ToParentContentMenuClicked());
                    }
                });
            } else {
                imvBack.setVisibility(View.INVISIBLE);
                imvBack.setOnClickListener(null);
            }
        }

    }
    public static class ToChildrenContentMenuClicked{
        Bookmark bookmark;

        public ToChildrenContentMenuClicked(Bookmark bookmark) {
            this.bookmark = bookmark;
        }

        public Bookmark getBookmark() {
            return bookmark;
        }
    }
    public static class ToParentContentMenuClicked {
        public ToParentContentMenuClicked() {
        }
    }

    public static class ContentMenuItemClickedEvent {
        Bookmark bookmark;

        public ContentMenuItemClickedEvent(Bookmark bookmark) {
            this.bookmark = bookmark;
        }

        public Bookmark getBookmark() {
            return bookmark;
        }
    }
}



