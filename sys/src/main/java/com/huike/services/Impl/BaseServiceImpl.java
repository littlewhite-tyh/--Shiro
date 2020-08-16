package com.huike.services.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huike.mapper.Imapper;
import com.huike.services.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public class BaseServiceImpl<T> implements BaseService<T> {
    @Autowired
    @Qualifier("imapper")
    private Imapper<T> imapper;

    @Override
    /**
     * 插入方法
     */
    public int insert(T pojo){
        return imapper.insert(pojo);
    }

    @Override
    /**
     * 更新方法
     */
    public int update(T pojo){
        return imapper.update(pojo);
    }

    @Override
    /**
     * 删除方法
     */
    public int delete(Long id){
        if(id==null){
            throw new RuntimeException("在删除数据的时候，没有指定id的值");
        }
        return imapper.delete(id);
    }

    @Override
    /**
     * 不需要参数的查询
     */
    public List<T> selectList(){
        return imapper.select(null);
    }


    /**
     * 条件查询
     */
    public List<T> selectList(T pojo){
        return imapper.select(pojo);
    }

    /**
     * 条件排序查询，根据name进行排序并查询
     */
    public List<T> selectList(T pojo,String orderBy){
        PageHelper.orderBy(orderBy);//排序
        return imapper.select(pojo);
    }

    /**
     * 查询一条数据。如果查询除了多条数据，就抛出throw异常
     */
    public T selectOne(T pojo){
        List<T> list = imapper.select(pojo);
        if(list!=null && list.size()>1){
            throw new RuntimeException("selectOne方法查询出了多条数据");
        }
        if(list==null || list.size()==0){
            return null;
        }
        return list.get(0);
    }

    /**
     * 此方法获取当前泛型类的真实类型

     */
    @SuppressWarnings("all")
    public T createInstanceAndSetIdOfFirstGeneric(Long id) {
        if (id == null) {
            //为了避免错误的将id设为null时把整张表都删除，在此检查
            throw new RuntimeException("id不能为null");
        }
        try {
            //通过反射创建泛型类对象并调用其setId方法设置id字段值
            ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
            Class clazz = (Class) type.getActualTypeArguments()[0];
            T t = (T) clazz.newInstance();
            clazz.getDeclaredMethod("setId", Long.class).invoke(t, id);
            return t;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据id进行查询，查询出多条数据会抛出Runtime异常
     */
    public T selectOne(Long id) {
        T pojo = createInstanceAndSetIdOfFirstGeneric(id);

        List<T> list = imapper.select(pojo);
        if (list == null || list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            throw new RuntimeException("selectOne方法查询出了多于1个结果");
        }
        return list.get(0);
    }

    /**
     * 条件分页查询
     */
    public PageInfo<T> page(int pageNum, int pageSize, T pojo) {
        PageHelper.startPage(pageNum, pageSize);
        List<T> list = imapper.select(pojo);
        return new PageInfo<T>(list);
    }
    /**
     * 条件分页排序查询
     */
    public PageInfo<T> page(int pageNum, int pageSize, T pojo, String orderBy) {
        PageHelper.startPage(pageNum, pageSize);//注意pageNum表示页码，从1开始
        PageHelper.orderBy(orderBy);
        List<T> list = imapper.select(pojo);//正常执行自己的Mapper的查询方法
        return new PageInfo<T>(list);
    }
    /**
     * 判断是否已经存在
     */
    public boolean isExisted(T pojo) {
        List<T> list = imapper.select(pojo);
        return list != null && list.size() > 0;
    }
}
