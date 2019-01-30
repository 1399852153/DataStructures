package DataStructures;

import com.xiongyx.datastructures.map.AVLTree;

/**
 * @Author xiongyx
 * @Date 2019/1/30
 */
public class AVLTreeTest {
    public static void main(String[] args) {
        AVLTree<Integer,String> avlTree = new AVLTree<>();

        for(int i=1; i<=100; i++){
            System.out.println("i=" + i);
            avlTree.put(i,i+"");
            System.out.println(avlTree);
        }

        System.out.println(avlTree);
    }
}
