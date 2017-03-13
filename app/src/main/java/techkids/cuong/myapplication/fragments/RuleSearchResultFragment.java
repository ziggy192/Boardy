package techkids.cuong.myapplication.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.devspark.progressfragment.ProgressFragment;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.LocationTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import techkids.cuong.myapplication.R;
import techkids.cuong.myapplication.activities.RuleActivity;
import techkids.cuong.myapplication.adapters.MyRuleSearchResultRecyclerViewAdapter;
import techkids.cuong.myapplication.adapters.MyRuleSearchResultRecyclerViewAdapter.RuleSearchResultItem;

import static techkids.cuong.myapplication.activities.RuleActivity.PDF_FILE_NAME_KEY;
import static techkids.cuong.myapplication.activities.RuleActivity.SAMPLE_FILE;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */

@EFragment
public class RuleSearchResultFragment extends ProgressFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String QUERY_KEY = "query";
    private static final String TAG = RuleSearchResultFragment.class.toString();
    // TODO: Customize parameters


    private int mColumnCount = 1;
    private String fileName;
    private OnListFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    PdfReader pdfReader;
    String query;
    MaterialSearchView searchView;
    View mContentView;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RuleSearchResultFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RuleSearchResultFragment create(String fileName, String query) {
        RuleSearchResultFragment fragment = new RuleSearchResultFragment_();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, 1);
        args.putString(PDF_FILE_NAME_KEY, fileName);
        args.putString(QUERY_KEY, query);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            fileName = getArguments().getString(PDF_FILE_NAME_KEY);
            query = getArguments().getString(QUERY_KEY);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.rules_search_only_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_rule_search_result_list, container, false);

        // Set the adapter
        if (mContentView instanceof RecyclerView) {
            Context context = mContentView.getContext();
            recyclerView = (RecyclerView) mContentView;
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setContentView(mContentView);
        setEmptyText(getString(R.string.nothing_found));
        // TODO: 3/13/2017 uncomment this
//        startBackgroundSearch(query);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnListFragmentInteractionListener");
            mListener = new OnListFragmentInteractionListener() {
                @Override
                public void onListFragmentInteraction(RuleSearchResultItem item) {
                    //do nothign
                }

            };
        }
        if (context instanceof RuleActivity) {
            searchView = ((RuleActivity) context).getSearchView();
        } else {
            Log.e(TAG, "onAttach: activity not instanceOf RuleActivity");
        }
        Log.d(TAG, "onAttach: binding pdfReader");

        if (context instanceof PdfReaderHolder) {
            pdfReader = ((PdfReaderHolder) context).getPdfReader();
        } else {

            // selfcreate a new pdfReader (take some time)
            String filePath = getActivity().getFilesDir() + "/" + fileName;
            try {
                pdfReader = new PdfReader(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart:");
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
        startBackgroundSearch(query);
    }


    public void startBackgroundSearch(String query) {
        setContentShown(false);
        Log.d(TAG, "startBackgroundSearch: start searching ");
        // TODO: 3/13/2017 create dummy pdfReader
        String filePath = getActivity().getFilesDir() + "/" + SAMPLE_FILE;
//        List<RuleSearchResultItem> searchResults;
        startSearching(query, filePath);
        Log.d(TAG, "startBackgroundSearch: continue in UiThread (must right after searching)");
//        updateRecyclerView(searchResults);

    }


    @Background
    public void startSearching(String query, String filePath) {
        PdfReader pdfReader = null;
        try {
            pdfReader = new PdfReader(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        PdfReaderContentParser parser = new PdfReaderContentParser(pdfReader);
//        MyLocationTextExtractionStrategy strategy;
        LocationTextExtractionStrategy strategy;
        final ArrayList<RuleSearchResultItem> searchResults = new ArrayList<>();
        for (int page = 1; page <= pdfReader.getNumberOfPages(); page++) {
            try {
                strategy = parser.processContent(page, new LocationTextExtractionStrategy());
                String currentText = strategy.getResultantText();
                SpannableStringBuilder searchResult = searchInPage(currentText, query);
                if (searchResult != null) {
//                    Log.d(TAG, String.format("search for %s: appear at page %s (%s)   ", query,page,searchResult));
                    searchResults.add(new RuleSearchResultItem(searchResult, page));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        updateRecyclerView(searchResults);

        //todo shold only set once and update over time
//        pdfReader.close();
    }

    @UiThread
    public void updateRecyclerView(List<RuleSearchResultItem> ruleSearchResultItemList) {
        if (ruleSearchResultItemList.isEmpty()) {
            setContentEmpty(true);
        }else{
            setContentEmpty(false);
            recyclerView.setAdapter(new MyRuleSearchResultRecyclerViewAdapter(ruleSearchResultItemList, mListener));
        }

        setContentShown(true);
        Log.d(TAG, "updateRecyclerView: finished search");
    }

    private SpannableStringBuilder searchInPage(String pageContent, String query) {

        /*todo
        * Return a FIRST FOUND CUT string which contain only 1 query
        *
        *
        *
        * return a SpannableStringBuilder
        * return a Array of SpannableStringBuilder that contain many CUT STRING RESULT
        * */

        final int MAX_WORD_COUNT = 4;
        int index = 0;
        index = pageContent.toLowerCase().indexOf(query.toLowerCase(), index);

        if (index == -1) {
            return null;
        }

        // find contentBefore
        int i = index - 1;
        int wordCount = 0;

        while ((i >= 0) && (wordCount < MAX_WORD_COUNT)) {
            if (pageContent.charAt(i) == ' ') {
                wordCount++;
            }
            i--;
        }
        String contentBefore = "..." + pageContent.substring(i + 1, index);

        // findStringAfter
        wordCount = 0;
        for (i = index + query.length(); wordCount < MAX_WORD_COUNT + 1 && i < pageContent.length(); i++) {
            if (pageContent.charAt(i) == ' ') {
                wordCount++;
            }
        }
        String contentAfter = pageContent.substring(index + query.length(), i) + "...";

        String result = contentBefore +
                pageContent.substring(index, index + query.length()) + contentAfter;

        SpannableStringBuilder builder = new SpannableStringBuilder(result);
        builder.setSpan(new StyleSpan(Typeface.BOLD), contentBefore.length(), contentBefore.length() + query.length(), 0);
        return builder;
    }

//            int savedIndex;
//            pageContent.indexOf(' ',index);

//            savedIndex = index;


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(RuleSearchResultItem item);
    }

    public interface PdfReaderHolder {
        PdfReader getPdfReader();
    }
}
