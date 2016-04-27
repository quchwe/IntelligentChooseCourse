package com.choosecourse.intelligentchoosecourse.db;

/**
 * Created by quchwe on 2016/4/27 0027.
 */
public class Diary {
    private int sno;
    private String title;
    private String message;
    private String time;

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
