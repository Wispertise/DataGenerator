package com.cgl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: SunnyDeer
 * @time: 2023/4/25
 */

@Component
@ConfigurationProperties(prefix = "info")
public class MyFileOutPutSetting {

    public static String Excelpath;

    public  String getExcelpath() {
        return Excelpath;
    }

    public  void setExcelpath(String excelpath) {
        Excelpath = excelpath;
    }

}
