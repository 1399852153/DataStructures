package com.xiongyx.datastructures.list;

import com.xiongyx.datastructures.iterator.Iterator;

/**
 * @Author xiongyx
 * on 2018/11/17.
 *
 * 列表---线性表实现
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
        size = 0;
        //:::设置数组大小为默认
        elements = new Object[DEFAULT_CAPACITY];
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
     * 插入时,下标越界检查
     * @param index 下标值
     */
    private void rangeCheckForAdd(int index){
        //:::如果下标小于0或者大于size的值，抛出异常
        //:::注意：插入时，允许插入线性表的末尾，因此(index == size)是合法的
        if(index > this.size || index < 0){
            throw new RuntimeException("index error  index=" + index + " size=" + this.size) ;
        }
    }

    /**
     * 下标越界检查
     * @param index 下标值
     */
    private void rangeCheck(int index){
        //:::如果下标小于0或者大于等于size的值，抛出异常
        if(index >= this.size || index < 0){
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
            //:::内部数组指向扩充了容量的新数组
            this.elements = new Object[this.capacity];

            //:::为了代码的可读性，使用for循环实现新老数组的copy操作
            //:::Tips: 比起for循环，System.arraycopy基于native的内存批量复制在内部数组数据量较大时具有更高的执行效率
            for(int i=0; i<tempArray.length; i++){
                this.elements[i] = tempArray[i];
            }
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
     * 向量 是否为空
     * @return true: 为空;
     *          false: 不为空;
     * */
    @Override
    public boolean isEmpty() {
        return (this.size == 0);
    }

    @Override
    public int indexOf(E e) {
        //:::判断当前参数是否为null
        if(e != null){
            //::::参数不为null
            //:::从前到后依次比对
            for(int i=0; i<this.size; i++){
                //:::判断当前item是否 equals 参数e
                if(e.equals(elements[i])){
                    //:::匹配成功，立即返回当前下标
                    return i;
                }
            }
        }else{
            //:::参数为null
            //:::从前到后依次比对
            for(int i=0; i<this.size; i++){
                //:::判断当前item是否为null
                if(this.elements[i] == null){
                    //:::为null，立即返回当前下标
                    return i;
                }
            }
        }

        //:::遍历列表未找到相等的元素，返回特殊值"-1"代表未找到
        return -1;
    }

    @Override
    public boolean contains(E e) {
        //:::复用indexOf方法,如果返回-1代表不存在;反之，则代表存在
        return (indexOf(e) != -1);
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
        //:::插入时，数组下标越界检查
        rangeCheckForAdd(index);
        //:::插入新数据前进行扩容检查
        expandCheck();

        //:::插入位置下标之后的元素整体向后移动一位(防止数据被覆盖，并且保证数据在数组中的下标顺序)
        //:::Tips: 比起for循环，System.arraycopy基于native的内存批量复制在内部数组数据量较大时具有更高的执行效率
        for(int i=this.size; i>index; i--){
            this.elements[i] = this.elements[i-1];
        }

        //:::在index下标位置处插入元素"e"
        this.elements[index] = e;
        //:::size自增
        this.size++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E remove(int index) {
        //:::数组下标越界检查
        rangeCheck(index);

        //:::先暂存将要被移除的元素
        E willBeRemoved = (E)this.elements[index];

        //:::将删除下标位置之后的数据整体前移一位
        //:::Tips: 比起for循环，System.arraycopy基于native的内存批量复制在内部数组数据量较大时具有更高的执行效率
        for(int i=index+1; i<this.size; i++){
            this.elements[i-1] = this.elements[i];
        }

        //:::由于数据整体前移了一位，释放列表末尾的失效引用，增加GC效率
        this.elements[(this.size - 1)] = null;
        //:::size自减
        this.size--;

        //:::返回被删除的元素
        return willBeRemoved;
    }

    @Override
    public boolean remove(E e) {
        //:::获得当前参数"e"的下标位置
        int index = indexOf(e);

        //:::如果存在
        if(index != -1){
            //:::删除下标对应位置的数据
            remove(index);
            //:::返回true，代表删除成功
            return true;
        }else{
            //:::不进行删除
            //:::返回false,代表删除失败
            return false;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public E set(int index, E e) {
        //:::数组下标越界检查
        rangeCheck(index);

        //:::先暂存之前index下标处元素的引用
        E oldValue = (E)this.elements[index];
        //:::将index下标元素设置为参数"e"
        this.elements[index] = e;

        //:::返回被替换掉的元素
        return oldValue;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E get(int index) {
        //:::数组下标越界检查
        rangeCheck(index);

        //:::返回对应下标的元素
        return (E)this.elements[index];
    }

    @Override
    public void clear() {
        //:::遍历列表，释放内部元素引用，增加GC效率
        for(int i=0; i<this.size; i++){
            this.elements[i] = null;
        }

        //:::将size重置为0
        this.size = 0;
    }

    /**
     * 收缩内部数组，使得"内部数组的大小"和"线性表逻辑大小"相匹配，提高空间利用率
     * */
    public void trimToSize(){
        //:::如果当前线性表逻辑长度 小于 内部数组的大小
        if(this.size < this.capacity){
            //:::创建一个和当前线性表逻辑大小相等的新数组
            Object[] newElements = new Object[this.size];

            //:::将当前旧内部数组的数据复制到新数组中
            //:::Tips: 这里使用Arrays.copy方法进行复制,效率更高
            for(int i = 0; i< newElements.length; i++){
                newElements[i] = this.elements[i];
            }
            //:::用新数组替换掉之前老的内部数组
            this.elements = newElements;
            //:::设置当前容量
            this.capacity = this.size;
        }
    }

    @Override
    public String toString(){
        //:::空列表
        if(this.isEmpty()){
            return "[]";
        }

        //:::列表起始使用"["
        StringBuilder s = new StringBuilder("[");

        //:::从第一个到倒数第二个元素之间
        for(int i=0; i<size-1; i++){
            //:::使用", "进行分割
            s.append(elements[i]).append(",").append(" ");
        }

        //:::最后一个元素使用"]"结尾
        s.append(elements[size - 1]).append("]");
        return s.toString();
    }

    //===============================================迭代器相关==============================================
    /**
     * 获得线性表 迭代器
     * */
    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    /**
     * 向量 迭代器内部类
     * */
    private class Itr implements Iterator<E>{
        /**
         * 迭代器下一个元素 指针下标
         */
        private int nextIndex = 0;
        /**
         * 迭代器当前元素 指针下标
         * */
        private int currentIndex = -1;

        @Override
        public boolean hasNext() {
            //:::如果"下一个元素指针下标" 小于 "当前线性表长度" ==> 说明迭代还未结束
            return this.nextIndex < ArrayList.this.size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public E next() {
            //:::当前元素指针下标 = 下一个元素指针下标
            this.currentIndex = nextIndex;
            //:::下一个元素指针下标自增,指向下一元素
            this.nextIndex++;

            //:::返回当前元素
            return (E)ArrayList.this.elements[this.currentIndex];
        }

        @Override
        public void remove() {
            //:::删除当前元素
            ArrayList.this.remove(this.currentIndex);
            //:::由于删除了当前下标元素，数据段整体向前平移一位，因此nextIndex不用自增

            //:::为了防止用户在一次迭代(next调用)中多次使用remove方法，将currentIndex设置为-1
            this.currentIndex = -1;
        }
    }
}
