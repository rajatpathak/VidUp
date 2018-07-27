package com.appentus.vidup.apis;

public class VideoFile {

    public String FileExt;
    public String AppKey;
    public String Status;

    public byte[] File;

    public VideoFile(String fileExt, String appKey, byte[] file,String Status) {
        FileExt = fileExt;
        AppKey = appKey;
        File = file;
        Status= Status;
    }

    public String getFileExt() {
        return FileExt;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = Status;
    }

    public void setFileExt(String fileExt) {
        FileExt = fileExt;
    }

    public String getAppKey() {
        return AppKey;
    }

    public void setAppKey(String appKey) {
        AppKey = appKey;
    }

    public byte[] getFile() {
        return File;
    }

    public void setFile(byte[] file) {
        File = file;
    }
}
