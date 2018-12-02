package DataStructures;


import com.xiongyx.datastructures.iterator.Iterator;
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
        for(int i=0; i<20; i++){
            list.add(i);
        }

        System.out.println(list);


        Iterator<Integer> iterator = list.iterator();
        while (iterator.hasNext()){
            int data = iterator.next();
            if(data % 3 == 0){
                iterator.remove();

                System.out.print("移除了:" + data);
                System.out.println(list);
            }
        }

        System.out.println(list);
    }
}
