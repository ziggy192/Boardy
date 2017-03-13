package techkids.cuong.myapplication.fragments;


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.devspark.progressfragment.ProgressFragment;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.shockwave.pdfium.PdfDocument;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.cuong.myapplication.MyLocationTextExtractionStrategy;
import techkids.cuong.myapplication.R;
import techkids.cuong.myapplication.activities.RuleActivity;

/**
 * A simple {@link Fragment} subclass.
 */


@EFragment
public class RulePDFViewingFragment extends ProgressFragment implements OnPageChangeListener, OnLoadCompleteListener {


    private static final String TAG = RulePDFViewingFragment.class.toString();
    @BindView(R.id.pdfView)
    PDFView pdfView;

    View mContentView;
    MaterialSearchView searchView;

    String pdfFileName;
    int pageNumber = 0;
    private boolean isLoaded = false;


    public RulePDFViewingFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContentView = inflater.inflate(R.layout.fragment_rule_pdfviewing, container, false);
        ButterKnife.bind(this, mContentView);
        setHasOptionsMenu(true);
        Log.d(TAG, String.format("onCreateView: page number = %s", pageNumber));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: ");
        super.onActivityCreated(savedInstanceState);


        setContentView(mContentView);
        setEmptyText(getString(R.string.error_loading_file));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: getting searchview references");
        if (context instanceof RuleActivity) {
            searchView = ((RuleActivity) context).getSearchView();
        }else{
            Log.e(TAG, "onAttach: Activity not RuleActivity");
            searchView = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        EventBus.getDefault().register(this);
        Log.d(TAG, "onStart: ");
        obtainData();

        if (isLoaded) {
            if (pdfView.getCurrentPage() != pageNumber) {
                pdfView.jumpTo(pageNumber,true);
            }
        }
//        displayFromAsset(pdfFileName);
        //// TODO: 3/12/2017 uncomment this
//        try {
//            testIText(pdfFileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//        loadPDFFromInternal(pdfFileName);
//        loadPDFViewFromInternal(pdfFileName);
    }

    @Override
    public void onStop() {
        super.onStop();
//        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: ");
        super.onViewCreated(view, savedInstanceState);
        getArgs();
        getActivity().setTitle(pdfFileName);
        isLoaded = false;

    }


    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        if (isLoaded) {
            inflater.inflate(R.menu.rules_menu, menu);
            MenuItem item = menu.findItem(R.id.action_search);
            searchView.setMenuItem(item);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bookmarks:
                EventBus.getDefault().post(new RuleActivity.ChangeToMenuContentFragmentEvent());
                Log.d(TAG, "onOptionsItemSelected: R.id.action_bookmarks");
                return true;
//            case R.id.action_search:
//                Log.d(TAG, "onOptionsItemSelected: R.id.action_search");
//                EventBus.getDefault().post(new RuleActivity.ChangeFragmentEvent
//                        (RuleSearchResultFragment.create(pdfFileName),true));
//                return true;
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void obtainData() {
        setContentShown(false);
        loadPDFFromInternal(pdfFileName);
    }
    private void displayFromAsset(String assetFileName) {
        Log.d(TAG, String.format("displayFromAsset: page number = %s", pageNumber));
        pdfView.fromAsset(assetFileName)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(getContext()))
                .load();
    }

    private void loadPDFFromInternal(String pdfFileName) {
        isLoaded = false;
        String filePath = getActivity().getFilesDir() + "/" + pdfFileName;
        pdfView.fromFile(new File(filePath))
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(getContext()))
                .load();
        setContentShown(true);
    }


    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
//        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }

    public void putPageNumber(int pageNumber) {
        getArguments().putInt(START_PAGE_NUMBER_KEY, pageNumber);
    }

    @Override
    public void loadComplete(int nbPages) {
        setContentShown(true);
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        if (meta == null) {
            Toast.makeText(getContext(), "meta == null", Toast.LENGTH_SHORT).show();
        } else {
            Log.e(TAG, "title = " + meta.getTitle());
            Log.e(TAG, "author = " + meta.getAuthor());
            Log.e(TAG, "subject = " + meta.getSubject());
            Log.e(TAG, "keywords = " + meta.getKeywords());
            Log.e(TAG, "creator = " + meta.getCreator());
            Log.e(TAG, "producer = " + meta.getProducer());
            Log.e(TAG, "creationDate = " + meta.getCreationDate());
            Log.e(TAG, "modDate = " + meta.getModDate());
        }
//        printBookmarksTree(pdfView.getTableOfContents(), "-");
        EventBus.getDefault().post(new OnPDFLoadedEvent(pdfView.getTableOfContents()));
        isLoaded = true;
        getActivity().invalidateOptionsMenu();

    }


    public void testIText(String fileName) throws IOException, DocumentException {
        /*todo
        * read simplefile from assets
        * draw a big highlight rectangle on the first page
        * save it in internal
        * load pdfView from internal
        *
        * */
        Log.d(TAG, "testIText: before open file");
        PdfReader pdfReader = new PdfReader(getActivity().getAssets().open(fileName));
        Log.d(TAG, "testIText: after open file");
        String dest = "highlighted.pdf";

        FileOutputStream fos = getActivity().openFileOutput(dest, Context.MODE_PRIVATE);
        PdfStamper stamper = new PdfStamper(pdfReader, fos);
        Rectangle bigCoordinate = new Rectangle(100, 270, 800, 720);

        PdfContentByte canvas = stamper.getUnderContent(1);
//        canvas.saveState();
        canvas.setColorFill(BaseColor.RED);
        canvas.rectangle(bigCoordinate);
        canvas.fill();

        canvas = stamper.getUnderContent(2);
//        canvas.saveState();
        canvas.setColorFill(BaseColor.BLUE);
        canvas.rectangle(bigCoordinate);
        canvas.fill();
//        canvas.restoreState();

//        PdfAnnotation annotation = PdfAnnotation.createSquareCircle(
//                stamper.getWriter(), bigCoordinate,
//                "Tickets available", true);
//        annotation.setColor(BaseColor.BLUE);
//        annotation.setFlags(PdfAnnotation.FLAGS_PRINT);
//        stamper.addAnnotation(annotation, 1);
//        stamper.addAnnotation(annotation, 2);

        stamper.close();
        fos.close();

        Log.d(TAG, "testIText: after added annotation");
        loadPDFFromInternal(dest);
    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        Log.d(TAG, String.format("printBookmarksTree: %s", tree.size()));
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));
            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    //for future highlight searching
    public void startSearchFromInternal(String fileName) throws IOException, DocumentException {
        //todo for future highlight searching

        String query = "and";
        //readFromINternal
        String filePath = getActivity().getFilesDir() + "/" + fileName;
        PdfReader pdfReader = new PdfReader(filePath);
        PdfReaderContentParser parser = new PdfReaderContentParser(pdfReader);
        MyLocationTextExtractionStrategy strategy;
        List<Rectangle> coordinates;

        String dest = "highlighted.pdf";
        boolean foundSth = false;
        FileOutputStream fos = getActivity().openFileOutput(dest, Context.MODE_PRIVATE);
        PdfStamper stamper = new PdfStamper(pdfReader, fos);

//        ArrayList<MyRuleSearchResultRecyclerViewAdapter.RuleSearchResultItem> searchResults = new ArrayList<>();
        for (int page = 1; page <= pdfReader.getNumberOfPages(); page++) {
            strategy = parser.processContent(page, new MyLocationTextExtractionStrategy());
            coordinates = (strategy.search(query));
            if (!coordinates.isEmpty()) {
                // add new highligted pdf and reload it from internal
                Log.d(TAG, String.format("startSearchFromInternal: found in page %s, first coordinate = %.2f", page, coordinates.get(0).getLeft()));

                foundSth = true;


//                PdfContentByte canvas = stamper.getUnderContent(page);
//                canvas.saveState();
//                canvas.setColorFill(BaseColor.YELLOW);

                for (Rectangle wordCoordinate : coordinates) {
//                    Rectangle bigCoordinate = new Rectangle(wordCoordinate.getLeft() - 100
//                            , wordCoordinate.getBottom() + 100,
//                            wordCoordinate.getRight() + 100,
//                            wordCoordinate.getTop() - 100);
                    Rectangle bigCoordinate = new Rectangle(150, 770, 200, 820);

                    PdfAnnotation ann = PdfAnnotation.createSquareCircle(stamper.getWriter(), bigCoordinate, "ahhihi", true);
                    ann.setColor(BaseColor.RED);
                    ann.setFlags(PdfAnnotation.FLAGS_PRINT);
                    stamper.addAnnotation(ann, page);
//                    canvas.rectangle(wordCoordinate);
//                    canvas.fill();
                }
//                canvas.restoreState();
            }

        }


        stamper.flush();
        stamper.close();
        fos.flush();
        fos.close();
        if (foundSth) {
            Log.d(TAG, "startSearchFromInternal: displaying highlighted docs");
            loadPDFFromInternal(dest);
        }
//        reader.close();
        pdfReader.close();

    }

    private void setPdfFileName(String pdfFileName) {
        this.pdfFileName = pdfFileName;
    }

    public static RulePDFViewingFragment create(String pdfFileName, int startPageNumber) {
        RulePDFViewingFragment fragment = new RulePDFViewingFragment_();
        Bundle args = new Bundle();
        args.putString(PDF_FILE_NAME_KEY, pdfFileName);
        args.putInt(START_PAGE_NUMBER_KEY, startPageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    private static String PDF_FILE_NAME_KEY = "pdfFileName";
    private static final String START_PAGE_NUMBER_KEY = "start page number";

    public void getArgs() {
        Bundle args = getArguments();
        pdfFileName = args.getString(PDF_FILE_NAME_KEY);
        pageNumber = args.getInt(START_PAGE_NUMBER_KEY);
    }

    public static class OnPDFLoadedEvent {
        private List<PdfDocument.Bookmark> bookmarks;

        public OnPDFLoadedEvent(List<PdfDocument.Bookmark> bookmarks) {
            this.bookmarks = bookmarks;
        }

        public List<PdfDocument.Bookmark> getBookmarks() {
            return bookmarks;
        }
    }
}
