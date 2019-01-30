package DataStructures;

import com.xiongyx.datastructures.iterator.Iterator;
import com.xiongyx.datastructures.map.AVLTree;
import com.xiongyx.datastructures.map.Map;

/**
 * @Author xiongyx
 * @Date 2019/1/30
 */
public class AVLTreeTest {
    public static void main(String[] args) {
        testAVL();
    }

    private static void testAVL(){
        AVLTree<Integer,String> avlTree = new AVLTree<>();

        long startInsert = System.currentTimeMillis();
        for(int i=1; i<=1000; i++){
            System.out.println("i=" + i);
            avlTree.put(i,i+"");
        }

        System.out.println(avlTree);

        long endInsert = System.currentTimeMillis();
        System.out.println("插入avl spend=" + (endInsert - startInsert));

        long startRemove = System.currentTimeMillis();
        Iterator<Map.EntryNode<Integer,String>> iterator = avlTree.iterator();
        while(iterator.hasNext()){
            if(iterator.next().getKey() % 5 == 0){
                iterator.remove();
            }
        }
        System.out.println(avlTree);

        long endRemove = System.currentTimeMillis();
        System.out.println("删除avl spend=" + (endRemove - startRemove));
    }
}
