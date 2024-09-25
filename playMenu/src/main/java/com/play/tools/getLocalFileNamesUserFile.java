package com.play.tools;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class getLocalFileNamesUserFile {

    public List<String> getFileNames() {



        ArrayList<String> allFileNames = new ArrayList<>();


        //SmbFile smbFile = new SmbFile("smb://192.168.1.103/E/12345/", auth);
        //String[] pathList = new String[]{"D:\\pornDocs"};
        String[] pathList = new String[]{"D:\\pornDocs","E:\\1021","D:\\4.2","G:\\0817_2","G:\\0817chun","G:\\0821-2chun","G:\\0821chun","F:\\0923","F:\\1001-2","D:\\1001","D:\\1021","D:\\佐山爱纯","E:\\12345","H:\\0130","H:\\0228","H:\\1001","H:\\1222"};

        for(int i=0;i< pathList.length;i++){
            try{
                File File = new File(pathList[i]);
                allFileNames.addAll(getFileName(File,new ArrayList<>()));
            }catch (Exception e){
                e.printStackTrace();
            }

        }



            return allFileNames;



    }
    public  List<String> getFileName(File file, List<String> fileName)  {

        try{
            File[] files = file.listFiles();
            assert files != null;
            for (File f:files){
                try{
                    if(f.isDirectory()){
                        getFileName(f,fileName);
                        System.out.println(fileName);
                    }else if(f.toString().contains(".mp4")){
                        fileName.add(f.getPath());
                    }
                }catch(NullPointerException e){
                    e.printStackTrace();
                }


            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return fileName;

    }
}
