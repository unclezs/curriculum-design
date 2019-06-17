package com.unclezs.dataStruct;

import com.sun.org.apache.regexp.internal.RE;

import java.util.Arrays;
import java.util.Random;

/*
 *顺序表做储存的 二叉查找树
 *按数组下标进行存储，根节点存储在下标0处，其左孩子存储于下标2*0+1，右孩子存储于下标2*0+2 …依次类型。
 *下标为i的节点，左右孩子存储于下标2*i+1与2*i+2
 *@author unclezs.com
 *@date 2019.06.15 13:59
 */
public class BSTree {
    private MyList<Integer> bSTree;
    private int aNum;//比较次数
    private int size;//元素个数
    public BSTree(int capaCity) {
        this.bSTree = new MyList<>((int) Math.pow(2,capaCity+1));
    }

    //添加元素
    public void add(Integer v){
        aNum++;
        //根节点
        int i=0;
        if(bSTree.get(0)==null){
            bSTree.set(0,v);
        }
        while (bSTree.get(i)!=null){
            if(v>bSTree.get(i)){//大于往左走
               i=i*2+2;
            }else if(v<bSTree.get(i)){//小于往右走
                i=i*2+1;
            }else {
                size--;
                break;
            }
            aNum++;
        }
        size++;
        bSTree.set(i,v);

    }
    //查找元素
    public boolean query(int v){
        int i=0;
        while (bSTree.get(i)!=null){
            if(v>bSTree.get(i)){//大于往左走
                i=i*2+2;
            }else if(v<bSTree.get(i)){//小于往右走
                i=i*2+1;
            }else {
                return true;
            }
        }
        if(bSTree.get(i)==null){
            return false;
        }
        return true;
    }
    //删除元素
    public void remove(int v){
        //删除头节点
        if(size==1){
            bSTree.set(0,null);
            return;
        }
        //没有则直接返回
        if(!query(v)){
            System.out.println("不存在："+v);
            return;
        }
        int i=0;
        //找到下该值下标
        while (bSTree.get(i)!=null){
            if(v>bSTree.get(i)){//大于往左走
                i=i*2+2;
            }else if(v<bSTree.get(i)){//小于往右走
                i=i*2+1;
            }else {
                break;
            }
        }
        //开始删除
        if(bSTree.get(i*2+1)!=null&&bSTree.get(i*2+2)!=null){//有两个孩子
            int min=i*2+2;//直接后继
            while (bSTree.get(min*2+1)!=null){
                min=min*2+1;
            }
            bSTree.set(i,bSTree.get(min));
            bSTree.set(min,bSTree.get(min*2+2));
            setNull(min*2+2);
        }else if(bSTree.get(i*2+1)!=null){//只有左节点
            bSTree.set(i,getLeft(i));
            setNull(i*2+1);
        }else if(bSTree.get(i*2+2)!=null){//有右节点
            bSTree.set(i,getRgiht(i));
            setNull(i*2+2);
        }else {//没有子节点
            bSTree.set(i,null);
        }
    }
    //中序遍历
    public void inorder(int i){
        if(bSTree.get(i)==null)
            return;
        inorder(2*i+1);
        System.out.print(bSTree.get(i)+" ");
        inorder(2*i+2);
    }
    //平均查找长度
    public double getASL(){
        return (0.0+aNum)/size;
    }
    private int getParentLeft(int i){
       return bSTree.get((i-1)/2);
    }
    private int getParentRight(int i){
        return bSTree.get((i-2)/2);
    }
    private int getLeft(int i){
        return bSTree.get(i*2+1);
    }
    private int getRgiht(int i){
        return bSTree.get(i*2+2);
    }
    private void setNull(int i){
        bSTree.set(i,null);
    }
    public static void main(String[] args) {
        BSTree bs=new BSTree(10);
        //读入值
        int[] v=new int[]{10,8,7,9,3,15,16,20,21,2};
        for (int i = 0; i < v.length; i++) {
            bs.add(v[i]);
        }
        System.out.println("原数据"+Arrays.toString(v));
        //中序遍历
        System.out.print("中序遍历：");
        bs.inorder(0);
        System.out.println();
        System.out.println("平均查找长度:"+bs.getASL());
        System.out.println("查找元素x=3得："+bs.query(45));
        bs.remove(8);
        System.out.print("删除x=3后：");
        bs.inorder(0);
    }
}