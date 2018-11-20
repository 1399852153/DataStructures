package com.xiongyx.datastructures.iterator;

/**
 * 迭代器接口
 * */
public interface Iterator <E> {

    /**
     * 当前迭代器 是否存在下一个元素
     * @return  true：存在
     *           false：不存在
     * */
    boolean hasNext();

    /**
     * 获得迭代器 迭代的下一个元素
     * @return 迭代的下一个元素
     * */
    E next();

    /**
     * 移除迭代器 当前迭代的元素
     */
    void remove();
}
