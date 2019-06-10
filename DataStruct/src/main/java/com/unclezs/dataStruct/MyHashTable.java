package com.unclezs.dataStruct;


import java.util.Objects;

/*
 *@author unclezs.com
 *@date 2019.06.04 18:51
 */
public class MyHashTable<K,V> {
    private int num=0;//索引使用数量
    private int capacity;//容量
    private Node<K,V>[] table;//散列表
    private int mode=1;//处理冲突方式 1开放地址法，2链表储存法
    private double eRate=0.8;//扩容因子（达到容量的多少比例后扩容）
    private int hashMode;//哈希值生成函数选择
    private int conflictsNum;//冲突次数
    private int linkNodeNum;//链式储存时当前节点个数
    //默认容量16
    public MyHashTable(){
        this(16);
    }
    //构建散列表
    public MyHashTable(int capacity){
        this.capacity=capacity;
        table=new Node[capacity];
    }

    /**
     * 高级构造
     * @param capacity 容量
     * @param mode 处理冲突方式
     * @param eRate 扩容因子
     */
    public MyHashTable(int capacity, int mode, double eRate,int hashMode) {
        this(capacity);
        this.mode = mode;
        this.eRate = eRate;
        this.hashMode=hashMode;
    }

    //散列函数
    public int hash(K key){
        if(hashMode==1){//余数法
            return (key.hashCode() & 0x7fffffff);
        }else {//折叠法+余数法
            int code=key.hashCode()& 0x7fffffff;
            //1865644118
            int h=code/1000000;//186
            h+=code/1000%1000;//564
            h+=code%1000;//118
            return h;
        }
    }
    //添加数据（三种情况，还没有初始化hash表，初始化了但是hash值冲突，key相同）
    public void put(K k,V v){
        //自动扩容，2倍扩容
        if(num>eRate*capacity){
            resize(capacity*2);
        }
        int hash=hash(k);//计算哈希值
        int index=indexFor(hash);//计算索引
        //如果没有冲突
        if(table[index]==null){
            table[index]=new Node<>(k,v,hash);
            linkNodeNum++;
            conflictsNum++;
        }else if(mode==1){//开放地址法解决冲突
            if(hash==table[index].hash&&(k.equals(table[index].getKey()))){//如果键的值一样且与上次hash值相同则更新
                table[index].setValue(v);
                conflictsNum++;
                return;
            }else {//确认为冲突
                while(table[index]!=null){
                    conflictsNum++;
                    //找到可以插入的索引
                    index=(index+1)%capacity;
                }
                table[index]=new Node<>(k,v,hash);
            }
        }else if(mode==2){//链式储存法解决冲突
            //判断是否为过更新
            Node<K,V> node=table[index];
            while (node.next!=null&&!node.getKey().equals(k)){//遍历找到末尾节点或者找到键值相同的节点
                node=node.next;
                conflictsNum++;
            }
            if(node.getKey().equals(k)){
                node.setValue(v);
                return;
            }
            //非更新
            linkNodeNum++;
            conflictsNum++;
            node.next=new Node<K,V>(k,v,hash);
            return;
        }
        num++;//当前表中索引使用数量增加
    }
    //删除数据
    public void remove(K k){

    }
    /**
     * 查询数据
     * @param k key值
     * @return 没找到返回null
     */
    public V get(K k){
        int hash=hash(k);
        int index=indexFor(hash);
        Node<K,V> node=table[index];
        if(node==null){
            return null;
        }
        if(mode==1){//开放地址法
            while (table[index]!=null&&!k.equals(table[index].getKey())){//如果当前索引处node为空了并且还没有找到与键值匹配的关键字则跳出循环
                index=(index+1)%capacity;
            }
            return table[index]==null?null:table[index].getValue();
        }else {//链式储存法
            while (node.next!=null&&!k.equals(node.getKey())){
                node=node.next;
            }
           if(k.equals(node.getKey())){
               return node.getValue();
           }
            return null;
        }
    }
    //获取index
    private int indexFor(int hash){
        return hash%capacity;
    }
    //获取当前表中数量,1线性探测法,2链式法
    public int getNum(int type){
        return type==1?num:linkNodeNum;
    }
    //获取容量
    public int getCapacity(){
        return capacity;
    }
    //获取冲突次数
    public int getConflictsNum() {
        return conflictsNum;
    }

    /**
     * 更改处理冲突模式
     * @param mode 1开放地址法，2链表储存法
     */
    public void setMode(int mode){
        this.mode=mode;
    }
    //重新设置大小
    public void resize(int capacity){
        //如果传入容量小于当前容量则缩小容量
        int size=this.capacity;
        if(capacity<this.capacity){
            size=capacity;
        }
        //数据迁移
        Node<K,V>[] tab=new Node[capacity];
        for(int i=0;i<size;i++){
            tab[i]=table[i];
        }
        table=tab;
        this.capacity=capacity;
    }
    //自定义节点类
    static class Node<K,V>{
        final K key;//键
        final int hash;//哈希值
        V value;//值
        Node<K,V> next;//链表处理冲突时用
        Node(K k,V v,int hash){
            this.key= k;
            this.value=v;
            this.hash=hash;
            this.next=null;
        }
        public final K getKey(){
            return key;
        }
        public final V getValue(){
            return value;
        }
        public final V setValue(V newValue){
            V oldValue=value;
            value=newValue;
            return oldValue;
        }
        public final int hashCode(){
            return key.hashCode()^value.hashCode();
        }
        public final boolean equals(Object o){
            Node<?,?> e=(Node<?,?>)o;
            if (Objects.equals(key, e.getKey()) && Objects.equals(value, e.getValue()))
                return true;
            return false;
        }
        public final String toString(){
            return key+"=" +value;
        }
    }
}
