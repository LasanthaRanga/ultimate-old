package modle;

import java.util.HashMap;
import java.util.List;

/**
 * @author suhada
 */
public interface DAO<T> {

    public boolean save(T t);

    public int saveWithId(T t);

    public boolean save(List<T> list);

    public boolean update(T t);

    public boolean update(List<T> list);

    public boolean saveOrUpdate(T t);

    public boolean saveOrUpdate(List<T> list);

    public boolean delete(T t);

    public List<T> getList();

    public HashMap<Integer, T> getHasMap();
}