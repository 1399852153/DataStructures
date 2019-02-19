package com.xiongyx.datastructures.queue;

import com.xiongyx.datastructures.iterator.Iterator;

/**
 * @Author xiongyx
 * @Date 2019/2/15
 *
 * 优先级队列 ADT接口
 */
public interface PriorityQueue <E>{

    /**
     * 插入新数据
     * @param newData 新数据
     * */
    void insert(E newData);

    /**
     * 获得优先级最大值（窥视）
     * @return  当前优先级最大的数据
     * */
    E peekMax();

    /**
     * 获得并且删除当前优先级最大值
     * @return  被删除的 当前优先级最大的数据
     */
    E popMax();

    /**
     * 获得当前优先级队列 元素个数
     * @return 当前优先级队列 元素个数
     * */
    int size();
}
