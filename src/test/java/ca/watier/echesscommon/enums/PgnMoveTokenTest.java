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

import ca.watier.echesscommon.enums.PgnMoveToken.PieceDataSection;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class PgnMoveTokenTest {

    @Test
    public void getParsedActionsTest() {
        List<PieceDataSection> pieceFromAction = PgnMoveToken.getParsedActions("axb1=Q#");
        assertThat(pieceFromAction).contains(
                PgnMoveToken.PieceDataSection.from("a", "b1", PgnMoveToken.CAPTURE),
                PgnMoveToken.PieceDataSection.from("b1", "Q", PgnMoveToken.PAWN_PROMOTION)
        );


        pieceFromAction = PgnMoveToken.getParsedActions("Qa6xb7#");
        assertThat(pieceFromAction).contains(
                PgnMoveToken.PieceDataSection.from("Qa6", "b7", PgnMoveToken.CAPTURE)
        );


        pieceFromAction = PgnMoveToken.getParsedActions("fxg1=Q+");
        assertThat(pieceFromAction).contains(
                PgnMoveToken.PieceDataSection.from("f", "g1", PgnMoveToken.CAPTURE),
                PgnMoveToken.PieceDataSection.from("g1", "Q", PgnMoveToken.PAWN_PROMOTION)
        );

    }
}