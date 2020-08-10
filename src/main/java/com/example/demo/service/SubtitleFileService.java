package com.example.demo.service;


import com.example.demo.form.TestForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class SubtitleFileService {

    static final String SEPARATOR = " --> ";
    static final String CRLF = "\r\n";
    static final String DATE_FORMAT = "HH:mm:ss,SSS";

    //TODO method name
    public byte[] addTime(TestForm form) {
        ByteArrayOutputStream ret = new ByteArrayOutputStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(form.getFile().getInputStream(), StandardCharsets.UTF_8));){
            String str;
            while ((str = reader.readLine()) != null) {
                if (str.contains(SEPARATOR)) {
                    str = form.plusTo(getLocalTime(StringUtils.substringBefore(str, SEPARATOR)))
                            + SEPARATOR
                            + form.plusTo(getLocalTime(StringUtils.substringAfter(str, SEPARATOR)));
                }
                ret.write(str.getBytes());
                ret.write(CRLF.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret.toByteArray();
    }

    private LocalTime getLocalTime(String time) {
        return LocalTime.parse(time, DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
}
