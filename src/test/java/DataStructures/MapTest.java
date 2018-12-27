package DataStructures;


/**
 * @Author xiongyx
 * @Date 2018/12/26
 */
public class MapTest {
    public static void main(String[] args){
        testJDKHashMap();

        System.out.println("=================================================");

        testMyHashMap();
    }

    private static void testJDKHashMap(){
        java.util.Map<Integer,String> map1 = new java.util.HashMap<>();
        System.out.println(map1.put(1,"aaa"));
        System.out.println(map1.put(2,"bbb"));
        System.out.println(map1.put(3,"ccc"));
        System.out.println(map1.put(1,"aaa"));
        System.out.println(map1.put(2,"bbb"));
        System.out.println(map1.put(3,"ccc"));
        System.out.println(map1.put(1,"111"));
        System.out.println(map1.put(3,"aaa"));
        System.out.println(map1.put(4,"bbb"));
        System.out.println(map1.put(5,"ccc"));
        System.out.println(map1.put(6,"111"));
        System.out.println(map1.put(8,"aaa"));
        System.out.println(map1.put(111,"bbb"));
        System.out.println(map1.put(222,"ccc"));
        System.out.println(map1.put(1321,"111"));
        System.out.println(map1.containsKey(1));
        System.out.println(map1.containsKey(11));
        System.out.println(map1.containsValue("bbb"));
        System.out.println(map1.containsValue("aaa"));
        System.out.println(map1.size());
        System.out.println(map1.get(1));
        System.out.println(map1.get(2));
        System.out.println(map1.get(3));
        System.out.println(map1.remove(1));
        System.out.println(map1.remove(2));
        System.out.println(map1.size());

    }

    private static void testMyHashMap(){
        com.xiongyx.datastructures.map.Map<Integer,String> map2 = new com.xiongyx.datastructures.map.HashMap<>();
        System.out.println(map2.put(1,"aaa"));
        System.out.println(map2.put(2,"bbb"));
        System.out.println(map2.put(3,"ccc"));
        System.out.println(map2.put(1,"111"));
        System.out.println(map2.put(3,"aaa"));
        System.out.println(map2.put(4,"bbb"));
        System.out.println(map2.put(5,"ccc"));
        System.out.println(map2.put(6,"111"));
        System.out.println(map2.put(8,"aaa"));
        System.out.println(map2.put(111,"bbb"));
        System.out.println(map2.put(222,"ccc"));
        System.out.println(map2.put(1321,"111"));
        System.out.println(map2.containsKey(1));
        System.out.println(map2.containsKey(11));
        System.out.println(map2.containsValue("bbb"));
        System.out.println(map2.containsValue("aaa"));
        System.out.println(map2.size());
        System.out.println(map2.get(1));
        System.out.println(map2.get(2));
        System.out.println(map2.get(3));
        System.out.println(map2.remove(1));
        System.out.println(map2.remove(2));
        System.out.println(map2.size());
    }
}
