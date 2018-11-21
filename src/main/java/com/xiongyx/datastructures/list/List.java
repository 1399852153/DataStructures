package com.xiongyx.datastructures.list;

import com.xiongyx.datastructures.iterator.Iterator;

/**
 * @Author xiongyx
 * on 2018/11/18.
 *
 * 列表ADT 接口定义
 */
public interface List <E> {

    /**
     * @return 返回当前列表中元素的个数
     */
    int size();

    /**
     * 判断当前列表是否为空
     * @return 如果当前列表中元素个数为0，返回true；否则，返回false
     */
    boolean isEmpty();

    /**
     * 返回元素"e"在列表中的下标值
     * @param e     查询的元素"e"
     * @return      返回"obj"元素在列表中的下标值;
     *               "obj"不存在于列表中，返回-1;
     */
    int indexOf(E e);

    /**
     * 判断元素"e"是否存在于列表中
     * @param e     查询的元素"e"
     * @return      返回"true"代表存在，返回"false"代表不存在;
     */
    boolean contains(E e);

    /**
     * 在列表的末尾插入元素"e"
     * @param e 需要插入的元素
     * @return  插入成功，返回true；否则返回false
     * */
    boolean add(E e);

    /**
     * 在列表的下标为"index"位置处插入元素"e"
     * @param index 插入位置的下标
     * @param e     需要插入的元素
     */
    void add(int index, E e);

    /**
     *  从列表中找到并且移除"e"对象
     * @param e   需要被移除的元素"e"
     * @return      找到并且成功移除返回true；否则返回false
     */
    boolean remove(E e);

    /**
     * 移除列表中下标为"index"位置处的元素
     * @param index  需要被移除元素的下标
     * @return      返回被移除的元素
     */
    E remove(int index);

    /**
     * 将列表中下标为"index"位置处的元素替代为"e"
     * @param index 需要被替代元素的下标
     * @param e     新的元素
     * @return      返回被替代的元素
     */
    E set(int index,E e);

    /**
     * 返回列表中下标为"index"位置处的元素
     * @param index 查找元素的"index"元素
     * @return      返回找到的元素
     */
    E get(int index);

    /**
     * 清除列表中所有元素
     * */
    void clear();

    /**
     * 获得迭代器
     * */
    Iterator<E> iterator();
}
