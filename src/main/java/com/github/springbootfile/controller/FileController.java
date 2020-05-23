package com.github.springbootfile.controller;

import com.github.springbootfile.pojo.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

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


    @GetMapping("black")
    public void downloadBlack(HttpServletResponse response) throws IOException {
        InputStream inputStream = new FileInputStream(new File(path, name));
        OutputStream outputStream = response.getOutputStream();
        response.setContentType("application/x-download");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=test.txt");
        IOUtils.copy(inputStream, outputStream);
        outputStream.flush();
    }

    private static String name = "black.txt";

    @PostConstruct
    public void init() throws IOException {
        FileWriter writer = new FileWriter(path + name, true);
        for (int i = 0; i < 1000000; i++) {
            writer.write(getRandomIPV4());
            writer.write("\n");
        }
        writer.close();
    }

    public static String getRandomIPV4() {
        //ip范围
        int[][] range = {{607649792, 608174079},
                {1038614528, 1039007743},
                {1783627776, 1784676351},
                {2035023872, 2035154943},
                {2078801920, 2079064063},
                {-1950089216, -1948778497},
                {-1425539072, -1425014785},
                {-1236271104, -1235419137},
                {-770113536, -768606209},
                {-569376768, -564133889},
        };

        Random rdint = new Random();
        int index = rdint.nextInt(10);
        return num2ip(range[index][0] + new Random().nextInt(range[index][1] - range[index][0]));
    }

    private static String num2ip(int ip) {
        int[] b = new int[4];
        String x;

        b[0] = (ip >> 24) & 0xff;
        b[1] = (ip >> 16) & 0xff;
        b[2] = (ip >> 8) & 0xff;
        b[3] = ip & 0xff;
        x = b[0] + "." + b[1] + "." + b[2] + "." + b[3];

        return x;
    }
}
