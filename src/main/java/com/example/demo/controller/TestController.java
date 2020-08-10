package com.example.demo.controller;

import com.example.demo.form.TestForm;
import com.example.demo.service.SubtitleFileService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class TestController {
    @NonNull
    SubtitleFileService subtitleFileService;

    @RequestMapping(value = "/")
    public ModelAndView index(ModelAndView mav) {
        mav.setViewName("top");
        return mav;
    }

    @RequestMapping(value = "/execute", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> execute(@ModelAttribute("testForm") TestForm form) throws IOException {
//TODO form -> dto or something
        HttpHeaders header = new HttpHeaders();
        //header.add("Content-Type", "file"); //TODO study about Content-Type
        header.add("Content-Disposition", "attachment; filename*=utf-8''" + URLEncoder.encode(Objects.requireNonNull(form.getFile().getOriginalFilename()), "UTF-8")); // zzz:任意のファイル名
        header.add("Set-Cookie", "fileDownload=true; path=/");
        return new ResponseEntity<>(subtitleFileService.addTime(form), header, HttpStatus.OK);
    }
}
