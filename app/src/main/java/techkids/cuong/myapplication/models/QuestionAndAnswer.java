package techkids.cuong.myapplication.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Cuong on 1/6/2017.
 */



public class QuestionAndAnswer {

    private String question;

    private Answer[] answer;

    public QuestionAndAnswer(String question, Answer[] answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public Answer[] getAnswer() {
        return answer;
    }


    public static QuestionAndAnswer[] questionAndAnswersArrays = new QuestionAndAnswer[ ]{
            new QuestionAndAnswer("Mn ơi cho mình hỏi nếu bị thần cupid ghép cặp thì có biết mặt nhau không ạ .. ??",
                    new Answer[]{new Answer("Tùy nơi bạn ơi, quản trò là cha là mẹ nên do thằng quản trò quyết định hết, kiểu gì cũng được.. :V ", "Trương Vô Kỵ")}),
            new QuestionAndAnswer("Ae ơi cho tớ hỏi tại sao lần nào chơi cũng bị giết đầu tiên thế nhể ??",
                    new Answer[] {new Answer("Biết tại sao không ?? Tại vì bạn ngu.. :v ", "Cristiano Ronaldo")}),
            new QuestionAndAnswer("Game hay nhất mà e từng chơi :'( Ma sói là chiếc gương để phản ánh bạn là ai: dũng cảm hay nhút nhát, chủ động hay bị động, lý trí hay cảm xúc. Và cách bạn chơi ma sói sẽ thể hiện rất rõ cách bạn phản ứng như thế nào với cuộc sống của mình ở hiện tại. Nhưng ma sói cũng sẽ là công cụ giúp bạn rèn luyện những kỹ năng còn thiếu của mình để hoàn thiện hơn. Thiết kế của ma sói về các lá bì nhân vật nó sẽ tương tự giống nhu việc bạn nhìn mình là ai trong cuộc sống sẽ tác động đến cách hành xử, suy nghĩ lẫn thái độ của bạn ra sao.",
                    new Answer[] {new Answer("Ờ", "Donald Trump")}),
            new QuestionAndAnswer("Khi chiến binh bảo vệ mục tiêu bị phù thủy giết thì mục tiêu có sống không ? ",
                    new Answer[] {new Answer("Chết sure vcl bạn nhé, chiến binh chỉ bảo vệ mục tiêu bị sói cắn thôi còn phù thủy thì chịu nhé", "Quách Tĩnh")})
    };

    public static List<QuestionAndAnswer> questionAndAnswerList = Arrays.asList(QuestionAndAnswer.questionAndAnswersArrays);

    @Override
    public String toString() {
        return "QuestionAndAnswer{" +
                "question='" + question + '\'' +
                ", answer=" + Arrays.toString(answer) +
                '}';
    }
}
