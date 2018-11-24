package DataStructures;


import java.util.ArrayList;

/**
 * 列表 测试
 */
public class ListTest {

    public static void main(String[] args){
        testArrayList();
    }

    private static void testArrayList(){
        ArrayList<Integer> arrayList = new ArrayList<>();

        arrayList.add(0,1);
        arrayList.add(2,2);

        System.out.println(arrayList);


    }
}
