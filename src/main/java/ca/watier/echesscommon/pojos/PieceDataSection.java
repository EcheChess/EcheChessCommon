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

import ca.watier.echesscommon.enums.PgnMoveToken;
import ca.watier.echesscommon.game.PieceData;
import ca.watier.echesscommon.utils.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class PieceDataSection extends PieceData {
    private String before;
    private String after;

    public PieceDataSection(String before, String after, PgnMoveToken token) {
        super(token);
        this.before = before;
        this.after = after;
    }

    private PieceDataSection() {
        super();
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
