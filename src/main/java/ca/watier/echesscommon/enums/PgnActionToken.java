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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public enum PgnActionToken {
    CAPTURE('x'), PAWN_PROMOTION('='), UNKNOWN('\0');

    private final char value;

    PgnActionToken(char c) {
        this.value = c;
    }

    public static List<PgnActionToken> getActionsFromAction(@NotNull String action) {
        List<PgnActionToken> values = new ArrayList<>();

        for (byte b : action.getBytes()) {
            for (PgnActionToken currentToken : values()) {
                if (currentToken.getValue() == b) {
                    values.add(currentToken);
                }
            }
        }

        return values;
    }

    public char getValue() {
        return value;
    }
}
