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

import ca.watier.echesscommon.enums.PgnMoveToken;

import java.util.Arrays;
import java.util.List;

import static ca.watier.echesscommon.enums.PgnMoveToken.*;

public abstract class PieceData {
    protected static final List<PgnMoveToken> BASIC_MOVES = Arrays.asList(CAPTURE, PAWN_PROMOTION);
    protected static final List<PgnMoveToken> NON_MOVES_TOKENS = Arrays.asList( //The order of this list is important for the CASTLING, when removing the tokens
            CHECK,
            CHECKMATE,
            QUEENSIDE_CASTLING,
            KINGSIDE_CASTLING,
            QUEENSIDE_CASTLING_CHECK,
            KINGSIDE_CASTLING_CHECK,
            QUEENSIDE_CASTLING_CHECKMATE,
            KINGSIDE_CASTLING_CHECKMATE
    );

    protected PgnMoveToken token;

    public PieceData(PgnMoveToken token) {
        this.token = token;
    }

    public PieceData() {
    }

    public PgnMoveToken getToken() {
        return token;
    }

    public void setToken(PgnMoveToken token) {
        this.token = token;
    }
}
