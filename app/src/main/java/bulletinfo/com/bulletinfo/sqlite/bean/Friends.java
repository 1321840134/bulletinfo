package bulletinfo.com.bulletinfo.sqlite.bean;

import bulletinfo.com.bulletinfo.sqlite.annotion.DbFiled;
import bulletinfo.com.bulletinfo.sqlite.annotion.DbTable;

/**
 *
 */
@DbTable("friend")
public class Friends {
    @DbFiled("id")
    public int id;     //好友对应主键
    @DbFiled("fid")
    public int fid; //好友id
    @DbFiled("time")
    public String time; //建立时间
    @DbFiled("uid")
    public int uid;   //用户id
    @DbFiled("user_name")
    public String user_name; //用户名

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
}
