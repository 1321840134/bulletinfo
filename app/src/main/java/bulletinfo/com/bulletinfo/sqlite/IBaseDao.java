package bulletinfo.com.bulletinfo.sqlite;

/**
 *
 */
public interface IBaseDao<T> {

    Long insert(T entity);

}
