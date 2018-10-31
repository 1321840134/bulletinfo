package bulletinfo.com.bulletinfo.dao;

import bulletinfo.com.bulletinfo.bean.User_Message;
import bulletinfo.com.bulletinfo.greendao.GreenDaoManager;

public class User_MessageDao {
    private final GreenDaoManager daoManager;
    private static User_MessageDao U_dao;
    public User_MessageDao(){
        this.daoManager = GreenDaoManager.getInstance();
    }
    public static User_MessageDao getInstance(){
        if (U_dao == null){
            U_dao = new User_MessageDao();
        }
        return  U_dao;
    }

    /**
     * 获取Dao对象
     * @return
     */
    public bulletinfo.com.bulletinfo.greendao.User_MessageDao getUserMessageDao(){
        return daoManager.getDaoSession().getUser_MessageDao();
    }

    /**
     * 插入单条信息
     * @return
     */
    public boolean inseartMsg(User_Message message){
        boolean flag = false;
        flag = getUserMessageDao().insert(message) == -1 ? false : true;
        return flag;
    }


}
