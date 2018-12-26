package DataStructures;


/**
 * @Author xiongyx
 * @Date 2018/12/26
 */
public class MapTest {
    public static void main(String[] args){
        java.util.Map<Integer,String> map1 = new java.util.HashMap<>();
        System.out.println(map1.put(1,"aaa"));
        System.out.println(map1.put(2,"bbb"));
        System.out.println(map1.put(3,"ccc"));
        System.out.println(map1.put(1,"111"));
        System.out.println(map1.size());
        System.out.println(map1.get(1));
        System.out.println(map1.get(2));
        System.out.println(map1.get(3));
        System.out.println(map1.remove(1));
        System.out.println(map1.remove(2));
        System.out.println(map1.size());


        System.out.println("=================================================");

        com.xiongyx.datastructures.map.Map<Integer,String> map2 = new com.xiongyx.datastructures.map.HashMap<>();
        System.out.println(map2.put(1,"aaa"));
        System.out.println(map2.put(2,"bbb"));
        System.out.println(map2.put(3,"ccc"));
        System.out.println(map2.put(1,"111"));
        System.out.println(map2.size());
        System.out.println(map2.get(1));
        System.out.println(map2.get(2));
        System.out.println(map2.get(3));
        System.out.println(map2.remove(1));
        System.out.println(map2.remove(2));
        System.out.println(map2.size());

    }
}
