package techkids.cuong.myapplication.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.shockwave.pdfium.PdfDocument;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.cuong.myapplication.R;
import techkids.cuong.myapplication.adapters.ContentMenuAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class RuleMenuContentFragment extends Fragment {

    private static final String TAG = RuleMenuContentFragment.class.toString();
    @Nullable
    @BindView(R.id.listView)
    ListView listView;

    List<PdfDocument.Bookmark> bookmarkList;
    ContentMenuAdapter adapter;


    public RuleMenuContentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getArgs();

        if (bookmarkList.isEmpty()) {
            return inflater.inflate(R.layout.fragment_rule_menu_content_empty, container, false);
        } else {
            return inflater.inflate(R.layout.fragment_rule_menu_content, container, false);
        }
    }




    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // bookmark list is not empty
        ButterKnife.bind(this, view);
        if (listView != null) {
            adapter = new ContentMenuAdapter(getActivity(), R.layout.item_content_menu_list
                    , R.id.tv_title, bookmarkList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    PdfDocument.Bookmark bookmark = adapter.getItem(position);
                    EventBus.getDefault().post
                            (new ContentMenuAdapter.ContentMenuItemClickedEvent(bookmark));
                }
            });

        }else{
            Log.d(TAG, "onViewCreated: listView == null");
        }
        getActivity().setTitle(R.string.action_bookmarks);

    }

    private void getArgs() {
        bookmarkList = bookmarkListHolder;
    }

    private void setBookmarkList(List<PdfDocument.Bookmark> bookmarkList) {
        this.bookmarkList = bookmarkList;
    }



    @Subscribe
    public void toChildMenu(ContentMenuAdapter.ToChildrenContentMenuClicked event) {
        PdfDocument.Bookmark bookmark = event.getBookmark();
        adapter.toChildren(bookmark.getChildren());
        listView.setSelectionAfterHeaderView();
//        adapter = new ContentMenuAdapter(getActivity(), R.layout.item_content_menu_list
//                , R.id.tv_title, bookmark.getChildren(), adapter.getParentList());
//        listView.setAdapter(adapter);
    }

    @Subscribe
    public void backToParentMenu(ContentMenuAdapter.ToParentContentMenuClicked event) {
        adapter.backToParent();
    }

    public static RuleMenuContentFragment create() {

        RuleMenuContentFragment fragment = new RuleMenuContentFragment();
        Bundle args = new Bundle();
//        bookmarkListHolder = bookmarkList;

        fragment.setArguments(args);
        return fragment;
    }

    private static String BOOKMARK_LIST_KEY = "book mark list";
    public static  List<PdfDocument.Bookmark> bookmarkListHolder ;

}
