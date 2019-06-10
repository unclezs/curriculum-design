package com.unclezs.dataStruct;

import java.io.Serializable;
import java.util.Arrays;

/*
 *@author unclezs.com
 *@date 2019.06.03 14:40
 */
public class MyList<T> implements Serializable {
    Object[] elementData={};//元素
    private static final Object[] EMPTY_ELEMENTDATA={};
    private static int capaCity=10;//容量
    private int size=0;//当前元素个数

    //泛型转化
    public MyList(int capaCity){
        if(capaCity>0){
            elementData=new Object[capaCity];
        }else if(capaCity==0){
            elementData=EMPTY_ELEMENTDATA;
        }else {
            throw new IllegalArgumentException("参数错误");
        }
    }
    //默认10个大小构造
    public MyList(){
        this(capaCity);
    }
    //当前容量
    public int size(){
        return size;
    }
    //检测是否满了，满了则扩容
    private void checkAndExpansion(){
        if(size+1>capaCity){
            this.elementData = Arrays.copyOf(elementData, size*2);
        }
    }

    //泛型转化
    private T elementData(int index){
        return (T)elementData[index];
    }
    //指定下标设置值
    public void set(int index,T o){
        checkAndExpansion();
        elementData[index]=o;
    }
    //在尾部添加一个元素
    public void add(T o){
        checkAndExpansion();
        elementData[size++]=o;
    }
    //移除一个元素
    public void remove(int index){
        System.arraycopy(elementData,index+1,elementData,index,size-index-1);
        size--;
    }
    //根据下标查询
    public T get(int index){
        checkAndExpansion();
        return elementData(index);
    }
    public void clear(){
        this.size=0;
        elementData=new Object[capaCity];
    }
    public void addAll(MyList<T> myList){
        if(myList==null||myList.size()==0)
            return;
        for(int i=0;i<myList.size();i++){
            this.add(myList.get(i));
        }
    }

}
