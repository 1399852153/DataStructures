package DataStructures.map;


/**
 * @Author xiongyx
 * @Date 2018/12/26
 */
public class HashMapTest {
    public static void main(String[] args){
        testJDKHashMap();

        System.out.println("=================================================");

        testMyHashMap();
    }

    private static void testJDKHashMap(){
        java.util.Map<Integer,String> map1 = new java.util.HashMap<>(1,2);
        System.out.println(map1.put(1,"aaa"));
        System.out.println(map1.put(2,"bbb"));
        System.out.println(map1.put(3,"ccc"));
        System.out.println(map1.put(1,"aaa"));
        System.out.println(map1.put(2,"bbb"));
        System.out.println(map1.put(3,"ccc"));
        System.out.println(map1.put(1,"111"));
        System.out.println(map1.put(3,"aaa"));
        System.out.println(map1.put(4,"ddd"));
        System.out.println(map1.put(5,"eee"));
        System.out.println(map1.put(6,"fff"));
        System.out.println(map1.put(8,"ggg"));
        System.out.println(map1.put(11,"bbb"));
        System.out.println(map1.put(22,"ccc"));
        System.out.println(map1.put(33,"111"));
        System.out.println(map1.put(9,"111"));
        System.out.println(map1.put(10,"111"));
        System.out.println(map1.put(12,"111"));
        System.out.println(map1.put(13,"111"));
        System.out.println(map1.put(14,"111"));

        System.out.println(map1.toString());
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
        com.xiongyx.datastructures.map.Map<Integer,String> map2 = new com.xiongyx.datastructures.map.HashMap<>(1,2);
        System.out.println(map2.put(1,"aaa"));
        System.out.println(map2.put(2,"bbb"));
        System.out.println(map2.put(3,"ccc"));
        System.out.println(map2.put(1,"aaa"));
        System.out.println(map2.put(2,"bbb"));
        System.out.println(map2.put(3,"ccc"));
        System.out.println(map2.put(1,"111"));
        System.out.println(map2.put(3,"aaa"));
        System.out.println(map2.put(4,"ddd"));
        System.out.println(map2.put(5,"eee"));
        System.out.println(map2.put(6,"fff"));
        System.out.println(map2.put(8,"ggg"));
        System.out.println(map2.put(11,"bbb"));
        System.out.println(map2.put(22,"ccc"));
        System.out.println(map2.put(33,"111"));
        System.out.println(map2.put(9,"111"));
        System.out.println(map2.put(10,"111"));
        System.out.println(map2.put(12,"111"));
        System.out.println(map2.put(13,"111"));
        System.out.println(map2.put(14,"111"));

        System.out.println(map2.toString());
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
