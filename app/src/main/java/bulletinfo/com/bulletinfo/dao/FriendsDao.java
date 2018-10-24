package bulletinfo.com.bulletinfo.dao;

import org.greenrobot.greendao.query.Query;

import java.util.List;

import bulletinfo.com.bulletinfo.bean.Friends;
import bulletinfo.com.bulletinfo.greendao.GreenDaoManager;

public class FriendsDao {
    private final GreenDaoManager daoManager;
    private static FriendsDao Fdao;

    public FriendsDao() {
        this.daoManager = GreenDaoManager.getInstance();
    }

    public static FriendsDao getInstance(){
        if (Fdao == null){
            Fdao = new FriendsDao();
        }
        return Fdao;
    }

    /**
     *  插入数据，若未建表则建表
     * @param friends
     * @return
     */
    public boolean insert(Friends friends){
       boolean flag = false;
       flag  =getFriendsDao().insert(friends) == -1 ? false : true;
       return flag;
    }

    /**
     * 插入或替换数据
     * @param friends
     * @return
     */
    public boolean insertOrReplace(Friends friends){
        boolean flag = false;
        try{
            flag = getFriendsDao().insertOrReplace(friends) == -1 ? false : true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  flag;
    }

    /**
     * 插入多条数据，子线程完成
     * @param list
     * @return
     */
    public boolean insertOrReplaceMulti(final List<Friends> list){
        boolean flag = false;
        try {
            getFriendsDao().getSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (Friends friends : list){
                        getFriendsDao().insertOrReplace(friends);
                    }
                }
            });
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  flag;
    }

    /**
     * 更新数据
     * @param friends
     * @return
     */
    public boolean update(Friends friends){
        boolean flag = false;
        try {
            getFriendsDao().update(friends);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  flag;
    }

    /**
     * 根据id删除数据
     *
     * @param friends
     * @return
     */
    public boolean deleteById(Friends friends) {
        boolean flag = false;
        try {
            getFriendsDao().delete(friends);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除所有数据
     *
     * @return
     */
    public boolean deleteAll() {
        boolean flag = false;
        try {
            getFriendsDao().deleteAll();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 根据主键查询单条数据
     * @param id
     * @return
     */
    public Friends queryById(Long id){
        return getFriendsDao().load(id);
    }

    /**
     * 查询所有数据
     * @return
     */
    public List<Friends> queryAll(){
        return getFriendsDao().loadAll();
    }

    /**
     *  根据uid查询所有好友
     * @param uid
     * @return
     */
    public List<Friends> queryByName(int uid){
        Query<Friends> build = null;
        try{
            build = getFriendsDao().queryBuilder()
                    .where(bulletinfo.com.bulletinfo.greendao.FriendsDao.Properties.Uid.eq(uid))
                    .build();
        }catch (Exception e){
            e.printStackTrace();
        }
        return build.list();
    }

    /**
     * 根据参数查询
     * @param where
     * @param param
     * @return
     */
    public List<Friends> queryByParam(String where , String param){
        return getFriendsDao().queryRaw(where, param);
    }
    public bulletinfo.com.bulletinfo.greendao.FriendsDao getFriendsDao(){
        return daoManager.getDaoSession().getFriendsDao();
    }

}
