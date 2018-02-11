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

package ca.watier.interfaces;

import ca.watier.echesscommon.interfaces.BaseUtils;
import org.junit.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

/**
 * Created by yannick on 7/5/2017.
 */
public class BaseUtilsTest {

    @Test
    public void getSafeInteger() {
        assertEquals(10, BaseUtils.getSafeInteger(10));
        assertEquals(20, BaseUtils.getSafeInteger(20));
        assertEquals(30, BaseUtils.getSafeInteger(30));
        assertEquals(40, BaseUtils.getSafeInteger(40));
        assertEquals(0, BaseUtils.getSafeInteger(null));
    }

    @Test
    public void getSafeBoolean() {
        assertTrue(BaseUtils.getSafeBoolean(true));
        assertFalse(BaseUtils.getSafeBoolean(false));
        assertFalse(BaseUtils.getSafeBoolean(null));
    }

    @Test
    public void getSafeList() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        assertThat(BaseUtils.getSafeList(list)).containsOnly(1, 2, 3);
        assertThat(BaseUtils.getSafeList(null)).isEmpty();
    }
}