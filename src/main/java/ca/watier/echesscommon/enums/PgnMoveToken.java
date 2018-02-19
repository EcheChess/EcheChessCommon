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

package ca.watier.echesscommon.enums;


import ca.watier.echesscommon.utils.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static ca.watier.echesscommon.enums.CasePosition.*;

public enum PgnMoveToken {
    CAPTURE("x"),
    CHECK("+"),
    CHECKMATE("#", "++"),
    PAWN_PROMOTION("="),
    KINGSIDE_CASTLING("O-O"),
    QUEENSIDE_CASTLING("O-O-O"),
    KINGSIDE_CASTLING_CHECK("O-O+"),
    QUEENSIDE_CASTLING_CHECK("O-O-O+"),
    KINGSIDE_CASTLING_CHECKMATE("O-O#"),
    QUEENSIDE_CASTLING_CHECKMATE("O-O-O#"),
    NORMAL_MOVE("\0");

    private static final List<PgnMoveToken> BASIC_MOVES = Arrays.asList(CAPTURE, PAWN_PROMOTION);
    private static final List<PgnMoveToken> NON_MOVES_TOKENS = Arrays.asList( //The order of this list is important for the CASTLING, when removing the tokens
            CHECK,
            CHECKMATE,
            QUEENSIDE_CASTLING,
            KINGSIDE_CASTLING,
            QUEENSIDE_CASTLING_CHECK,
            KINGSIDE_CASTLING_CHECK,
            QUEENSIDE_CASTLING_CHECKMATE,
            KINGSIDE_CASTLING_CHECKMATE
    );
    private List<String> chars = new ArrayList<>();

    PgnMoveToken(@NotNull String... chars) {
        if (chars.length > 0) {
            this.chars.addAll(Arrays.asList(chars));
        }
    }

    public static List<PgnMoveToken> getPieceMovesFromLetter(@NotNull String action) {
        List<PgnMoveToken> moves = new ArrayList<>();

        if (QUEENSIDE_CASTLING.getChars().contains(action)) {  //The Queen side casting token contains also the king side (O-O in O-O-O...)
            moves.add(QUEENSIDE_CASTLING);
        } else if (KINGSIDE_CASTLING.getChars().contains(action)) {
            moves.add(KINGSIDE_CASTLING);
        } else if (QUEENSIDE_CASTLING_CHECK.getChars().contains(action)) {
            moves.add(QUEENSIDE_CASTLING_CHECK);
        } else if (KINGSIDE_CASTLING_CHECK.getChars().contains(action)) {
            moves.add(KINGSIDE_CASTLING_CHECK);
        } else if (QUEENSIDE_CASTLING_CHECKMATE.getChars().contains(action)) {
            moves.add(QUEENSIDE_CASTLING_CHECKMATE);
        } else if (KINGSIDE_CASTLING_CHECKMATE.getChars().contains(action)) {
            moves.add(KINGSIDE_CASTLING_CHECKMATE);
        } else {
            for (PgnMoveToken pgnMoveToken : values()) {
                for (String current : pgnMoveToken.getChars()) {
                    if (action.contains(current)) {
                        switch (pgnMoveToken) { //The moves that contain a "normal move"
                            case CAPTURE:
                            case CHECK:
                            case CHECKMATE:
                            case PAWN_PROMOTION:
                                if (!moves.contains(NORMAL_MOVE)) {
                                    moves.add(NORMAL_MOVE);
                                }
                                break;
                        }
                        moves.add(pgnMoveToken);
                    }
                }
            }

            if (moves.isEmpty()) {
                moves.add(NORMAL_MOVE);
            }
        }

        return moves;
    }

    public List<String> getChars() {
        return chars;
    }

    public static List<PieceDataSection> getParsedActions(@NotNull String action) {
        List<PieceDataSection> values = new ArrayList<>();
        List<Pair<PgnMoveToken, Integer>> indexOfActions = new ArrayList<>();

        //Remove all invalid tokens
        for (PgnMoveToken nonMovesToken : NON_MOVES_TOKENS) {
            for (String currentChar : nonMovesToken.getChars()) {
                action = action.replaceAll(Pattern.quote(currentChar), "");
            }
        }

        //Find the positions of the tokens in the string
        for (PgnMoveToken current : BASIC_MOVES) {
            for (String currentChar : current.getChars()) {
                int index = action.indexOf(currentChar);

                while (index >= 0) {
                    indexOfActions.add(new Pair<>(current, index));
                    index = action.indexOf(currentChar, index + 1);
                }
            }
        }

        int lastIndex = 0;
        int cuttingPos;
        String before;
        String after;
        for (int i = 0, indexOfActionsSize = indexOfActions.size(); i < indexOfActionsSize; i++) {

            Pair<PgnMoveToken, Integer> currentPair = indexOfActions.get(i);
            PgnMoveToken currentPgnMoveToken = currentPair.getFirstValue();
            Integer currentIndex = currentPair.getSecondValue();

            if (currentIndex == null) {
                continue;
            }

            Pair<PgnMoveToken, Integer> nextPair = (i < (indexOfActionsSize - 1)) ? indexOfActions.get(i + 1) : null;
            PieceDataSection currentSection = new PieceDataSection();
            currentSection.setToken(currentPgnMoveToken);

            before = action.substring(lastIndex, currentIndex);
            cuttingPos = currentIndex + 1; //To remove the current character from the substring

            after = (nextPair != null) ?
                    action.substring(cuttingPos, nextPair.getSecondValue()) :
                    action.substring(cuttingPos, action.length());

            currentSection.setBefore(before);
            currentSection.setAfter(after);
            values.add(currentSection);
            lastIndex = cuttingPos;
        }

        return values;
    }

    public static CasePosition getCastlingRookPosition(PgnMoveToken pgnMoveToken, Side playerSide) {
        if (!PgnMoveToken.isCastling(pgnMoveToken)) {
            return null;
        }

        CasePosition value = null;
        boolean isQueenSide = PgnMoveToken.isQueenSideCastling(pgnMoveToken);


        switch (playerSide) {
            case BLACK:
                value = (isQueenSide ? A8 : H8);
                break;
            case WHITE:
                value = (isQueenSide ? A1 : H1);
                break;
        }

        return value;
    }

    private static boolean isCastling(PgnMoveToken pgnMoveToken) {
        return isQueenSideCastling(pgnMoveToken) || isKingSideCastling(pgnMoveToken);
    }

    private static boolean isQueenSideCastling(PgnMoveToken pgnMoveToken) {
        return PgnMoveToken.QUEENSIDE_CASTLING.equals(pgnMoveToken) ||
                PgnMoveToken.QUEENSIDE_CASTLING_CHECK.equals(pgnMoveToken) ||
                PgnMoveToken.QUEENSIDE_CASTLING_CHECKMATE.equals(pgnMoveToken);
    }

    private static boolean isKingSideCastling(PgnMoveToken pgnMoveToken) {
        return PgnMoveToken.KINGSIDE_CASTLING.equals(pgnMoveToken) ||
                PgnMoveToken.KINGSIDE_CASTLING_CHECK.equals(pgnMoveToken) ||
                PgnMoveToken.KINGSIDE_CASTLING_CHECKMATE.equals(pgnMoveToken);
    }

    public static class PieceDataSection {
        private String before;
        private String after;
        private PgnMoveToken token;

        public PieceDataSection(String before, String after, PgnMoveToken token) {
            this.before = before;
            this.after = after;
            this.token = token;
        }

        public PieceDataSection() {
        }

        public static PieceDataSection from(String before, String after, PgnMoveToken token) {
            return new PieceDataSection(before, after, token);
        }


        public String getBefore() {
            return before;
        }

        public void setBefore(String before) {
            this.before = before;
        }

        public String getAfter() {
            return after;
        }

        public void setAfter(String after) {
            this.after = after;
        }

        public PgnMoveToken getToken() {
            return token;
        }

        public void setToken(PgnMoveToken token) {
            this.token = token;
        }

        @Override
        public int hashCode() {
            return Objects.hash(before, after, token);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PieceDataSection that = (PieceDataSection) o;
            return Objects.equals(before, that.before) &&
                    Objects.equals(after, that.after) &&
                    token == that.token;
        }

        @Override
        public String toString() {
            return "PieceDataSection{" +
                    "before='" + before + '\'' +
                    ", after='" + after + '\'' +
                    ", token=" + token +
                    '}';
        }
    }
}
