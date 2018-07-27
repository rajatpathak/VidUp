package com.appentus.vidup.apis;

class VideoFileBase {

    public String FileExt;
    public String AppKey;
    public String File;

    public VideoFileBase(String fileExt, String appKey, String file) {
        FileExt = fileExt;
        AppKey = appKey;
        File = file;
    }

    public String getFileExt() {
        return FileExt;
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

    public String getFile() {
        return File;
    }

    public void setFile(String file) {
        File = file;
    }
}
