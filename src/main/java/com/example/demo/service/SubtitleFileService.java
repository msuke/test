package com.example.demo.service;


import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SubtitleFileService {
    static final String SEPARATOR = " --> ";
    public byte[] addTime(MultipartFile file, long min, long sec, long mSec) throws IOException {
        File f = new File(Objects.requireNonNull(file.getOriginalFilename()));
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8))){
            String str;
            while ((str = reader.readLine()) != null) {
                if (str.contains(SEPARATOR)) {
                    LocalTime time1 = getLocalTime(StringUtils.substringBefore(str, SEPARATOR));
                    LocalTime time2 = getLocalTime(StringUtils.substringAfter(str, SEPARATOR));
                    str = plus(time1, min, sec, mSec) + SEPARATOR + plus(time2, min, sec, mSec);
                }
                writer.write(str);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertFile(f);
    }
    private byte[] convertFile(File file) throws IOException {
        return Files.readAllBytes(file.toPath());
    }
    private LocalTime plus(LocalTime time, long min, long sec, long mSec) {
        time = time.plusMinutes(min);
        time = time.plusSeconds(sec);
        time = time.plus(Duration.ofMillis(mSec));
        return time;
    }

    private LocalTime getLocalTime(String time) {
        return LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm:ss,SSS"));
    }
}
