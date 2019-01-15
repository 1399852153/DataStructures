package DataStructures;

import com.xiongyx.datastructures.map.TreeMap;

/**
 * @Author xiongyx
 * @Date 2019/1/8
 */
public class TreeMapTest {
    public static void main(String[] args){
        TreeMap<Integer,String> treeMap = new TreeMap<>();
        treeMap.put(4,"4");
        treeMap.put(3,"3");
        treeMap.put(6,"6");
        treeMap.put(2,"2");
        treeMap.put(1,"1");
        treeMap.put(5,"5");
        CommonUtil.show(treeMap);

        treeMap.remove(2);
        CommonUtil.show(treeMap);
    }
}
