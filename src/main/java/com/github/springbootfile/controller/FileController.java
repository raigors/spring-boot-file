package com.github.springbootfile.controller;

import com.github.springbootfile.pojo.FileInfo;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>
 * 创建时间为 15:32 2019-05-19
 * 项目名称 spring-boot-file
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */

@RestController
public class FileController {

    private static String path = "/Users/shao/Documents/IdeaProjects/spring-boot-file/src/main/resources/file/";

    @GetMapping("/file/{id}")
    public void download(@PathVariable String id, HttpServletResponse response) throws IOException {
        InputStream inputStream = new FileInputStream(new File(path, id + ".txt"));
        OutputStream outputStream = response.getOutputStream();
        response.setContentType("application/x-download");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=test.txt");
        IOUtils.copy(inputStream, outputStream);
        outputStream.flush();
    }


    @PostMapping("/file")
    public FileInfo upload(MultipartFile file) throws IOException {
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        File localFile = new File(path, System.currentTimeMillis() + ".txt");
        // 把上传的文件写到本地文件
        file.transferTo(localFile);
        return new FileInfo(path);
    }


}
