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

package ca.watier.echechess.common.factories;

import ca.watier.echechess.common.enums.CasePosition;
import ca.watier.echechess.common.enums.PgnMoveToken;
import ca.watier.echechess.common.enums.PgnPieceFound;
import ca.watier.echechess.common.pojos.PieceDataSection;
import ca.watier.echechess.common.pojos.PieceSingleMoveSection;

public final class PieceDataFactory {

    private PieceDataFactory() {
    }

    public static PieceDataSection from(String before, String after, PgnMoveToken token) {
        return new PieceDataSection(before, after, token);
    }

    public static PieceSingleMoveSection from(PgnPieceFound pgnPieceFound, Character column, Byte row, CasePosition to) {
        return new PieceSingleMoveSection(pgnPieceFound, column, row, to);
    }
}
