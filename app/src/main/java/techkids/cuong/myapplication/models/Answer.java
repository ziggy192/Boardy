package techkids.cuong.myapplication.models;

/**
 * Created by Cuong on 1/7/2017.
 */
public class Answer{
    private String answer;

    private String writer;

    public Answer(String answer, String writer) {
        this.answer = answer;
        this.writer = writer;
    }

    public String getAnswer() {
        return answer;
    }

    public String getWriter() {
        return writer;
    }
}
