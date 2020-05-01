package com.example.pointme.models;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

public class ServiceProvider implements Parcelable {

    private String email;
    private String name;
    private String tel;
    private String ig;
    private boolean image;
    private Image imageFile;
    private String uID;
    private String service;

    public ServiceProvider(){

    }

    public ServiceProvider(String email, String name, String tel, String ig, boolean image, Image imageFile, String uID, String service) {
        this.email = email;
        this.name = name;
        this.tel = tel;
        this.ig = ig;
        this.image = image;
        this.imageFile = imageFile;
        this.uID = uID;
        this.service = service;
    }

    protected ServiceProvider(Parcel in) {
        email = in.readString();
        name = in.readString();
        tel = in.readString();
        ig = in.readString();
        image = in.readByte() != 0;
        uID = in.readString();
        service = in.readString();
    }

    public static final Creator<ServiceProvider> CREATOR = new Creator<ServiceProvider>() {
        @Override
        public ServiceProvider createFromParcel(Parcel in) {
            return new ServiceProvider(in);
        }

        @Override
        public ServiceProvider[] newArray(int size) {
            return new ServiceProvider[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIg() {
        return ig;
    }

    public void setIg(String ig) {
        this.ig = ig;
    }

    public boolean isImage() {
        return image;
    }

    public void setImage(boolean image) {
        this.image = image;
    }

    public Image getImageFile() {
        return imageFile;
    }

    public void setImageFile(Image imageFile) {
        this.imageFile = imageFile;
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (obj instanceof ServiceProvider) {
            ServiceProvider serviceProvider = (ServiceProvider) obj;
            if ((serviceProvider.getuID() == null && uID == null)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(email);
        parcel.writeString(name);
        parcel.writeString(tel);
        parcel.writeString(ig);
        parcel.writeByte((byte) (image ? 1 : 0));
        parcel.writeString(uID);
        parcel.writeString(service);
    }
}
