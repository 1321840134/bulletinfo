package bulletinfo.com.bulletinfo.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User_Message {
    @Id(autoincrement = true)
    private Long id;
    private String get_user;
    private String put_user;
    private String message;
    private String time;
    @Generated(hash = 1890766180)
    public User_Message(Long id, String get_user, String put_user, String message,
            String time) {
        this.id = id;
        this.get_user = get_user;
        this.put_user = put_user;
        this.message = message;
        this.time = time;
    }
    @Generated(hash = 2138733406)
    public User_Message() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getGet_user() {
        return this.get_user;
    }
    public void setGet_user(String get_user) {
        this.get_user = get_user;
    }
    public String getPut_user() {
        return this.put_user;
    }
    public void setPut_user(String put_user) {
        this.put_user = put_user;
    }
    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getTime() {
        return this.time;
    }
    public void setTime(String time) {
        this.time = time;
    }

}
