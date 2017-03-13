package techkids.cuong.myapplication.activities;

import android.content.res.AssetManager;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.github.barteksc.pdfviewer.PDFView;
import com.itextpdf.text.pdf.PdfReader;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.shockwave.pdfium.PdfDocument;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import techkids.cuong.myapplication.R;
import techkids.cuong.myapplication.adapters.ContentMenuAdapter;
import techkids.cuong.myapplication.adapters.MyRuleSearchResultRecyclerViewAdapter;
import techkids.cuong.myapplication.fragments.RuleMenuContentFragment;
import techkids.cuong.myapplication.fragments.RulePDFViewingFragment;
import techkids.cuong.myapplication.fragments.RuleSearchResultFragment;
import techkids.cuong.myapplication.fragments.RuleSearchResultFragment_;


@EActivity(R.layout.activity_rule)
public class RuleActivity extends AppCompatActivity implements RuleSearchResultFragment.OnListFragmentInteractionListener, RuleSearchResultFragment.PdfReaderHolder {

    private static final String TAG = MainActivity.class.getSimpleName();

    //    private final static int REQUEST_CODE = 42;
//
//    public static final int PERMISSION_CODE = 42042;
//    public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
    public static final String SAMPLE_FILE = "OneNightUltimateWerewolf_rules.pdf";

    private static final String BACKSTACK_FRAGMENT_TAGS = "Backstacked Fragment";
    public static final String PDF_FILE_NAME_KEY = "pdf file name";

    @ViewById
    PDFView pdfView;
    @ViewById(R.id.search_view)
    MaterialSearchView searchView;


    RulePDFViewingFragment rulePDFViewingFragment;
    RuleMenuContentFragment ruleMenuContentFragment;


    String pdfFileName;
    PdfReader pdfReader;


    @AfterViews
    void afterViews() {

//        pdfFileName = SAMPLE_FILE;
        pdfFileName = getIntent().getStringExtra(PDF_FILE_NAME_KEY);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.fl_container);
                if (visibleFragment instanceof RulePDFViewingFragment) {
                    changeFragment(RuleSearchResultFragment_.create(pdfFileName, query), true);
                    searchView.clearFocus();
                } else if (visibleFragment instanceof RuleSearchResultFragment_) {
                    ((RuleSearchResultFragment_) visibleFragment).startBackgroundSearch(query);
                    searchView.clearFocus();
                }

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prepareData();
    }

    @Background
    public void prepareData() {
        //todo debugging (no future assets files)
        copyFromAssetToInternal(pdfFileName);
        String filePath = getFilesDir() + "/" + pdfFileName;
        try {
            pdfReader = new PdfReader(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        rulePDFViewingFragment = RulePDFViewingFragment.create(pdfFileName, 0);
        changeFragment(rulePDFViewingFragment, false);
    }

    public void copyFromAssetToInternal(String pdfFileName) {
        AssetManager assetManager = getAssets();
        InputStream in = null;
        try {
            in = assetManager.open(pdfFileName);
            OutputStream out = openFileOutput(pdfFileName, MODE_PRIVATE);
            byte[] buffer = new byte[1024];
            int read = in.read(buffer);
            while (read != -1) {
                out.write(buffer, 0, read);
                read = in.read(buffer);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onPDFLoaded(RulePDFViewingFragment.OnPDFLoadedEvent event) {
        //todo handle stop loading

        RuleMenuContentFragment.bookmarkListHolder = event.getBookmarks();
        ruleMenuContentFragment = RuleMenuContentFragment.create();

        //todo stop loading here
    }


    @Subscribe
    public void changePageEvent(ContentMenuAdapter.ContentMenuItemClickedEvent event) {
        int pageNumber = -1;
        PdfDocument.Bookmark bookmark = event.getBookmark();
        if (bookmark != null) {
            pageNumber = (int) bookmark.getPageIdx();
        }
        switchToPDFViewer(pageNumber);
//        retainFragment(RulePDFViewingFragment.create(SAMPLE_FILE, (int) bookmark.getPageIdx()));

    }


    public void switchToPDFViewer(int pageNumber) {
        if (pageNumber >= 0) {
            Log.d(TAG, String.format("switchToPDFViewer: set page number = %s", pageNumber));
            rulePDFViewingFragment.putPageNumber(pageNumber);
        } else {
            Log.d(TAG, String.format("switchToPDFViewer: page number not change", pageNumber));
        }

        onBackPressed();

//        getSupportFragmentManager().beginTransaction()
//                .setCustomAnimations(R.anim.slide_to_right_enter, R.anim.slide_to_right_exit)
//                .replace(R.id.fl_container, rulePDFViewingFragment)
//                .commit();
//        retainFragment(ruleMenuContentFragment);
    }

    @Subscribe
    public void switchToMenuContent(ChangeToMenuContentFragmentEvent event) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_to_left_exit, R.anim.slide_to_left_exit, R.anim.slide_to_right_enter, R.anim.slide_to_right_exit)
                .addToBackStack(BACKSTACK_FRAGMENT_TAGS)
                .replace(R.id.fl_container, ruleMenuContentFragment)
                .commit();
    }

    @Subscribe
    public void changeFragmentEvent(ChangeFragmentEvent event) {
        changeFragment(event.getFragment(), event.isAddToBackstack());
    }

    public void retainFragment(Fragment fragment) {
        getSupportFragmentManager().popBackStack(BACKSTACK_FRAGMENT_TAGS
                , FragmentManager.POP_BACK_STACK_INCLUSIVE);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container, fragment)
                .setCustomAnimations(R.anim.slide_to_right_enter, R.anim.slide_to_right_exit)
                .commit();

    }

    @UiThread
    public void changeFragment(Fragment fragment, boolean addToBackStack) {
        if (addToBackStack)
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_to_left_exit, R.anim.slide_to_left_exit
                            , R.anim.slide_to_right_enter, R.anim.slide_to_right_exit)
                    .replace(R.id.fl_container, fragment)
                    .addToBackStack(BACKSTACK_FRAGMENT_TAGS)
                    .commit();
        else
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_to_left_exit, R.anim.slide_to_left_exit
                            , R.anim.slide_to_right_enter, R.anim.slide_to_right_exit)
                    .replace(R.id.fl_container, fragment)
                    .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d(TAG, "onOptionsItemSelected: home");
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListFragmentInteraction(MyRuleSearchResultRecyclerViewAdapter.RuleSearchResultItem item) {
        rulePDFViewingFragment.putPageNumber(item.getPageNumber() - 1);
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pdfReader.close();
    }

    @Override
    public PdfReader getPdfReader() {
        return pdfReader;
    }

    public MaterialSearchView getSearchView() {
        return searchView;
    }


    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.rules_search_only_menu, menu);
//        return true;
//    }


    /**
     * Listener for response to user permission request
     *
     * @param requestCode  Check that permission request code matches
     * @param permissions  Permissions that requested
     * @param grantResults Whether permissions granted
     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
//                                           @NonNull int[] grantResults) {
//        Log.d(TAG, "onRequestPermissionsResult: ");
//        if (requestCode == PERMISSION_CODE) {
//            if (grantResults.length > 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                launchPicker();
//            }
//        }
//    }

    public static class ChangeToMenuContentFragmentEvent {


    }

    public static class ChangeFragmentEvent {
        private Fragment fragment;
        private boolean addToBackstack;

        public ChangeFragmentEvent(Fragment fragment, boolean addToBackstack) {
            this.fragment = fragment;
            this.addToBackstack = addToBackstack;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public boolean isAddToBackstack() {
            return addToBackstack;
        }
    }
}
