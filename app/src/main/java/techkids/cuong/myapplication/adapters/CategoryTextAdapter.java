package techkids.cuong.myapplication.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import techkids.cuong.myapplication.R;
import techkids.cuong.myapplication.models.RealmString;
import techkids.cuong.myapplication.viewHolders.CategoryViewHolder;

/**
 * Created by Cuong on 2/6/2017.
 */

public class CategoryTextAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private ArrayList<String> categories;

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.item_category, parent, false);

        CategoryViewHolder viewHolder = new CategoryViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {

        holder.bind(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public CategoryTextAdapter(ArrayList<String> categories) {
        super();
        this.categories = categories;
    }
}
