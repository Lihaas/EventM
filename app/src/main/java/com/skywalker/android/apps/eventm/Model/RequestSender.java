package com.skywalker.android.apps.eventm.Model;

public class RequestSender {

 private String mEventName;
    private String mImgName;
    private String mTeamName;
    private String mSendBy;
    String mId;

    public RequestSender() {
    }

    public RequestSender(String mEventName, String mImgName, String mTeamName, String mSendBy, String mId) {
        this.mEventName = mEventName;
        this.mImgName = mImgName;
        this.mTeamName = mTeamName;
        this.mSendBy = mSendBy;
        this.mId = mId;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmSendBy() {
        return mSendBy;
    }

    public void setmSendBy(String mSendBy) {
        this.mSendBy = mSendBy;
    }

    public String getmEventName() {
        return mEventName;
    }

    public void setmEventName(String mEventName) {
        this.mEventName = mEventName;
    }

    public String getmImgName() {
        return mImgName;
    }

    public void setmImgName(String mImgName) {
        this.mImgName = mImgName;
    }

    public String getmTeamName() {
        return mTeamName;
    }

    public void setmTeamName(String mTeamName) {
        this.mTeamName = mTeamName;
    }
}
