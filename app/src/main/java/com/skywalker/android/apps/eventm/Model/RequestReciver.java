package com.skywalker.android.apps.eventm.Model;

public class RequestReciver {

    private String mEventName;
    private String mImgName;
    private String mTeamName;
    private String mSendBy;
    private String mId;

    public RequestReciver() {
    }

    public RequestReciver(String mEventName, String mImgName, String mTeamName, String mSendBy, String mId) {
        this.mEventName = mEventName;
        this.mImgName = mImgName;
        this.mTeamName = mTeamName;
        this.mSendBy = mSendBy;
        this.mId = mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmId() {
        return mId;
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

    public String getmSendBy() {
        return mSendBy;
    }

    public void setmSendBy(String mSendBy) {
        this.mSendBy = mSendBy;
    }
}
