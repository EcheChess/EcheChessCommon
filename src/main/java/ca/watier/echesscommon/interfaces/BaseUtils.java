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

package ca.watier.echesscommon.interfaces;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yannick on 4/27/2017.
 */
public interface BaseUtils {
    static int getSafeInteger(Integer value) {
        return value == null ? 0 : value;
    }

    static boolean getSafeBoolean(Boolean value) {
        return value != null && value;
    }

    static <T> List<T> getSafeList(List<T> list) {
        return list != null ? list : new ArrayList<>();
    }
}