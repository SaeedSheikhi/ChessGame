package com.chess.engine.player;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.board.MoveStatus;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Asus on 2017-07-08.
 */
public final class MoveTransition {

    private final Board fromBoard;
    private final Board toBoard;
    private final Move transitionMove;
    private final MoveStatus moveStatus;

    public MoveTransition(final Board fromBoard,
                          final Board toBoard,
                          final Move transitionMove,
                          final MoveStatus moveStatus) {
        this.fromBoard = fromBoard;
        this.toBoard = toBoard;
        this.transitionMove = transitionMove;
        this.moveStatus = moveStatus;
    }

    public Board getFromBoard() {
        return this.fromBoard;
    }

    public Board getToBoard() {
        return this.toBoard;
    }

    public Move getTransitionMove() {
        return this.transitionMove;
    }

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }
}
