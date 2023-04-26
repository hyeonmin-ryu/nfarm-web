package kr.re.amc.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IntroPage {

    private final Logger log = LoggerFactory.getLogger(getClass());

    // 연구분석포탈소개 - 사용자가이드
    @GetMapping(value = "/intro/guide")
    public String introGuide() {

        return "pages/intro/guide";
    }

    // IoT Core
    @GetMapping(value = "/data/iotcore")
    public String dataIotcore() {

        return "pages/intro/iotCore";
    }
    
    // easy Track
    @GetMapping(value = "/data/easytrack")
    public String dataEasytrack() {

        return "pages/intro/easyTrack";
    }
    
    // easy Track
    @GetMapping(value = "/data/upload")
    public String dataUpload() {

        return "pages/intro/dataUpload";
    }
}
