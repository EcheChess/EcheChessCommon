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

package ca.watier.echesscommon.utils;

import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.expiry.Expiry;

import java.util.concurrent.TimeUnit;

/**
 * Created by yannick on 6/11/2017.
 */
public class CacheConstants {

    public static final String CACHE_UI_SESSION_NAME = "uiCache";
    public static final Expiry<Object, Object> CACHE_UI_SESSION_EXPIRY = Expirations.timeToIdleExpiration(new Duration(5, TimeUnit.MINUTES));

    private CacheConstants() {
    }
}
