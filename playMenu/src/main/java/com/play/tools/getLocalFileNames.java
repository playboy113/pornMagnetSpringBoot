package com.play.tools;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class getLocalFileNames {
    public List<String> getFileNames() throws MalformedURLException, SmbException {
        String ip = "192.168.1.103";
        String user = "Administrator";
        String pass = "960902";
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(ip, user, pass);

        jcifs.Config.setProperty("jcifs.smb.client.responseTimeout", "1200000");
        jcifs.Config.setProperty("jcifs.smb.client.soTimeout", "1200000");

        String[] pornFiles = new String[]{"smb://192.168.1.103/f/","smb://192.168.1.103/D/12345/","smb://192.168.1.103/E/12345/"};


        ArrayList<String> allFileNames = new ArrayList<>();


        //SmbFile smbFile = new SmbFile("smb://192.168.1.103/E/12345/", auth);
        String[] pathList = new String[]{"smb://192.168.1.103/f/","smb://192.168.1.103/D/12345/","smb://192.168.1.103/E/12345/"};

        for (int i=0;i<pathList.length;i++){
            SmbFile smbFile = new SmbFile(pathList[i], auth);
            allFileNames.addAll(getFileName(smbFile,new ArrayList<>()));
        }
        return allFileNames;



    }
    public  List<String> getFileName(SmbFile file, List<String> fileName) throws SmbException {

        try{
            SmbFile[] files = file.listFiles();
            for (SmbFile f:files){
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
