package DataStructures;


import com.xiongyx.datastructures.iterator.Iterator;
import com.xiongyx.datastructures.list.ArrayList;

/**
 * 列表 测试
 */
public class ListTest {

    public static void main(String[] args){
        testArrayList();
    }

    private static void testArrayList(){
        ArrayList<Integer> arrayList = new ArrayList<>();

        for(int i=0; i<20; i++){
            arrayList.add(i);
        }
        System.out.println("init: " + arrayList);


//        for(int i=0; i<arrayList.size(); i++){
//            if(arrayList.indexOf(i)%10 == 0){
//                arrayList.remove(i);
//            }
//        }
//        System.out.println("remove to forEach: " + arrayList);

        Iterator<Integer> iterator = arrayList.iterator();
        while(iterator.hasNext()){
            int item = iterator.next();
            if(item%3 == 0){
                iterator.remove();
            }
        }
        System.out.println("remove to forEach: " + arrayList);
    }
}
