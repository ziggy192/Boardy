package techkids.cuong.myapplication.events;

import techkids.cuong.myapplication.models.BoardGame;

/**
 * Created by Cuong on 2/18/2017.
 */
public class BoardGameEvent {

    private BoardGame boardGame;

    public BoardGameEvent(BoardGame boardGame) {
        this.boardGame = boardGame;
    }

    public BoardGame getBoardGame() {
        return boardGame;
    }
}
