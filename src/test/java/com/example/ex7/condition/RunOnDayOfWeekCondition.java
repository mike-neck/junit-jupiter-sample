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

import com.example.ex7.condition.filter.DayOfWeekTestFilter;
import com.example.ex7.condition.filter.RunOn;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static java.time.DayOfWeek.FRIDAY;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.DayOfWeek.THURSDAY;
import static java.time.DayOfWeek.TUESDAY;
import static java.time.DayOfWeek.WEDNESDAY;

@Slf4j
@DisplayName("曜日によって実行されるテストが異なる")
@ExtendWith({ DayOfWeekTestFilter.class })
public class RunOnDayOfWeekCondition {

    @Test
    @DisplayName("平日に実行される")
    @RunOn(
            zoneId = "Asia/Tokyo"
            , dayOfWeek = {
            MONDAY
            , TUESDAY
            , WEDNESDAY
            , THURSDAY
            , FRIDAY
    })
    void runOnWeekday() {
        log.info("runOnWeekday");
    }

    @Test
    @DisplayName("週末に実行される")
    @RunOn(
            zoneId = "Asia/Tokyo"
            , dayOfWeek = {
            SATURDAY
            , SUNDAY
    })
    void runOnWeekend() {
        log.info("runOnWeekend");
    }
}
