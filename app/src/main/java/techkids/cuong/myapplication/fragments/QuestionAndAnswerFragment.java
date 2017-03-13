package techkids.cuong.myapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.cuong.myapplication.R;
import techkids.cuong.myapplication.adapters.QuestionAnswerAdapter;
import techkids.cuong.myapplication.events.HideToolbarEvent;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionAndAnswerFragment extends Fragment {


    @BindView(R.id.rv_question_and_answer_list)
    RecyclerView rvQuestionAnswer;

    private QuestionAnswerAdapter adapter;

    public QuestionAndAnswerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question_and_answer, container, false);
        ButterKnife.bind(this, view);
        setupUI();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
//        EventBus.getDefault().post(new HideToolbarEvent(false, false));
    }

    private void setupUI() {
        adapter = new QuestionAnswerAdapter();
        rvQuestionAnswer.setLayoutManager(new LinearLayoutManager(getContext()));
        rvQuestionAnswer.setAdapter(adapter);
    }

}
