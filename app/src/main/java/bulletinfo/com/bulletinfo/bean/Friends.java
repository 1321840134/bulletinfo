package bulletinfo.com.bulletinfo.bean;

/**
 * Created by foxcold on 2018/10/9.
 */

public class Friends {
    private int id;     //好友对应主键
    private int fid; //好友id
    private String time; //建立时间
    private int uid;   //用户id
    private String user_name; //用户名

    public Friends(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Friends(int fid, String time, int uid, String user_name) {
        this.fid = fid;
        this.time=time;
        this.uid = uid;
        this.user_name = user_name;
    }
}
