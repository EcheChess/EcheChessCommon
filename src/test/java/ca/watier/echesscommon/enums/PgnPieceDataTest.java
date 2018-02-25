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

import ca.watier.echesscommon.factories.PieceDataFactory;
import ca.watier.echesscommon.pojos.PieceDataSection;
import ca.watier.echesscommon.pojos.PieceSingleMoveSection;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class PgnPieceDataTest {

    @Test
    public void getParsedActionsTest() {
        assertThat(PieceDataSection.getParsedActions("axb1=Q#")).contains(
                PieceDataFactory.from("a", "b1", PgnMoveToken.CAPTURE),
                PieceDataFactory.from("b1", "Q", PgnMoveToken.PAWN_PROMOTION)
        );

        assertThat(PieceDataSection.getParsedActions("Qa6xb7#")).contains(
                PieceDataFactory.from("Qa6", "b7", PgnMoveToken.CAPTURE)
        );

        assertThat(PieceDataSection.getParsedActions("fxg1=Q+")).contains(
                PieceDataFactory.from("f", "g1", PgnMoveToken.CAPTURE),
                PieceDataFactory.from("g1", "Q", PgnMoveToken.PAWN_PROMOTION)
        );

    }


    @Test
    public void getParsedSingleMoveTest() {
        assertThat(PieceSingleMoveSection.getParsedActions("Nge2")).
                isEqualTo(PieceDataFactory.from(PgnPieceFound.KNIGHT, 'g', null, CasePosition.E2));

        PieceSingleMoveSection pieceSingleMoveSectionNg2e2 = PieceSingleMoveSection.getParsedActions("Ng2e2");
        assertThat(pieceSingleMoveSectionNg2e2).
                isEqualTo(PieceDataFactory.from(PgnPieceFound.KNIGHT, 'g', (byte) 2, CasePosition.E2));

        CasePosition fromFullCordinate = pieceSingleMoveSectionNg2e2.getFromFullCordinate();
        assertThat(fromFullCordinate).isEqualByComparingTo(CasePosition.G2);

        assertThat(PieceSingleMoveSection.getParsedActions("Nbd2")).
                isEqualTo(PieceDataFactory.from(PgnPieceFound.KNIGHT, 'b', null, CasePosition.D2));

        assertThat(PieceSingleMoveSection.getParsedActions("Qf6")).
                isEqualTo(PieceDataFactory.from(PgnPieceFound.QUEEN, null, null, CasePosition.F6));

        assertThat(PieceSingleMoveSection.getParsedActions("Rfe8")).
                isEqualTo(PieceDataFactory.from(PgnPieceFound.ROOK, 'f', null, CasePosition.E8));

        assertThat(PieceSingleMoveSection.getParsedActions("R5e8")).
                isEqualTo(PieceDataFactory.from(PgnPieceFound.ROOK, null, (byte) 5, CasePosition.E8));

        assertThat(PieceSingleMoveSection.getParsedActions("Nf6")).
                isEqualTo(PieceDataFactory.from(PgnPieceFound.KNIGHT, null, null, CasePosition.F6));

        assertThat(PieceSingleMoveSection.getParsedActions("e4")).
                isEqualTo(PieceDataFactory.from(PgnPieceFound.PAWN, null, null, CasePosition.E4));

        assertThat(PieceSingleMoveSection.getParsedActions("c6")).
                isEqualTo(PieceDataFactory.from(PgnPieceFound.PAWN, null, null, CasePosition.C6));
    }
}