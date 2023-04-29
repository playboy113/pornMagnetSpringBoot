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
        String[] pathList = new String[]{"D:/pornDocs","E:","K:"};

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
            for (File f:files){
                if(f.isDirectory()){
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
