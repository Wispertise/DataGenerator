package com.cgl.controller;

import com.cgl.model.ResponseResult;
import com.cgl.service.SqlGenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @description:
 * @author: SunnyDeer
 * @time: 2023/4/25
 */
@RestController
@RequestMapping("/download")
public class FileDownLoadController {
    @Autowired
    private SqlGenService sqlGenService;
    @Value("${info.Excelpath}")
    String path;
    @PostMapping("/{type}")
    public ResponseResult downloadLocal(HttpServletRequest request, HttpServletResponse response, @PathVariable String type) throws IOException {
        if (!type.equals("txt") && !type.equals("csv") && !type.equals("xlsx")){
            return ResponseResult.errorResult(409,"不支持的文件类型");
        }
        String userId = sqlGenService.getIdByToken(request);
        String fileName = userId + "." + type; //需要下载的文件名
        String filePath = path+userId+"\\" +fileName; //文件路径
        File file = new File(filePath);
        System.out.println(filePath);
        if (file.exists()) { //如果文件存在
            try {
                InputStream inputStream = new FileInputStream(file);
                //设置响应头
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                OutputStream outputStream = response.getOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseResult.okResult();
    }

}
