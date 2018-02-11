/*
 *    Copyright 2014 - 2017 Yannick Watier
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

package ca.watier.echesscommon.impl;

import ca.watier.echesscommon.enums.ChessEventMessage;
import ca.watier.echesscommon.enums.Side;
import ca.watier.echesscommon.interfaces.WebSocketService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yannick on 6/10/2017.
 */
public class WebSocketServiceTestImpl implements WebSocketService {

    private List<Object> messages = new ArrayList<>();

    public void clearMessages() {
        messages.clear();
    }

    @Override
    public void fireSideEvent(String uuid, Side side, ChessEventMessage evtMessage, String message) {
        messages.add(message);
    }

    @Override
    public void fireSideEvent(String uuid, Side side, ChessEventMessage evtMessage, String message, Object obj) {
        messages.add(message);
    }

    @Override
    public void fireUiEvent(String uiUuid, ChessEventMessage evtMessage, String message) {
        messages.add(message);
    }

    @Override
    public void fireGameEvent(String uuid, ChessEventMessage evtMessage, Object message) {
        messages.add(message);
    }

    @Override
    public void fireGameEvent(String uuid, ChessEventMessage refreshBoard) {
        messages.add(refreshBoard);
    }

    public List<Object> getMessages() {
        return messages;
    }
}
