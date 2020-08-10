package com.example.demo.controller;

import com.example.demo.service.SubtitleFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
public class TestController {
    @Autowired
    SubtitleFileService subtitleFileService;

    @RequestMapping(value = "/")
    public ModelAndView index(ModelAndView mav) {
        mav.setViewName("top");
        return mav;
    }
    @RequestMapping(value = "/execute", method = RequestMethod.POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> execute(@RequestParam("file") MultipartFile file,
                                  @RequestParam("min") int min,
                                  @RequestParam("sec") int sec,
                                  @RequestParam("mSec") int mSec) throws IOException {

        System.out.println(file.getName());

        System.out.println(file.getOriginalFilename());
        System.out.println(min);

        HttpHeaders header = new HttpHeaders();
        //header.add("Content-Type", "file"); //TODO
        header.add("Content-Disposition", "attachment; filename*=utf-8''" + URLEncoder.encode(file.getOriginalFilename(), "UTF-8")); // zzz:任意のファイル名
        header.add("Set-Cookie", "fileDownload=true; path=/aaa");
        return new ResponseEntity<>(subtitleFileService.addTime(file, min, sec, mSec), header, HttpStatus.OK);
    }
    private byte[] toByteArray(List<String> l) {
        byte[] ret = new byte[l.size()];
        for (int i = 0; i < l.size(); i++) {
            ret[i] = Byte.parseByte(l.get(i));
        }
        return ret;
    }
}
