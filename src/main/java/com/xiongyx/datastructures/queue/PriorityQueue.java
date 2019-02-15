package com.xiongyx.datastructures.queue;

/**
 * @Author xiongyx
 * @Date 2019/2/15
 *
 * 优先级队列 ADT接口
 */
public interface PriorityQueue <T>{

    /**
     * 插入数据
     * @param newData 新数据
     * */
    void insert(T newData);

    /**
     * 获得优先级最大值
     * @return  当前优先级最大的数据
     * */
    T getMax();

    /**
     * 删除当前优先级最大值
     * @return  被删除的 当前优先级最大的数据
     */
    T delMax();

    /**
     *
     * */
    int size();
}
