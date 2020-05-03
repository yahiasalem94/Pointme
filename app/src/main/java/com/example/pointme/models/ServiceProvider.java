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
    private int avgRating;
    private int attitueRating;
    private int puncRating;
    private int qualityRating;
    private int numReviews;

    public ServiceProvider(){

    }


    public ServiceProvider(String email, String name, String tel, String ig, boolean image, Image imageFile, String uID, String service,
                           int avgRating, int attitueRating, int puncRating, int qualityRating, int numReviews) {
        this.email = email;
        this.name = name;
        this.tel = tel;
        this.ig = ig;
        this.image = image;
        this.imageFile = imageFile;
        this.uID = uID;
        this.service = service;
        this.avgRating = avgRating;
        this.attitueRating = attitueRating;
        this.puncRating = puncRating;
        this.qualityRating = qualityRating;
        this.numReviews = numReviews;
    }

    protected ServiceProvider(Parcel in) {
        email = in.readString();
        name = in.readString();
        tel = in.readString();
        ig = in.readString();
        image = in.readByte() != 0;
        uID = in.readString();
        service = in.readString();
        avgRating = in.readInt();
        attitueRating = in.readInt();
        puncRating = in.readInt();
        qualityRating = in.readInt();
        numReviews = in.readInt();
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

    public int getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(int avgRating) {
        this.avgRating = avgRating;
    }

    public int getAttitueRating() {
        return attitueRating;
    }

    public void setAttitueRating(int attitueRating) {
        this.attitueRating = attitueRating;
    }

    public int getPuncRating() {
        return puncRating;
    }

    public void setPuncRating(int puncRating) {
        this.puncRating = puncRating;
    }

    public int getQualityRating() {
        return qualityRating;
    }

    public void setQualityRating(int qualityRating) {
        this.qualityRating = qualityRating;
    }

    public int getNumReviews() {
        return numReviews;
    }

    public void setNumReviews(int numReviews) {
        this.numReviews = numReviews;
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
        parcel.writeInt(avgRating);
        parcel.writeInt(attitueRating);
        parcel.writeInt(puncRating);
        parcel.writeInt(qualityRating);
        parcel.writeInt(numReviews);
    }
}
