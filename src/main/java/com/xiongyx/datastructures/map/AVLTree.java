package com.xiongyx.datastructures.map;

/**
 * @Author xiongyx
 * @Date 2019/1/25
 *
 * AVL树实现
 */
public class AVLTree<K,V> extends TreeMap<K,V>{

    @Override
    public V put(K key, V value) {
        if(this.root == null){
            this.root = new EntryNode<>(key,value);
            this.size++;
            return null;
        }

        //:::获得目标节点
        TargetEntryNode<K,V> targetEntryNode = getTargetEntryNode(key);
        if(targetEntryNode.relativePosition == RelativePosition.CURRENT){
            //:::目标节点存在于当前容器

            //:::暂存之前的value
            V oldValue = targetEntryNode.target.value;
            //:::替换为新的value
            targetEntryNode.target.value = value;
            //:::返回之前的value
            return oldValue;
        }else{
            //:::目标节点不存在于当前容器
            EntryNode<K,V> parent = targetEntryNode.parent;
            EntryNode<K,V> newEntryNode = new EntryNode<>(key,value,parent);
            if(targetEntryNode.relativePosition == RelativePosition.LEFT){
                //:::目标节点位于左边
                parent.left = newEntryNode;
            }else{
                //:::目标节点位于右边
                parent.right = newEntryNode;
            }

            //:::插入新节点后进行重平衡操作
            afterNewNodeInsert(newEntryNode);

            this.size++;
            return null;
        }
    }

    @Override
    public V remove(K key) {
        V returnValue = super.remove(key);

        //:::判断删除之后，二叉搜索树是否失去了平衡
        //:::进行重平衡

        return returnValue;
    }

    /**
     * 3+4 重构
     * */
    private void refactor34(
        EntryNode<K,V> leftNode, EntryNode<K,V> middleNode, EntryNode<K,V> rightNode,
        EntryNode<K,V> llChild, EntryNode<K,V> lrChild,
        EntryNode<K,V> rlChild, EntryNode<K,V> rrChild){

        //:::调整 左节点和对应子树的拓扑结构
        leftNode.left = llChild;
        if(llChild != null){
            llChild.parent = leftNode;
        }

        leftNode.right = lrChild;
        if(lrChild != null){
            lrChild.parent = leftNode;
        }
        //:::todo updateHeight leftNode

        //:::调整 右节点和对应子树的拓扑结构
        rightNode.left = rlChild;
        if(rlChild != null){
            rlChild.parent = rightNode;
        }

        rightNode.right = rrChild;
        if(rrChild != null){
            rrChild.parent = rightNode;
        }
        //:::todo updateHeight rightNode

        //:::调整 中间节点 和左、右节点的拓扑结构
        middleNode.left = leftNode;
        middleNode.right = rightNode;

        leftNode.parent = middleNode;
        rightNode.parent = middleNode;
        //:::todo updateHeight middleNode
    }

    /**
     * 插入后 重平衡操作
     * */
    private void afterNewNodeInsert(EntryNode<K,V> newEntryNode){
        EntryNode<K,V> currentAncestorNode = newEntryNode.parent;

        //:::遍历新插入节点的祖先节点,逐层向上
        while(currentAncestorNode != null){
            //:::判断当前祖先节点是否失去平衡
            if(!isAVLBalanced(currentAncestorNode)){
                //:::不平衡

            }else{
                //:::平衡

                //:::todo 更新当前祖先节点的高度
            }

            currentAncestorNode = currentAncestorNode.parent;
        }
    }

    /**
     * 当前节点 是否满足AVL树约定的平衡条件
     * */
    private boolean isAVLBalanced(EntryNode<K,V> entryNode){
        //:::获得 左子树高度
        int leftChildHeight = getHeight(entryNode.left);
        //:::获得右子树高度
        int rightChildHeight = getHeight(entryNode.right);

        //:::获得左右子树高度差
        int difference = leftChildHeight - rightChildHeight;

        //:::高度差绝对值在1之内,认为是满足AVL平衡条件
        return -1 <= difference && difference <= 1;
    }

    /**
     * 获得当前节点的高度
     * */
    private int getHeight(EntryNode<K,V> entryNode){
        if(entryNode == null){
            return 0;
        }else{
            //:::获得 左子树高度
            int leftChildHeight = getHeight(entryNode.left);
            //:::获得右子树高度
            int rightChildHeight = getHeight(entryNode.right);

            //:::当前节点高度 = 更高子树分支高度 + 1
            return Math.max(leftChildHeight,rightChildHeight) + 1;
        }
    }

    /**
     * 进行旋转
     * */
    private void rotateAt(EntryNode<K,V> entryNode){

    }

    /**
     * 获得较高子树分支孩子节点
     */
    private EntryNode<K,V> getTallerChild(EntryNode<K,V> entryNode){


        return null;
    }
}
