package com.skywalker.android.apps.eventm.Model;

public class DetailPojo {

    String mName;
    String mPhoneNumber;
    String mProfesion;
    private String mImageUrl;
    private int eventId;
    private String mId;

    public DetailPojo(){ }

    public DetailPojo(String mName, String mPhoneNumber, String mProfesion, String mImageUrl, int eventId, String mId) {
        this.mName = mName;
        this.mPhoneNumber = mPhoneNumber;
        this.mProfesion = mProfesion;
        this.mImageUrl = mImageUrl;
        this.eventId = eventId;
        this.mId = mId;
    }

    public DetailPojo(String mName, String mPhoneNumber, String mProfesion, String mImageUrl) {
        this.mName = mName;
        this.mPhoneNumber = mPhoneNumber;
        this.mProfesion = mProfesion;
        this.mImageUrl = mImageUrl;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public void setmProfesion(String mProfesion) {
        this.mProfesion = mProfesion;
    }

    public String getmName() {
        return mName;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public String getmProfesion() {
        return mProfesion;
    }
}
