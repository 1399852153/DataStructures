package com.xiongyx.datastructures.list;

/**
 * @Author xiongyx
 * on 2018/11/17.
 *
 * 列表接口 线性表实现
 */
public class ArrayList <E> implements List <E>{

    //===============================================成员变量======================================================
    /**
     * 内部封装的数组
     */
    private Object[] elements;

    /**
     * 线性表默认的容量大小
     * */
    private static final int DEFAULT_CAPACITY = 16;

    /**
     * 扩容翻倍的基数
     * */
    private static final double EXPAND_BASE = 1.5;

    /**
     * 内部数组的实际大小
     * */
    private int capacity;

    /**
     * 当前线性表的实际大小
     * */
    private int size;

    //=================================================构造方法======================================================
    /**
     * 默认的无参构造方法
     * */
    public ArrayList() {
        this.capacity = DEFAULT_CAPACITY;
        size = 0;
        //:::设置数组大小为默认
        elements = new Object[capacity];
    }

    /**
     * 设置内部数组初始大小的构造方法
     * @param capacity 内部数组初始大小
     * */
    public ArrayList(int capacity) {
        this.capacity = capacity;
        size = 0;
        //:::设置数组大小
        elements = new Object[capacity];
    }

    //===============================================内部辅助方法===================================================
    /**
     * 下标越界检查
     * @param index 下标值
     */
    private void rangeCheck(int index){
        //:::如果下标小于0或者超过了最大值，抛出异常
        if(index > this.size || index < 0){
            throw new RuntimeException("index error  index=" + index + " size=" + this.size) ;
        }
    }

    /**
     * 内部数组扩容检查
     * */
    private void expandCheck(){
        //:::如果当前元素个数 = 当前内部数组容量
        if(this.size == this.capacity){
            //:::需要扩容

            //:::先暂存之前内部数组的引用
            Object[] tempArray = this.elements;
            //:::当前内部数组扩充 一定的倍数
            this.capacity = (int)(this.capacity * EXPAND_BASE);
            this.elements = new Object[this.capacity];

            //:::为了代码的可读性，使用for循环实现新老数组的copy操作
            for(int i=0; i<tempArray.length; i++){
                this.elements[i] = tempArray[i];
            }
            //:::Tips: 比起for循环，arraycopy基于native的内存批量复制在内部数组数据量较大时具有更高的执行效率
            //System.arraycopy(tempArray, 0, elements, 0, tempArray.length);
        }
    }

    //=================================================外部接口=======================================================
    /**
     * 返回线性表 内部元素的个数
     * */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * 线性表 是否为空
     * @return true: 为空;
     *          false: 不为空;
     * */
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * 在线性表的末尾插入元素"e"
     * @param e 需要插入的元素
     * @return  插入成功，返回true；否则返回false
     * */
    @Override
    public boolean add(E e) {
        //:::插入新数据前进行扩容检查
        expandCheck();

        //;::在末尾插入元素
        this.elements[this.size] = e;
        //:::size自增
        this.size++;

        return true;
    }

    /**
     * 在线性表的下标为"index"位置处插入元素"e"
     * @param index 插入位置的下标
     * @param e     需要插入的元素
     */
    @Override
    public void add(int index, E e) {
        //:::数组下标越界检查
        rangeCheck(index);
        //:::插入新数据前进行扩容检查
        expandCheck();

        //:::插入位置下标之后的元素整体向后移动一位(防止数据被覆盖，并且保证数据在数组中的下标顺序)
        for(int i=this.size; i>index; i--){
            this.elements[i] = this.elements[i-1];
        }
        //:::Tips: 比起for循环，arraycopy基于native的内存批量复制在内部数组数据量较大时具有更高的执行效率

        //:::在index下标位置处插入元素"e"
        this.elements[index] = e;
        //:::size自增
        this.size++;
    }

    @Override
    public boolean remove(Object obj) {
        return false;
    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public E set(int index, Object o) {
        return null;
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public int indexOf(Object obj) {
        return 0;
    }
}
