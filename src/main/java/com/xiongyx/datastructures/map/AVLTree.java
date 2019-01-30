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

        //获得目标节点
        TargetEntryNode<K,V> targetEntryNode = getTargetEntryNode(key);
        if(targetEntryNode.relativePosition == RelativePosition.CURRENT){
            //目标节点存在于当前容器

            //暂存之前的value
            V oldValue = targetEntryNode.target.value;
            //替换为新的value
            targetEntryNode.target.value = value;
            //返回之前的value
            return oldValue;
        }else{
            //目标节点不存在于当前容器
            EntryNode<K,V> parent = targetEntryNode.parent;
            EntryNode<K,V> newEntryNode = new EntryNode<>(key,value,parent);
            if(targetEntryNode.relativePosition == RelativePosition.LEFT){
                //目标节点位于左边
                parent.left = newEntryNode;
            }else{
                //目标节点位于右边
                parent.right = newEntryNode;
            }

            //插入新节点后进行重平衡操作
            afterNewNodeInsert(newEntryNode);

            this.size++;
            return null;
        }
    }

    @Override
    public V remove(K key) {
        if(this.root == null){
            return null;
        }

        //查询目标节点
        TargetEntryNode<K,V> targetEntryNode = getTargetEntryNode(key);
        if(targetEntryNode.relativePosition != RelativePosition.CURRENT){
            //没有找到目标节点
            return null;
        }else{
            //找到了目标节点
            EntryNode<K,V> target = targetEntryNode.target;
            //先保存被删除节点 删除之前的双亲节点
            EntryNode<K,V> parent = target.parent;

            //从二叉树中删除目标节点
            deleteEntryNode(target);

            //删除节点后，对其历代祖先节点进行重平衡操作
            afterNodeRemove(parent);

            return targetEntryNode.target.value;
        }
    }

    /**
     * 插入后 重平衡操作
     * @param newEntryNode 新插入的节点
     * */
    private void afterNewNodeInsert(EntryNode<K,V> newEntryNode){
        EntryNode<K,V> currentAncestorNode = newEntryNode.parent;

        //遍历新插入节点的祖先节点,逐层向上
        while(currentAncestorNode != null){
            //判断当前祖先节点是否失去平衡
            if(!isAVLBalanced(currentAncestorNode)){
                //不平衡

                //获得重构之前 失衡节点的父节点及其相对位置
                EntryNode<K,V> parent = currentAncestorNode.parent;
                //获得更高子树分支对应的孙辈节点，决定AVL树重平衡的策略
                EntryNode<K,V> tallerSonNode = getTallerChild(currentAncestorNode);
                EntryNode<K,V> tallerGrandSonNode = getTallerChild(tallerSonNode);
                //以孙辈节点为基准，进行旋转，重平衡
                EntryNode<K,V> newSubTreeRoot = rotateAt(currentAncestorNode,tallerSonNode,tallerGrandSonNode);

                //重构之后的子树 重新和全树对接
                newSubTreeRoot.parent = parent;
                //可能currentAncestorNode是根节点，不存在双亲节点
                if(parent != null){
                    //原子树根节点的双亲节点 和新的子树进行连接
                    if(isLeftChild(parent,currentAncestorNode)){
                        parent.left = newSubTreeRoot;
                    }else{
                        parent.right = newSubTreeRoot;
                    }
                }else{
                    this.root = newSubTreeRoot;
                }
                //插入时，最低失衡节点重平衡后，全树即恢复平衡，因此结束循环
                return;
            }else{
                //平衡

                //todo 更新当前祖先节点的高度
            }

            //指向上一层祖先节点
            currentAncestorNode = currentAncestorNode.parent;
        }
    }

    /**
     * 插入后 重平衡操作
     * @param deletedNode 被删除的节点
     * */
    private void afterNodeRemove(EntryNode<K,V> deletedNode){
        EntryNode<K,V> currentAncestorNode = deletedNode;

        //遍历新插入节点的祖先节点,逐层向上
        while(currentAncestorNode != null){
            //判断当前祖先节点是否失去平衡
            if(!isAVLBalanced(currentAncestorNode)){
                //不平衡

                //获得重构之前 失衡节点的父节点及其相对位置
                EntryNode<K,V> parent = currentAncestorNode.parent;
                //获得更高子树分支对应的孙辈节点，决定AVL树重平衡的策略
                EntryNode<K,V> tallerSonNode = getTallerChild(currentAncestorNode);
                EntryNode<K,V> tallerGrandSonNode = getTallerChild(tallerSonNode);
                //以孙辈节点为基准，进行旋转，重平衡
                EntryNode<K,V> newSubTreeRoot = rotateAt(currentAncestorNode,tallerSonNode,tallerGrandSonNode);

                //重构之后的子树 重新和全树对接
                newSubTreeRoot.parent = parent;
                //可能currentAncestorNode是根节点，不存在双亲节点
                if(parent != null){
                    //原子树根节点的双亲节点 和新的子树进行连接
                    if(isLeftChild(parent,currentAncestorNode)){
                        parent.left = newSubTreeRoot;
                    }else{
                        parent.right = newSubTreeRoot;
                    }
                }else{
                    this.root = newSubTreeRoot;
                }
            }else{
                //平衡

                //todo 更新当前祖先节点的高度
            }

            //指向上一层祖先节点
            currentAncestorNode = currentAncestorNode.parent;
        }
    }

    /**
     * 3+4 重构
     * */
    private void refactor34(
        EntryNode<K,V> leftNode, EntryNode<K,V> middleNode, EntryNode<K,V> rightNode,
        EntryNode<K,V> llChild, EntryNode<K,V> lrChild,
        EntryNode<K,V> rlChild, EntryNode<K,V> rrChild){

        //调整 左节点和对应子树的拓扑结构
        leftNode.left = llChild;
        if(llChild != null){
            llChild.parent = leftNode;
        }

        leftNode.right = lrChild;
        if(lrChild != null){
            lrChild.parent = leftNode;
        }
        //todo updateHeight leftNode

        //调整 右节点和对应子树的拓扑结构
        rightNode.left = rlChild;
        if(rlChild != null){
            rlChild.parent = rightNode;
        }

        rightNode.right = rrChild;
        if(rrChild != null){
            rrChild.parent = rightNode;
        }
        //todo updateHeight rightNode

        //调整 中间节点 和左、右节点的拓扑结构
        middleNode.left = leftNode;
        middleNode.right = rightNode;

        leftNode.parent = middleNode;
        rightNode.parent = middleNode;
        //todo updateHeight middleNode
    }

    /**
     * 当前节点 是否满足AVL树约定的平衡条件
     * */
    private boolean isAVLBalanced(EntryNode<K,V> entryNode){
        //获得 左子树高度
        int leftChildHeight = getHeight(entryNode.left);
        //获得右子树高度
        int rightChildHeight = getHeight(entryNode.right);

        //获得左右子树高度差
        int difference = leftChildHeight - rightChildHeight;

        //高度差绝对值在1之内,认为是满足AVL平衡条件
        return -1 <= difference && difference <= 1;
    }

    /**
     * 获得当前节点的高度
     * */
    private int getHeight(EntryNode<K,V> entryNode){
        if(entryNode == null){
            return 0;
        }else{
            //获得 左子树高度
            int leftChildHeight = getHeight(entryNode.left);
            //获得右子树高度
            int rightChildHeight = getHeight(entryNode.right);

            //当前节点高度 = 更高子树分支高度 + 1
            return Math.max(leftChildHeight,rightChildHeight) + 1;
        }
    }

    /**
     * 进行旋转,使用3+4重构完成重平衡
     * @return 重构之后子树的树根节点
     * */
    private EntryNode<K,V> rotateAt(EntryNode<K,V> currentNode,EntryNode<K,V> sonNode,EntryNode<K,V> grandSonNode){
        if(isLeftChild(currentNode,sonNode)){
            //左 zig
            if(isLeftChild(sonNode,grandSonNode)){
                //左-左   zig-zig旋转
                refactor34(grandSonNode,sonNode,currentNode,
                    grandSonNode.left,grandSonNode.right,sonNode.right,currentNode.right);

                return sonNode;
            }else{
                //左-右   zig-zag旋转
                refactor34(sonNode,grandSonNode,currentNode,
                    sonNode.left,grandSonNode.left,grandSonNode.right,currentNode.right);

                return grandSonNode;
            }
        }else{
            //右 zag
            if(isLeftChild(sonNode,grandSonNode)){
                //右-左   zag-zig旋转
                refactor34(currentNode,grandSonNode,sonNode,
                    currentNode.left,grandSonNode.left,grandSonNode.right,sonNode.right);

                return grandSonNode;
            }else{
                //右-右   zag-zag旋转
                refactor34(currentNode,sonNode,grandSonNode,
                currentNode.left,sonNode.left,grandSonNode.left,grandSonNode.right);

                return sonNode;
            }
        }
    }

    /**
     * 获得较高子树分支孩子节点
     */
    private EntryNode<K,V> getTallerChild(EntryNode<K,V> entryNode){
        int leftChildHeight = getHeight(entryNode.left);
        int rightChildHeight = getHeight(entryNode.right);

        if(leftChildHeight > rightChildHeight){
            //左子树高度 > 右子树高度
            return entryNode.left;
        }else{
            //左子树高度 <= 右子树高度
            return entryNode.right;
        }
    }

    /**
     * 是否是左孩子
     * */
    private boolean isLeftChild(EntryNode<K,V> parent,EntryNode<K,V> target){
        return getRelativeByParent(parent,target) == RelativePosition.LEFT;
    }
}
