package DataStructures.map;

import com.xiongyx.datastructures.iterator.Iterator;
import com.xiongyx.datastructures.map.AVLTree;
import com.xiongyx.datastructures.map.Map;
import com.xiongyx.datastructures.map.TreeMap;

/**
 * @Author xiongyx
 * @Date 2019/1/30
 */
public class AVLTreeTest {
    public static void main(String[] args) {
        int num = 30000;

        testAVL(num);

        testNormalBST(num);

        testJDKTreeMap(num);
    }

    private static void testAVL(int num){
        AVLTree<Integer,String> avlTree = new AVLTree<>();

        long startInsert = System.currentTimeMillis();
        for(int i=1; i<=num; i++){
//            System.out.println("i=" + i);

            int data = getData(i);
            avlTree.put(data,data+"");
        }

//        System.out.println(avlTree);

        long endInsert = System.currentTimeMillis();
        System.out.println("插入avl spend=" + (endInsert - startInsert));

        long startRemove = System.currentTimeMillis();
        Iterator<Map.EntryNode<Integer,String>> iterator = avlTree.iterator();
        while(iterator.hasNext()){
            if(iterator.next().getKey() % 5 == 0){
                iterator.remove();
            }
        }
        System.out.println(avlTree.size());

        long endRemove = System.currentTimeMillis();
        System.out.println("删除avl spend=" + (endRemove - startRemove));
    }

    private static void testNormalBST(int num){
        TreeMap<Integer,String> normalBST = new TreeMap<>();

        long startInsert = System.currentTimeMillis();
        for(int i=1; i<=num; i++){
//            System.out.println("i=" + i);

            int data = getData(i);
            normalBST.put(data,data+"");
        }

        long endInsert = System.currentTimeMillis();
        System.out.println("插入BST spend=" + (endInsert - startInsert));

        long startRemove = System.currentTimeMillis();
        Iterator<Map.EntryNode<Integer,String>> iterator = normalBST.iterator();
        while(iterator.hasNext()){
            if(iterator.next().getKey() % 5 == 0){
                iterator.remove();
            }
        }
        System.out.println(normalBST.size());

        long endRemove = System.currentTimeMillis();
        System.out.println("删除BST spend=" + (endRemove - startRemove));
    }

    private static void testJDKTreeMap(int num){
        java.util.TreeMap<Integer,String> jdkTreeMap = new java.util.TreeMap<>();

        long startInsert = System.currentTimeMillis();
        for(int i=1; i<=num; i++){
//            System.out.println("i=" + i);

            int data = getData(i);
            jdkTreeMap.put(data,data+"");
        }

//        System.out.println(normalBST);

        long endInsert = System.currentTimeMillis();
        System.out.println("插入JDK BST spend=" + (endInsert - startInsert));

        long startRemove = System.currentTimeMillis();
        java.util.Iterator<java.util.Map.Entry<Integer,String>> iterator = jdkTreeMap.entrySet().iterator();
        while(iterator.hasNext()){
            if(iterator.next().getKey() % 5 == 0){
                iterator.remove();
            }
        }
        System.out.println(jdkTreeMap.size());

        long endRemove = System.currentTimeMillis();
        System.out.println("删除JDK BST spend=" + (endRemove - startRemove));
    }

    private static int getData(int i){
        //(int)(Math.random() * i)
        return i;
    }
}
