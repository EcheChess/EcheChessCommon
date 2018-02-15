/*
 *    Copyright 2014 - 2018 Yannick Watier
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package ca.watier.echesscommon.game;

import ca.watier.echesscommon.enums.CasePosition;
import ca.watier.echesscommon.enums.Pieces;
import ca.watier.echesscommon.enums.Ranks;
import ca.watier.echesscommon.enums.Side;
import ca.watier.echesscommon.interfaces.BaseUtils;
import ca.watier.echesscommon.utils.*;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * Created by yannick on 6/29/2017.
 */
public abstract class GameBoard {

    //The default position of the board
    private final Map<CasePosition, Pieces> defaultPositions;
    //The pieces position on the board
    private Map<CasePosition, Pieces> positionPiecesMap;
    //Used to check if the piece have moved
    private Map<CasePosition, Boolean> isPiecesMovedMap;
    //Used to check if the pawn used it's special ability to move by two case
    private Map<CasePosition, Boolean> isPawnUsedSpecialMoveMap;
    //Used to track the turn that the piece have moved
    private Map<CasePosition, Integer> turnNumberPieceMap;
    //Used to track the pawn promotions
    private MultiArrayMap<Side, Pair<CasePosition, CasePosition>> pawnPromotionMap;
    //Used to track the number of turn of each player
    private int blackTurnNumber;
    private int whiteTurnNumber;
    private int totalMove = 0;

    private boolean isGameDraw = false;
    private boolean isGamePaused = false;

    public GameBoard() {
        defaultPositions = new EnumMap<>(CasePosition.class);
        positionPiecesMap = GameUtils.getDefaultGame();
        defaultPositions.putAll(positionPiecesMap);
        isPiecesMovedMap = GameUtils.initNewMovedPieceMap(positionPiecesMap);
        isPawnUsedSpecialMoveMap = GameUtils.initPawnMap(positionPiecesMap);
        turnNumberPieceMap = GameUtils.initTurnMap(positionPiecesMap);
        pawnPromotionMap = new MultiArrayMap<>();
    }

    /**
     * Find the position for a column and a rank
     *
     * @param rank
     * @param column
     */
    public CasePosition getPositionByRankAndColumn(@NotNull Ranks rank, char column, @NotNull Side side) {
        return Arrays.stream(CasePosition.values()).filter(casePosition -> rank.equals(Ranks.getRank(casePosition, side))
                && casePosition.isOnSameColumn(column)).findFirst().orElse(null);
    }

    /**
     * Get an unmodifiable {@link java.util.Collections.UnmodifiableMap} of the current game
     *
     * @return
     */
    public final Map<CasePosition, Pieces> getPiecesLocation() {
        return Collections.unmodifiableMap(positionPiecesMap);
    }


    public final void addPawnPromotion(CasePosition from, CasePosition to, Side side) {
        Assert.assertNotNull(from, to, side);

        if (Side.OBSERVER.equals(side)) {
            return;
        }

        pawnPromotionMap.put(side, new Pair<>(from, to));
    }

    /**
     * Set the specified case at the position
     *
     * @param piece
     * @param from
     * @param to
     */
    public final void setPiecePosition(Pieces piece, CasePosition from, CasePosition to) {
        positionPiecesMap.put(to, piece);
        changeMovedStateOfPiece(piece, from, to);
    }

    /**
     * If it's the default from of the piece, mark this one as moved
     *
     * @param piece
     * @param from
     * @param to
     */
    private void changeMovedStateOfPiece(Pieces piece, CasePosition from, CasePosition to) {
        Assert.assertNotNull(piece, from, to);


        boolean isValid = BaseUtils.getSafeBoolean(isPiecesMovedMap.get(from)) || GameUtils.isDefaultPosition(from, piece, this);
        isPiecesMovedMap.put(to, isValid);
        isPiecesMovedMap.remove(from);
    }

    /**
     * Set the specified case at the position, without changing the move state (useful when evaluating)
     *
     * @param piece
     * @param to
     */
    public final void setPiecePositionWithoutMoveState(Pieces piece, CasePosition to) {
        positionPiecesMap.put(to, piece);
    }

    /**
     * Get the piece at the specific position
     *
     * @param position
     * @return
     */
    public final Pieces getPiece(CasePosition position) {
        Assert.assertNotNull(position);

        return positionPiecesMap.get(position);
    }

    /**
     * Change a piece position, there's no check/constraint(s) on this method (Direct access to the Map)
     *
     * @param from
     * @param to
     * @param piece
     */
    protected final void movePieceTo(CasePosition from, CasePosition to, Pieces piece) {
        Assert.assertNotNull(from, to, piece);

        positionPiecesMap.remove(from);
        positionPiecesMap.put(to, piece);
        changeMovedStateOfPiece(piece, from, to);
        changePawnSpecialMove(piece, from, to);
        updatePlayerTurnValue(piece.getSide());
        changePieceTurnNumber(from, to);
        totalMove++;
    }

    /**
     * Change the state of the pawn if the move is 2
     *
     * @param piece
     * @param from
     * @param to
     */
    private void changePawnSpecialMove(Pieces piece, CasePosition from, CasePosition to) {
        Assert.assertNotNull(from, to, piece);

        if (Pieces.isPawn(piece)) {
            boolean isValid = BaseUtils.getSafeBoolean(isPawnUsedSpecialMoveMap.get(from)) || MathUtils.getDistanceBetweenPositions(from, to) == 2;
            isPawnUsedSpecialMoveMap.put(to, isValid);
            isPawnUsedSpecialMoveMap.remove(from);
        }
    }

    /**
     * Update the turn number of the player (based on the color of the piece)
     *
     * @param side
     */
    private void updatePlayerTurnValue(Side side) {
        Assert.assertNotNull(side);

        switch (side) {
            case WHITE:
                whiteTurnNumber++;
                break;
            case BLACK:
                blackTurnNumber++;
                break;
            case OBSERVER:
            default:
                break;
        }
    }

    private void changePieceTurnNumber(CasePosition from, CasePosition to) {
        Assert.assertNotNull(from, to);

        turnNumberPieceMap.remove(from);
        turnNumberPieceMap.put(to, totalMove);
    }

    /**
     * Get the turn number based on a {@link CasePosition}
     *
     * @param position
     * @return
     */
    public final Integer getPieceTurn(CasePosition position) {
        Assert.assertNotNull(position);

        return turnNumberPieceMap.get(position);
    }

    /**
     * Remove a piece from the board
     *
     * @param from
     */
    public final void removePieceAt(CasePosition from) {
        Assert.assertNotNull(from);

        positionPiecesMap.remove(from);
        isPiecesMovedMap.remove(from);
        isPawnUsedSpecialMoveMap.remove(from);
        turnNumberPieceMap.remove(from);
    }

    /**
     * Check if the piece is moved, return null if the position is invalid
     *
     * @param position
     * @return
     */
    public final Boolean isPieceMoved(CasePosition position) {
        Assert.assertNotNull(position);

        return isPiecesMovedMap.get(position);
    }

    /**
     * Return true if the pawn used the special move
     *
     * @param position
     * @return
     */
    public final boolean isPawnUsedSpecialMove(CasePosition position) {
        Assert.assertNotNull(position);

        return BaseUtils.getSafeBoolean(isPawnUsedSpecialMoveMap.get(position));
    }

    public Map<CasePosition, Pieces> getDefaultPositions() {
        return Collections.unmodifiableMap(defaultPositions);
    }

    public final void setPositionPiecesMap(Map<CasePosition, Pieces> positionPiecesMap) {
        Assert.assertNotEmpty(positionPiecesMap);

        this.positionPiecesMap = positionPiecesMap;
        this.defaultPositions.clear();
        this.defaultPositions.putAll(positionPiecesMap);
        this.isPiecesMovedMap = GameUtils.initNewMovedPieceMap(positionPiecesMap);
        this.turnNumberPieceMap = GameUtils.initTurnMap(positionPiecesMap);
    }

    public int getBlackTurnNumber() {
        return blackTurnNumber;
    }

    public int getWhiteTurnNumber() {
        return whiteTurnNumber;
    }

    public int getNbTotalMove() {
        return totalMove;
    }

    public final boolean upgradePiece(CasePosition to, Pieces pieces, Side playerSide) {
        Assert.assertNotNull(pieces, playerSide);

        Pair<CasePosition, CasePosition> pair = null;
        for (Pair<CasePosition, CasePosition> casePositionCasePositionPair : BaseUtils.getSafeList(pawnPromotionMap.get(playerSide))) {
            CasePosition toValue = casePositionCasePositionPair.getSecondValue();

            if (to.equals(toValue)) {
                pair = casePositionCasePositionPair;
                break;
            }
        }

        boolean isPresent = pair != null;
        if (isPresent) {
            removePawnPromotion(pair, playerSide);
            CasePosition currentPawnFromPosition = pair.getFirstValue();

            positionPiecesMap.remove(currentPawnFromPosition); //remove the pawn
            positionPiecesMap.put(to, pieces); // add the wanted piece
            isGamePaused = false;
        }


        return isPresent;
    }

    private void removePawnPromotion(Pair<CasePosition, CasePosition> pair, Side side) {
        Assert.assertNotNull(pair, side);

        if (Side.OBSERVER.equals(side)) {
            return;
        }

        pawnPromotionMap.removeFromList(side, pair);
    }

    public Map<CasePosition, Boolean> getIsPiecesMovedMap() {
        return isPiecesMovedMap;
    }

    public void setIsPiecesMovedMap(Map<CasePosition, Boolean> isPiecesMovedMap) {
        this.isPiecesMovedMap = isPiecesMovedMap;
    }

    public Map<CasePosition, Boolean> getIsPawnUsedSpecialMoveMap() {
        return isPawnUsedSpecialMoveMap;
    }

    public void setIsPawnUsedSpecialMoveMap(Map<CasePosition, Boolean> isPawnUsedSpecialMoveMap) {
        this.isPawnUsedSpecialMoveMap = isPawnUsedSpecialMoveMap;
    }

    public Map<CasePosition, Integer> getTurnNumberPieceMap() {
        return turnNumberPieceMap;
    }

    public void setTurnNumberPieceMap(Map<CasePosition, Integer> turnNumberPieceMap) {
        this.turnNumberPieceMap = turnNumberPieceMap;
    }

    public boolean isGamePaused() {
        return isGamePaused;
    }

    public void setGamePaused(boolean gamePaused) {
        isGamePaused = gamePaused;
    }

    public boolean isGameDraw() {
        return isGameDraw;
    }
}
