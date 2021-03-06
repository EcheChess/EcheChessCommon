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

package ca.watier.echechess.common.responses;

/**
 * Created by yannick on 4/23/2017.
 */
public class DualValueResponse {
    private Object value1;
    private Object value2;
    private String message;

    public DualValueResponse(Object value1, Object value2, String message) {
        this.value1 = value1;
        this.value2 = value2;
        this.message = message;
    }

    public Object getValue1() {
        return value1;
    }

    public Object getValue2() {
        return value2;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
