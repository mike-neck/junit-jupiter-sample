/*
 * Copyright 2016 Shinya Mochida
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
package com.example.guice.impl;

import com.example.guice.annotation.Date;
import com.example.guice.annotation.Time;
import com.example.guice.api.DateService;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateServiceImpl implements DateService {

    private final ZoneId zone;
    private final DateTimeFormatter dateFormat;
    private final DateTimeFormatter timeFormat;
    private final Locale locale;

    @Inject
    public DateServiceImpl(
            ZoneId zone
            , @Date DateTimeFormatter dateFormat
            , @Time DateTimeFormatter timeFormat
            , Locale locale
    ) {
        this.zone = zone;
        this.dateFormat = dateFormat.withLocale(locale);
        this.timeFormat = timeFormat.withLocale(locale);
        this.locale = locale;
    }

    @Override
    public Locale getLocale() {
        return locale;
    }

    @Override
    public String date(int year, int month, int day) {
        return LocalDate.of(year, month, day).format(dateFormat);
    }

    @Override
    public String today() {
        return LocalDate.now(zone).format(dateFormat);
    }

    @Override
    public String time(int hour, int minutes) {
        return LocalTime.of(hour, minutes).format(timeFormat);
    }

    @Override
    public String now() {
        return LocalDateTime.now(zone).format(timeFormat);
    }

    @Override
    public ZoneId getZone() {
        return zone;
    }
}
