package com.house.api.utils;

import java.io.File;
import java.io.IOException;
import java.time.Instant;

import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.Files;

/**
 * @Description 文件上传工具类
 * @Author wujin
 **/
public class FileUtil {

    /**
     * @Description 保存文件到本地
     **/
    public static File saveToLocal(MultipartFile file, String filePath) throws IOException {
        File newfile = new File(filePath + "/" + Instant.now().getEpochSecond() + "/" + file.getOriginalFilename());
        if (!newfile.exists()) {
            newfile.getParentFile().mkdirs();
            newfile.createNewFile();
        }
        Files.write(file.getBytes(), newfile);
        return newfile;
    }
}
