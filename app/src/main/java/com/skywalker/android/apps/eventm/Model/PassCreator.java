package com.skywalker.android.apps.eventm.Model;

public class PassCreator {

    private String mEvent;
    private String mTeamName;
    private Boolean mValid;
    private String mId;
    private String mImageUrl;

    public PassCreator() {
    }

    public PassCreator(String mEvent, String mTeamName, Boolean mValid, String mId, String mImageUrl) {
        this.mEvent = mEvent;
        this.mTeamName = mTeamName;
        this.mValid = mValid;
        this.mId = mId;
        this.mImageUrl = mImageUrl;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmEvent() {
        return mEvent;
    }

    public void setmEvent(String mEvent) {
        this.mEvent = mEvent;
    }

    public String getmTeamName() {
        return mTeamName;
    }

    public void setmTeamName(String mTeamName) {
        this.mTeamName = mTeamName;
    }

    public Boolean getmValid() {
        return mValid;
    }

    public void setmValid(Boolean mValid) {
        this.mValid = mValid;
    }

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }
}
