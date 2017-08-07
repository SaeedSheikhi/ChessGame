package com.chess.engine.player.AI;


import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.Player;

import java.util.Collection;

/**
 * Created by Asus on 2017-08-02.
 */
public class AI extends Player {
    Alliance AIAlliance;

    public AI(final Board board,
              final Collection<Move> playerLegals,
              final Collection<Move> opponentLegals, Alliance AIAlliance) {
        super(board, playerLegals, opponentLegals);
        this.AIAlliance = AIAlliance;
    }



    @Override
    public Collection<Piece> getActivePieces() {
        return null;
    }

    @Override
    public Alliance getAlliance() {
        return null;
    }

    @Override
    public Player getOpponent() {
        return null;
    }

    @Override
    protected Collection<Move> calculateKingCastles(Collection<Move> playerLegals, Collection<Move> opponentLegals) {
        return null;
    }


}
