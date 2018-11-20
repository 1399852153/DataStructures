package DataStructures;


import com.xiongyx.datastructures.list.ArrayList;

/**
 * 列表 测试
 */
public class ListTest {

    public static void main(String[] args){
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        arrayList.add(5);

        System.out.println(arrayList);
        arrayList.remove(3);
        System.out.println(arrayList);
    }
}
