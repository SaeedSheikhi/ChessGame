package com.chess.engine.player.AI;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.player.BlackPlayer;

import java.util.Collection;

/**
 * Created by Asus on 2017-08-02.
 */
public class BlackAIPlayer extends BlackPlayer implements Runnable{
    public BlackAIPlayer(Board board, Collection<Move> playerLegals, Collection<Move> opponentLegals) {
        super(board, playerLegals, opponentLegals);
    }

    @Override
    public void run() {

    }
}
