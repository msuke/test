package com.example.demo.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.time.LocalTime;

//TODO validate あんまりかわらない　
@Data
public class TestForm {
    private MultipartFile file;
    private long min;
    private long sec;
    private long mSec;

    public LocalTime plusTo(LocalTime time) {
        time = time.plusMinutes(this.min);
        time = time.plusSeconds(this.sec);
        time = time.plus(Duration.ofMillis(this.mSec));
        return time;
    }
}
