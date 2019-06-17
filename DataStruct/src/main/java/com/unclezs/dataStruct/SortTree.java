package com.unclezs.dataStruct;

/*
 *二叉排序树，可变红黑树
 *@author unclezs.com
 *@date 2019.06.12 21:39
 */
public class SortTree<E> {
    private Node<E> root;//根节点
    private int size = 0;//当前节点数量
    public boolean isFix=false;//是否开启红黑树
    private double findLen;//查找长度
    //添加节点
    public boolean add(E e) {
        Node<E> r = root;//跟节点
        Node<E> parent;//父节点
        int cmp;//比较数
        //第一次add的时候
        if (r == null) {
            root = new Node<>(e, null);
            size = 1;
            return true;
        }
        Comparable<? super E> ec = (Comparable<? super E>) e;
        do {
            parent = r;
            cmp = ec.compareTo(r.e);
            if (cmp < 0) {//小于父节点放左边
                r = r.left;
            } else if (cmp > 0) {//大于父节点放右边
                r = r.right;
            } else {//等于则键值重复，添加失败
                return false;
            }
        } while (r != null);
        //找到父节点有左或者右支节点为空
        Node<E> node = new Node<>(e, parent);
        if (cmp < 0) {
            parent.left = node;
        } else {
            parent.right = node;
        }
        size++;
        //是否修复红黑树
        if(isFix)
            fixInsert(node);
        return true;
    }
    //获取根节点
    public Node<E> first() {
        return root;
    }
    //删除节点
    public boolean remove(E e) {
        Node<E> node = getNode(e);
        //没有这个节点
        if (node == null)
            return false;
        //如果有两个节点
        if (node.left != null && node.right != null) {
            Node<E> minNode = node.right;//直接前驱
            //找到右边最小的节点
            while (minNode.left != null) {
                minNode = minNode.left;
            }
            //左枝
            minNode.left = node.left;
            node.left.parent = minNode;
            //右枝节
            if (node != minNode.parent) {
                minNode.parent.left = minNode.right;
                if (minNode.right != null) {
                    minNode.right.parent = minNode.parent;
                }
                minNode.right = node.right;
                node.right.parent = minNode;
            }
            //父节点
            minNode.parent = node.parent;
            if (node.parent.left == node) {//如果待删节点在父节点左边
                node.parent.left = minNode;
            } else {
                node.parent.right = minNode;
            }
        } else if (node.left != null) {//只有左节点
            if (node == node.parent.left) {
                node.parent.left = node.left;
            } else if (node == node.parent.right) {
                node.parent.right = node.left;
            }
            node.left.parent = node.parent;
        } else if (node.right != null) {//只有右节点
            if (node == node.parent.left) {
                node.parent.left = node.right;
            } else if (node == node.parent.right) {
                node.parent.right = node.right;
            }
            node.right.parent = node.parent;
        } else {//没有子节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else if (node == node.parent.right) {
                node.parent.right = null;
            }
            node.parent = null;
        }
        return true;
    }
    //根据值查找节点
    public Node<E> getNode(E e) {
        findLen++;
        Comparable<? super E> ec = (Comparable<? super E>) e;
        Node<E> r = root;
        while (r != null) {
            findLen++;
            int cmp = ec.compareTo(r.e);
            if (cmp < 0)
                r = r.left;
            else if (cmp > 0)
                r = r.right;
            else
                return r;
        }
        return null;
    }
    //根据头节点进行中序遍历,返回队列,第一个参数为根节点，第二个请传入null
    MyQueue<Node<E>> queue=new MyQueue<>();
    public MyQueue<Node<E>> inorder(Node<E>... roots) {
        if(roots.length==2){//重置队列
            queue=new MyQueue<>();
        }
        Node<E> r=roots[0];
        if (r == null) {
            return queue;
        }
        inorder(r.left);
//        System.out.print(root.e + " ");
        if(r.x!=0.0){
            queue.add(r);
        }
        getNode(r.e);//获取查找长度
        inorder(r.right);
        return queue;
    }

    /**
     * 返回平均查找长度
     *
     * @return 查找长度
     */
    public int getAvgLen() {
        findLen=0;
        inorder(root);
        return (int) ((findLen + 0.0) / size);
    }

    //树节点
    public class Node<E> {
        boolean color=BLACK;
        E e;//值
        private Node<E> parent;//父节点
        private Node<E> left;//左节点
        private Node<E> right;//右节点
        public double x, y;//坐标


        public Node(E e, Node<E> parent) {
            this.e = e;
            this.parent = parent;
        }

        public E getElement() {
            return e;
        }

        public Node<E> getParent() {
            return parent;
        }

        public Node<E> getLeft() {
            return left;
        }

        public Node<E> getRight() {
            return right;
        }
        @Override
        public String toString() {
            return "Node{" +
                    " e=" + e +
                    ", x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    /*************************红黑树相关***************************/
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    /*
     * 左旋示意图：对节点x进行左旋
     *     p                       p
     *    /                       /
     *   x                       y
     *  / \                     / \
     * lx  y      ----->       x  ry
     *    / \                 / \
     *   ly ry               lx ly
     * 左旋做了三件事：
     * 1. 将y的左子节点赋给x的右子节点,并将x赋给y左子节点的父节点(y左子节点非空时)
     * 2. 将x的父节点p(非空时)赋给y的父节点，同时更新p的子节点为y(左或右)
     * 3. 将y的左子节点设为x，将x的父节点设为y
     */
    void leftRotate(Node<E> x) {
        if(x==null)
            return;
        Node<E> y = x.right;
        x.right = y.left;//将y的左子节点赋给x的右子节点
        if (y.left != null) {//将x赋给y左子节点的父节点(y左子节点非空时)
            y.left.parent = x;
        }

        Node<E> p = x.parent;
        y.parent = p;//将x的父节点p(非空时)
        if (p != null) {
            if (p.left == x) {//更新p的子节点为y(左或右)
                p.left = y;
            } else {
                p.right = y;
            }
        } else {
            this.root = y;//没有父节点则为根节点
        }
        //将y的左子节点设为x，将x的父节点设为y
        y.left = x;
        x.parent = y;
    }

    /*
     * 左旋示意图：对节点y进行右旋
     *        p                   p
     *       /                   /
     *      y                   x
     *     / \                 / \
     *    x  ry   ----->      lx  y
     *   / \                     / \
     * lx  rx                   rx ry
     * 右旋做了三件事：
     * 1. 将x的右子节点赋给y的左子节点,并将y赋给x右子节点的父节点(x右子节点非空时)
     * 2. 将y的父节点p(非空时)赋给x的父节点，同时更新p的子节点为x(左或右)
     * 3. 将x的右子节点设为y，将y的父节点设为x
     */
    void rightRotate(Node<E> y) {
        if(y==null)
            return;
        Node<E> x = y.left;
        y.left = x.right;//将x的右子节点赋给y的左子节点
        if (x.right != null) {
            x.right.parent = y;//将y赋给x右子节点的父节点
        }
        Node<E> p = y.parent;
        x.parent = p;
        if (p != null) {
            if (p.right == y) {
                p.right = x;
            } else {
                p.left = x;
            }
        } else {
            this.root = x;
        }
        x.right = y;
        y.parent = x;
    }

    //修复红黑树
    void fixInsert(Node<E> me){
        me.color=RED;
        //x为根节点时候不需要操作，x父亲为黑色时为正确红黑树，不需要修复
        while (me!=null&&me!=root&&me.parent.color==RED){
            //分两大种情况处理，父亲在左边或者右边时
            if(parentOf(me)==leftOf(parentOf(parentOf(me)))){//父亲在左边的时候
                Node<E> uncle = rightOf(parentOf(parentOf(me)));//获取叔叔(在右)
                if(colorOf(uncle)==BLACK){//叔叔为黑色
                    //此时父左，叔右，若我为左则以祖父为根R旋转，若我在右边则以我为根进行LR转
                    if(rightOf(parentOf(me))==me){//我在右边
                        me=parentOf(me);
                        leftRotate(me);
                    }
                    setColor(parentOf(me),BLACK);
                    setColor(parentOf(parentOf(me)),RED);
                    rightRotate(parentOf(parentOf(me)));
                }else {//叔叔为红色,交换颜色（父亲,叔叔）<->（祖父），再将祖父接着前操作
                    setColor(parentOf(uncle),RED);//祖父红色
                    setColor(parentOf(me),BLACK);//父亲黑色
                    setColor(uncle,BLACK);//叔叔黑色
                    me=parentOf(uncle);//祖父为节点接着修复
                }
            }
            else {//父亲在右边的时候
                Node<E> uncle = leftOf(parentOf(parentOf(me)));//获取叔叔(在左)
                if(colorOf(uncle)==BLACK){//叔叔为黑色
                    //此时父右，叔左，若我为左则以我为根RL旋转，若我在右边则以祖父为根进行L转
                    if(leftOf(parentOf(me))==me){//我在左边
                        me=parentOf(me);
                        rightRotate(me);
                    }
                    setColor(parentOf(me),BLACK);
                    setColor(parentOf(parentOf(me)),RED);
                    leftRotate(parentOf(parentOf(me)));
                }else {//叔叔为红色,交换颜色（父亲,叔叔）<->（祖父），再将祖父接着前操作
                    setColor(parentOf(uncle),RED);//祖父红色
                    setColor(parentOf(me),BLACK);//父亲黑色
                    setColor(uncle,BLACK);//叔叔黑色
                    me=parentOf(uncle);//祖父为节点接着修复
                }
            }
        }
        root.color=BLACK;
    }
    // 防止空指针
    private boolean colorOf(Node<E> p) {
        return (p == null ? BLACK : p.color);
    }
    private Node<E> parentOf(Node<E> p) {
        return (p == null ? null : p.parent);
    }
    private void setColor(Node<E> p, boolean c) {
        if (p != null)
            p.color = c;
    }
    private Node<E> leftOf(Node<E> p) {
        return (p == null) ? null : p.left;
    }
    private Node<E> rightOf(Node<E> p) {
        return (p == null) ? null : p.right;
    }
}
