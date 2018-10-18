package bulletinfo.com.bulletinfo.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import bulletinfo.com.bulletinfo.sqlite.annotion.DbFiled;
import bulletinfo.com.bulletinfo.sqlite.annotion.DbTable;

/**
 * 中间层,逻辑实现
 * @param <T>
 */
public class BaseDao<T> implements IBaseDao<T> {
    /**
     * 持有数据库操作类的引用
     */
    private SQLiteDatabase database;

    private Class<T> entityClass;

    private String tableName;

    private HashMap<String,Field> cacheMap;

    private boolean isInit = false;

    public synchronized boolean init(Class<T> entity, SQLiteDatabase sqLiteDatabase){
        //没有初始化
        if (!isInit){
            entityClass = entity;
            database = sqLiteDatabase;
            tableName  = entity.getAnnotation(DbTable.class).value();
            //开始自动建表
            if (!sqLiteDatabase.isOpen()){//数据库是否打开
                return false;
            }
            if (!autoCreateTable()){//建表失败
                return false;
            }
            isInit = true;
        }
        initCacheMap();

        return isInit;
    }

    private void initCacheMap() {
        //映射关系
        //情况一 其他开发者改变了表结构
        //情况二 版本升级了 在新的版本中删除了表的一个字段，由于数据库版本没有更新成功导致插入这个对象时产生崩溃
        cacheMap = new HashMap<>();
        //查一次空表，查看表的字段
        String sql = "SELECT * FROM "+this.tableName+" LIMIT 1,0 ";
        Cursor cursor = database.rawQuery(sql ,null);
        //得到字段名数组
        String[] columnNames =cursor.getColumnNames();
        Field[] columnFields = entityClass.getDeclaredFields();
        for (String columnName : columnNames){
            Field resultField = null;
            for (Field field : columnFields){
                if (columnName.equals(field.getAnnotation(DbFiled.class).value())){//找到映射关系
                    resultField = field;
                    break;
                }
            }
            if (resultField !=null){//将映射关系储存
                cacheMap.put(columnName,resultField);
            }
        }
    }
    private boolean autoCreateTable() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("CREATE TABLE IF NOT EXISTS ");
        stringBuffer.append(tableName+" ( ");
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            Class type = field.getType();
            if (type == String.class) {
                stringBuffer.append(field.getAnnotation(DbFiled.class).value() + " TEXT,");
            } else if (type == Double.class) {
                stringBuffer.append(field.getAnnotation(DbFiled.class).value() + " DOUBLE,");
            } else if (type == Integer.class) {
                stringBuffer.append(field.getAnnotation(DbFiled.class).value() + " INTEGER,");
            } else if (type == Long.class) {
                stringBuffer.append(field.getAnnotation(DbFiled.class).value() + " BIGINT,");
            } else if (type == byte[].class) {
                stringBuffer.append(field.getAnnotation(DbFiled.class).value() + " BLOB,");
            } else {
                /**
                 * 不支持类型
                 */
                continue;
            }
        }
        if (stringBuffer.charAt(stringBuffer.length() - 1) == ','){
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        }
        stringBuffer.append(")");
        try {
            this.database.execSQL(stringBuffer.toString());
        }catch (Exception e){
                e.printStackTrace();
                return false;
        }
        return  true;
    }

    @Override
    public Long insert(T entity) {
        ContentValues contentValues = getValues(entity);
        database.insert(tableName,null,contentValues);

        return null;
    }

    private ContentValues getValues(T entity) {
        ContentValues contentValues = new ContentValues();
        Iterator<Map.Entry<String,Field>> iterator = cacheMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String,Field> fieldEntry = iterator.next();
            //成员变量 ---->取值
            Field field = fieldEntry.getValue();
            //表字段名
            String key = fieldEntry.getKey();

            field.setAccessible(true);

            try {
                Object object = field.get(entity);
                Class type = field.getType();
                if (type == String.class){
                    String value = (String) object;
                    contentValues.put(key,value);
                }else if(type == Double.class){
                    Double value = (Double) object;
                    contentValues.put(key,value);
                }else if(type == Integer.class){
                    Integer value = (Integer) object;
                    contentValues.put(key,value);
                }else if(type == Long.class){
                    Long value = (Long) object;
                    contentValues.put(key,value);
                }else if(type == byte[].class){
                    byte[] value = (byte[]) object;
                    contentValues.put(key,value);
                }else {
                    /**
                     * 不支持类型
                     */
                   continue;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return contentValues;
    }


}
