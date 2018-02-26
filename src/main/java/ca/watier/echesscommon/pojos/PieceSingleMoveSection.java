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

package ca.watier.echesscommon.pojos;

import ca.watier.echesscommon.enums.CasePosition;
import ca.watier.echesscommon.enums.PgnMoveToken;
import ca.watier.echesscommon.enums.PgnPieceFound;
import ca.watier.echesscommon.game.PieceData;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.regex.Pattern;

public class PieceSingleMoveSection extends PieceData {
    private PgnPieceFound pgnPieceFound;
    private Character column; //Letters
    private Byte row; //Numbers
    private CasePosition to;

    public PieceSingleMoveSection(PgnPieceFound pgnPieceFound, Character column, Byte row, CasePosition to) {
        super(PgnMoveToken.NORMAL_MOVE);
        this.pgnPieceFound = pgnPieceFound;
        this.column = column;
        this.row = row;
        this.to = to;
    }

    private PieceSingleMoveSection() {
        super();
    }

    public static PieceSingleMoveSection getParsedActions(@NotNull String action) {
        PieceSingleMoveSection value = new PieceSingleMoveSection();
        PgnPieceFound pieceFromAction = PgnPieceFound.getPieceFromAction(action);
        value.setPgnPieceFound(pieceFromAction);

        //Remove all invalid tokens
        for (PgnMoveToken nonMovesToken : NON_MOVES_TOKENS) {
            for (String currentChar : nonMovesToken.getChars()) {
                action = action.replaceAll(Pattern.quote(currentChar), "");
            }
        }

        //Remove the piece letter from the string, if not a pawn
        if (!PgnPieceFound.PAWN.equals(pieceFromAction)) {
            action = action.substring(1);
        }

        int length = action.length();
        value.setTo(CasePosition.valueOf(action.substring(length - 2, length).toUpperCase()));

        //Remove the ending position
        action = action.substring(0, length - 2);

        switch (action.length()) {
            case 0:
                value.setColumn(null);
                value.setRow(null);
                break;
            case 1: //Row or column
                Character tmp = action.charAt(0);

                if (Character.isLetter(tmp)) {
                    value.setColumn(tmp);
                    value.setRow(null);
                } else if (Character.isDigit(tmp)) {
                    value.setColumn(null);
                    value.setRow((byte) Character.getNumericValue(tmp));
                } else {
                    throw new IllegalStateException("Invalid type of Character!");
                }
                break;
            case 2: //Full position
                CasePosition position = CasePosition.valueOf(action.toUpperCase());
                value.setColumn(position.getCol());
                value.setRow((byte) position.getRow());
                break;
            default:
                throw new IllegalStateException("Invalid length!");
        }


        return value;
    }

    public CasePosition getFromFullCoordinate() {
        CasePosition value = null;

        if (isFullSeparatedCoordinate()) {
            value = CasePosition.valueOf(Character.toString(column).toUpperCase() + row);
        } else if (isFullCoordinate()) {
            value = to;
        }

        return value;
    }

    private boolean isFullSeparatedCoordinate() {
        return row != null && column != null;
    }

    private boolean isFullCoordinate() {
        return row == null && column == null && to != null;
    }

    public boolean isFromPositionFullCoordinate() {
        return isFullSeparatedCoordinate() || isFullCoordinate();
    }

    public PgnPieceFound getPgnPieceFound() {
        return pgnPieceFound;
    }

    public void setPgnPieceFound(PgnPieceFound pgnPieceFound) {
        this.pgnPieceFound = pgnPieceFound;
    }

    public Character getColumn() {
        return column;
    }

    public void setColumn(Character column) {
        this.column = column;
    }

    public Byte getRow() {
        return row;
    }

    public void setRow(Byte row) {
        this.row = row;
    }

    public CasePosition getTo() {
        return to;
    }

    public void setTo(CasePosition to) {
        this.to = to;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pgnPieceFound, column, row, to);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PieceSingleMoveSection that = (PieceSingleMoveSection) o;
        return pgnPieceFound == that.pgnPieceFound &&
                Objects.equals(column, that.column) &&
                Objects.equals(row, that.row) &&
                to == that.to;
    }
}
