package com.chess.engine.player.AI;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.player.WhitePlayer;

import java.util.Collection;

/**
 * Created by Asus on 2017-08-02.
 */
public class WhiteAIPlayer extends WhitePlayer implements Runnable{





    public WhiteAIPlayer(Board board, Collection<Move> playerLegals, Collection<Move> opponentLegals) {
        super(board, playerLegals, opponentLegals);

    }

    @Override
    public void run() {

        while(board.currentPlayer().getAlliance() == Alliance.WHITE){

            legalMoves.toArray();


        }




    }
}
