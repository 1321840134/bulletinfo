package bulletinfo.com.bulletinfo.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by foxcold on 2018/10/9.
 */
@Entity
public class Friends {
    @Id(autoincrement = true)
    private long id;     //好友对应主键
    private int fid; //好友id
    private String time; //建立时间
    private int uid;   //用户id
    @Generated(hash = 924857691)
    public Friends(long id, int fid, String time, int uid) {
        this.id = id;
        this.fid = fid;
        this.time = time;
        this.uid = uid;
    }
    @Generated(hash = 823074882)
    public Friends() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public int getFid() {
        return this.fid;
    }
    public void setFid(int fid) {
        this.fid = fid;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public int getUid() {
        return this.uid;
    }
    public void setUid(int uid) {
        this.uid = uid;
    }

}
