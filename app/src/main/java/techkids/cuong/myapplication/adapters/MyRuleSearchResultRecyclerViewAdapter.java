package techkids.cuong.myapplication.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import techkids.cuong.myapplication.R;
import techkids.cuong.myapplication.fragments.RuleSearchResultFragment;
import techkids.cuong.myapplication.fragments.RuleSearchResultFragment.OnListFragmentInteractionListener;
import techkids.cuong.myapplication.fragments.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyRuleSearchResultRecyclerViewAdapter extends RecyclerView.Adapter<MyRuleSearchResultRecyclerViewAdapter.ViewHolder> {

    private final List<RuleSearchResultItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyRuleSearchResultRecyclerViewAdapter(List<RuleSearchResultItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rule_search_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(mValues.get(position));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView tvSearchResult;
        final TextView tvPageNumber;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvSearchResult = (TextView) view.findViewById(R.id.tv_search_result);
            tvPageNumber = (TextView) view.findViewById(R.id.tv_page_number);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvSearchResult.getText() + "'";
        }

        public void bind(final RuleSearchResultItem item) {
            tvSearchResult.setText(item.getSearchResult());
            tvPageNumber.setText(String.format("Page %s", item.getPageNumber()));
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(item);
                    }
                }
            });
        }
    }

    public static class RuleSearchResultItem{
        SpannableStringBuilder searchResult;
        int pageNumber;

        public RuleSearchResultItem(SpannableStringBuilder searchResult, int pageNumber) {
            this.searchResult = searchResult;
            this.pageNumber = pageNumber;
        }

        public SpannableStringBuilder getSearchResult() {
            return searchResult;
        }

        public int getPageNumber() {
            return pageNumber;
        }
    }
}
