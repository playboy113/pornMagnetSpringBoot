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
    @RequestMapping("/queryInfo.do")
    @ResponseBody
    public Map<String,Object> queryInfo(Integer id,String name,String age,String tall,String weight,String breast,String service,String city,String locate,String price){
        HashMap<String, Object> map = new HashMap<>();

        map.put("id",id);
        map.put("name",name);
        map.put("age",age);
        map.put("tall",tall);
        map.put("weight",weight);
        map.put("breast",breast);
        map.put("service",service);
        map.put("city",city);
        map.put("locate",locate);
        map.put("price",price);

        List<flower> flowersList = flowService.queryInfo(map);

        HashMap<String, Object> retMap = new HashMap<>();
        retMap.put("flowersList",flowersList);
        return retMap;


    }
    @RequestMapping("/copyInfo.do")
    @ResponseBody
    public HashMap<Object, Object> copyInfo(String num){

        Integer id = Integer.valueOf(num);
        HashMap<Object, Object> retMap = new HashMap<>();
        flower selectedFlower = flowService.selectById(id);
        String info  = "编号："+selectedFlower.getId()+"      姓名："+selectedFlower.getName()+"                       年龄："+selectedFlower.getAge()+"\n\n身高："+selectedFlower.getTall()+
                "              体重:"+selectedFlower.getWeight()+"            胸："+selectedFlower.getBreast()+"\n\n城市：#"+selectedFlower.getCity()+"             地区:#"+selectedFlower.getLocate()+
                "\n\n价格："+selectedFlower.getPrice()+"                 服务："+selectedFlower.getService()+"\n\n介绍："+selectedFlower.getIntroduce()+"\n\n探访报告:"+selectedFlower.getReport();

        StringBuilder info2 = new StringBuilder();
        info2.append("编号：").append(selectedFlower.getId());
        info2.append("      姓名："+selectedFlower.getName());
        info2.append("                       年龄："+selectedFlower.getAge());
        info2.append("\n\n身高："+selectedFlower.getTall());
        info2.append("              体重:"+selectedFlower.getWeight());
        info2.append("            胸："+selectedFlower.getBreast());
        info2.append("\n\n城市：#"+selectedFlower.getCity());
        info2.append("             地区:#"+selectedFlower.getLocate());
        info2.append("\n\n价格："+selectedFlower.getPrice());
        if(selectedFlower.getService()!=null){
            info2.append("                 服务："+selectedFlower.getService());
        }
        if(!selectedFlower.getIntroduce().equals("")){
            info2.append("\n\n介绍："+selectedFlower.getIntroduce());
        }
        if(!selectedFlower.getReport().equals("")){
            info2.append("\n\n探访报告:"+selectedFlower.getReport());
        }
        

        retMap.put("info",info2);
        return retMap;
    }


}
