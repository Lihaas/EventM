package com.skywalker.android.apps.eventm.Model;

public class SearchPojo {
    private   String Detail;
    private    String Location;
    private  int Prize;
    private  int Likes;
    private  String NoOfParti;
    private  int EntryFee;
    private   String pic;
    private String EventName;

    public SearchPojo() { }

    public SearchPojo(String detail, String location, int prize, int likes, String noOfParti, int entryFee, String pic, String eventName) {
        Detail = detail;
        Location = location;
        Prize = prize;
        Likes = likes;
        NoOfParti = noOfParti;
        EntryFee = entryFee;
        this.pic = pic;
        EventName = eventName;
    }

    public String getDetail() {
        return Detail;
    }

    public String getLocation() {
        return Location;
    }

    public int getPrize() {
        return Prize;
    }

    public int getLikes() {
        return Likes;
    }

    public String getNoOfParti() {
        return NoOfParti;
    }

    public int getEntryFee() {
        return EntryFee;
    }

    public String getPic() {
        return pic;
    }

    public String getEventName() {
        return EventName;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public void setPrize(int prize) {
        Prize = prize;
    }

    public void setLikes(int likes) {
        Likes = likes;
    }

    public void setNoOfParti(String noOfParti) {
        NoOfParti = noOfParti;
    }

    public void setEntryFee(int entryFee) {
        EntryFee = entryFee;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }
}
