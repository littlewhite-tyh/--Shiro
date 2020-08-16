package com.huike.services;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.List;

public interface BaseService<T> {


    /**
     * 插入方法
     */
    public int insert(T pojo);

    /**
     * 更新方法
     */
    public int update(T pojo);

    /**
     * 删除方法
     */
    public int delete(Long id);

    /**
     * 不需要参数的查询
     */
    public List<T> selectList();

    /**
     * 条件查询
     */
    public List<T> selectList(T pojo);

    /**
     * 条件排序查询，根据name进行排序并查询
     */
    public List<T> selectList(T pojo,String orderBy);

    /**
     * 查询一条数据。如果查询除了多条数据，就抛出throw异常
     */
    public T selectOne(T pojo);

    /**
     * 此方法获取当前泛型类的真实类型

     */
    @SuppressWarnings("all")
    public T createInstanceAndSetIdOfFirstGeneric(Long id) ;

    /**
     * 根据id进行查询，查询出多条数据会抛出Runtime异常
     */
    public T selectOne(Long id) ;

    /**
     * 条件分页查询
     */
    public PageInfo<T> page(int pageNum, int pageSize, T pojo) ;

    /**
     * 条件分页排序查询
     */
    public PageInfo<T> page(int pageNum, int pageSize, T pojo, String orderBy) ;
    /**
     * 判断是否已经存在
     */
    public boolean isExisted(T pojo) ;
}
