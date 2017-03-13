package techkids.cuong.myapplication.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import techkids.cuong.myapplication.R;
import techkids.cuong.myapplication.models.QuestionAndAnswer;
import techkids.cuong.myapplication.viewHolders.QuestionAnswerViewHolder;

/**
 * Created by Cuong on 1/7/2017.
 */
public class QuestionAnswerAdapter extends RecyclerView.Adapter<QuestionAnswerViewHolder> {

    @Override
    public QuestionAnswerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.item_question_answer, parent, false);

        QuestionAnswerViewHolder questionAnswerViewHolder = new QuestionAnswerViewHolder(view);

        return questionAnswerViewHolder;
    }

    @Override
    public void onBindViewHolder(QuestionAnswerViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return QuestionAndAnswer.questionAndAnswerList.size();
    }
}
