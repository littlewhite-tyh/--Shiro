package com.huike.mapper;

import java.util.List;

public interface Imapper<T> {
    /**
     *  保存
     * @param pojo
     * @return
     */
    int insert(T pojo);

    /**
     * 根据id进行更新
     */
    int update(T pojo);

    /**
     * 根据id删除数据
     */
    int delete(Long id);

    /**
     * 以非空字段值作为查询条件进行查询
     */
    List<T> select(T pojo);

}
