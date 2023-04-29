package com.play;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class testMain {

    private static List<File> fileList = new ArrayList<>();
    public static void main(String[] args) throws MalformedURLException, SmbException {
        String path = "K:";
        File file = new File(path);
        loadFileList(file);
        //System.out.println(fileList);


    }

    public static void loadFileList(File file){
        // 判断这个路径是文件还是文件夹
        if(file.isDirectory()){
            File[] files = file.listFiles();
            System.out.println(Arrays.toString(files));
            if(files == null || files.length == 0 ){
                return;
            }
            for(File file1 : files){
                if(file1.isDirectory()){
                    loadFileList(file1);
                }else if(file1.toString().contains(".mp4")){
                    fileList.add(file1);
                }
            }
        }
    }

    public static List<String> getFileNames(String path) throws SmbException {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        List<String> fileNames = new ArrayList<>();
        return getFileName(file, fileNames);
    }



    public static  List<String> getFileName(File file, List<String> fileName) throws SmbException {

        try{
            File[] files = file.listFiles();
            for (File f:files){
                if(f.isDirectory()){
                    System.out.println(f.getPath());
                    getFileName(f,fileName);
                }else if(f.toString().contains(".mp4")){
                    fileName.add(f.getPath());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return fileName;
    }
}
