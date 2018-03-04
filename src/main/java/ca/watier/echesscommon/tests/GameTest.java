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

package ca.watier.echesscommon.tests;

import ca.watier.echesscommon.enums.Side;
import ca.watier.echesscommon.impl.WebSocketServiceTestImpl;
import ca.watier.echesscommon.interfaces.WebSocketService;
import ca.watier.echesscommon.responses.GameScoreResponse;

/**
 * Created by yannick on 5/30/2017.
 */

/**
 * This class is only intended to be used inside the unit tests.
 */
public abstract class GameTest {
    protected static final Side WHITE = Side.WHITE;
    protected static final Side BLACK = Side.BLACK;
    protected static final WebSocketService WEB_SOCKET_SERVICE = new WebSocketServiceTestImpl();
    protected static final GameScoreResponse EMPTY_GAME_SCORE_RESPONSE = new GameScoreResponse((short) 0, (short) 0);
}
