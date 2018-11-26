package DataStructures;


import com.xiongyx.datastructures.list.LinkedList;

/**
 * 列表 测试
 */
public class ListTest {

    public static void main(String[] args){
        testArrayList();
    }

    private static void testArrayList(){
        LinkedList<Integer> list = new LinkedList<>();
        boolean b1 = list.isEmpty();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(0,10);

        list.remove(4);

        int i1 = list.indexOf(10);
        int i2 = list.indexOf(100);
        list.set(1,100);
        int i3 = list.indexOf(100);
        boolean b2 = list.isEmpty();
        list.get(0);
        list.clear();
        boolean b3 = list.isEmpty();
    }
}
