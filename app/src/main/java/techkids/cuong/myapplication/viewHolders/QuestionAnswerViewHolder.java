package techkids.cuong.myapplication.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.cuong.myapplication.R;
import techkids.cuong.myapplication.models.Answer;
import techkids.cuong.myapplication.models.QuestionAndAnswer;
import techkids.cuong.myapplication.transforms.CircleTransform;

/**
 * Created by Cuong on 1/7/2017.
 */
public class QuestionAnswerViewHolder extends RecyclerView.ViewHolder{

    private static final String TAG = QuestionAnswerViewHolder.class.toString();

    @BindView(R.id.tv_question)
    TextView tvQuestion;
//
//    @BindView(R.id.iv_avatar)
//    ImageView ivAvatar;

//    @BindView(R.id.tv_writer)
//    TextView tvWriter;
//
//    @BindView(R.id.tv_answer)
//    TextView tvAnswer;

    private QuestionAndAnswer qaa;

    public QuestionAnswerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(int position) {
        qaa = QuestionAndAnswer.questionAndAnswerList.get(position);

        tvQuestion.setText(qaa.getQuestion());

        Answer answer = qaa.getAnswer()[0];

//        String writer = String.format("%s : %s", ivAvatar.getContext().getResources().getString(R.string.answer_from), answer.getWriter());
//
//        tvWriter.setText(writer);
//
//        tvAnswer.setText(answer.getAnswer());
//
//        Picasso.with(itemView.getContext()).load("http://www.designofsignage.com/application/symbol/building/image/600x600/no-photo.jpg")
//                .transform(new CircleTransform()).into(ivAvatar);
    }
}
