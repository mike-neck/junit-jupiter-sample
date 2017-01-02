/*
 * Copyright 2017 Shinya Mochida
 * 
 * Licensed under the Apache License,Version2.0(the"License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,software
 * Distributed under the License is distributed on an"AS IS"BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.ex7.condition;

import com.example.ex7.condition.filter.DayOfWeekFilter;
import com.example.ex7.condition.filter.RunOn;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.DayOfWeek;

@Slf4j
@ExtendWith({ DayOfWeekFilter.class })
@RunOn(dayOfWeek = DayOfWeek.MONDAY, zoneId = "Asia/Tokyo")
@DisplayName("月曜のみ起動される")
@Tag("filter")
public class RunOnMonday {

    @Test
    void test() {
        log.info("RunOnMonday");
    }
}
