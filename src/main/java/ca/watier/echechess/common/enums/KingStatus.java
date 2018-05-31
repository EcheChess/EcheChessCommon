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

package ca.watier.echechess.common.enums;

import ca.watier.echechess.common.utils.Assert;

/**
 * Created by yannick on 5/14/2017.
 */
public enum KingStatus {
    CHECK, CHECKMATE, OK, STALEMATE;

    public static boolean isCheckOrCheckMate(KingStatus kingStatus) {
        Assert.assertNotNull(kingStatus);

        return KingStatus.CHECK.equals(kingStatus) || KingStatus.CHECKMATE.equals(kingStatus);
    }
}
