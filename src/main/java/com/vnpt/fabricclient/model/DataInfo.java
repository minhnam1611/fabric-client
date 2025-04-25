package com.vnpt.fabricclient.model;

public class DataInfo {
    private String assetID;

    private String owner;

    private String typeFile;
    private String base64File;

    public DataInfo() {
    }

    public DataInfo(String assetID, String owner, String typeFile, String base64File) {
        this.assetID = assetID;
        this.owner = owner;
        this.typeFile = typeFile;
        this.base64File = base64File;
    }

    public String getAssetID() {
        return assetID;
    }

    public void setAssetID(String assetID) {
        this.assetID = assetID;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTypeFile() {
        return typeFile;
    }

    public void setTypeFile(String typeFile) {
        this.typeFile = typeFile;
    }

    public String getBase64File() {
        return base64File;
    }

    public void setBase64File(String base64File) {
        this.base64File = base64File;
    }
}
