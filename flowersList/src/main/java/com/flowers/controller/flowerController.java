package com.flowers.controller;

import com.flowers.entity.flower;
import com.flowers.service.flowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flower")
public class flowerController {
    @Autowired
    private flowService flowService;

    @RequestMapping("/selectAll.do")
    @ResponseBody
    public Map<String,Object> selectAll(){
        Map<String, Object> retMap = new HashMap<>();
        List<flower> flowers = flowService.selectAll();
        retMap.put("flowers",flowers);
        return retMap;
    }
    @RequestMapping("/insertNew.do")
    @ResponseBody
    public void insertNew(String name,String age,String tall,String breast,String price,String service,String type,String city,String locate,
                          String introduce,String report,String weight,String updateTime,String wechat,String phone,String qq){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name",name);
        map.put("age",age);
        map.put("tall",tall);
        map.put("breast",breast);
        map.put("price",price);
        map.put("service",service);
        map.put("type",type);
        map.put("city",city);
        map.put("locate",locate);
        map.put("introduce",introduce);
        map.put("report",report);
        map.put("weight",weight);
        map.put("updateTime",updateTime);
        map.put("wechat",wechat);
        map.put("phone",phone);
        map.put("qq",qq);
        flowService.insertNew(map);

    }
    @RequestMapping("/upload.do")
    @ResponseBody
    public String uploadFiles(MultipartFile[] file, String name, HttpServletRequest request) throws IOException {



        String parentFolderPath = "D:\\pornDocs\\telegramPic"; // 指定父文件夹的路径

        String newSubfolderName = name; // 子文件夹的名称

        // 创建一个File对象，表示新子文件夹的路径
        File newSubfolder = new File(parentFolderPath, newSubfolderName);

        // 使用mkdir()方法创建子文件夹
        if (newSubfolder.mkdir()) {
            System.out.println("子文件夹创建成功！");
        } else {
            System.out.println("子文件夹创建失败，可能已存在或父文件夹不存在。");
        }
        System.out.println(newSubfolder.getAbsolutePath());
        for (int i=0;i<file.length;i++){
            //源文件和目标文件夹
            String sourceFilePath = parentFolderPath+"\\"+file[i].getOriginalFilename();
            String destinationFolderPath = newSubfolder.getAbsolutePath();
            //形成路径
            Path sourcePath = Paths.get(sourceFilePath);
            Path destinationPath = Paths.get(destinationFolderPath, i+".jpg");
            //开始复制
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

            //删除本地文件
            Files.delete(sourcePath);
        }

        return "success";
    }


}