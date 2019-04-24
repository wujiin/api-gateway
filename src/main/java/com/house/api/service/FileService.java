package com.house.api.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.house.api.utils.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;

@Service
public class FileService {
  
  
  @Value("${file.path}")
  private String filePath;

  /**
   *@Description 文件上传
   **/
  public List<String> getImgPaths(List<MultipartFile> files) {
    List<String> paths = Lists.newArrayList();
    files.forEach(file -> {
      File localFile = null;
      try {
        if (!file.isEmpty()) {
          //上传
          localFile = FileUtil.saveToLocal(file, filePath);
          //获取文件绝对地址
          String path =  StringUtils.substringAfterLast(localFile.getAbsolutePath(), filePath);
          path = path.replace("\\","/");
          paths.add(path);
        }
      } catch (IOException e) {
        throw new RuntimeException(e.getMessage());
      }
    });
    return paths;
    
  }

}
