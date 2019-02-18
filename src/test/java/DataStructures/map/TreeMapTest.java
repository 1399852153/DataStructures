package DataStructures.map;

import DataStructures.util.CommonUtil;
import com.xiongyx.datastructures.iterator.Iterator;
import com.xiongyx.datastructures.map.Map;
import com.xiongyx.datastructures.map.TreeMap;

/**
 * @Author xiongyx
 * @Date 2019/1/8
 */
public class TreeMapTest {
    public static void main(String[] args){
        //:::反向比较
        TreeMap<Integer,String> treeMap = new TreeMap<>(
            (o1, o2) -> -(o1.compareTo(o2))
        );
        treeMap.put(3,"3");
        treeMap.put(2,"2");
        treeMap.put(1,"1");

        for(int i=0; i<100; i++){
            int value = (int)(Math.random() * i * 10);
            CommonUtil.show("value=" + value);
            treeMap.put(value,value + "");
        }

        CommonUtil.show(treeMap);
        CommonUtil.show("size=" + treeMap.size());

        Iterator<Map.EntryNode<Integer,String>> iterator = treeMap.iterator();
        while(iterator.hasNext()){
            Map.EntryNode<Integer,String> entry = iterator.next();
            if(entry.getKey() % 3 == 0){
                CommonUtil.show("删除key entry=" + entry);
                iterator.remove();
            }
        }

        CommonUtil.show(treeMap);
        CommonUtil.show("size=" + treeMap.size());
    }
}
