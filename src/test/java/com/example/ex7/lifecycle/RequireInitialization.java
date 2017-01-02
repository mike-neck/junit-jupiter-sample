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
package com.example.ex7.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
@DisplayName("初期化が必要")
@ExtendWith({PostProcessor.class})
public class RequireInitialization {

    private LocalDate today;

    private DateTimeFormatter formatter;

    @PostConstruct
    void init() {
        this.today = LocalDate.now(ZoneId.of("Asia/Tokyo"));
        this.formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd");
    }

    @Test
    void dynamicTestStream() {
        log.info("If required instances are not initialized, an exception will be thrown. {}", today.format(formatter));
    }
}
