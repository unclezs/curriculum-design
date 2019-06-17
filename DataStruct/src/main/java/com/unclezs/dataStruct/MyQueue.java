package com.unclezs.dataStruct;

import java.util.Queue;

/*
 *@author unclezs.com
 *@date 2019.06.06 15:05
 */
public class MyQueue<E> {
   private MyList<E> queueData=new MyList<>();
   //入队列
   public void add(E e){
       queueData.add(e);
   }
   //获取当前队列长度
    public int size(){
       return queueData.size();
    }
   //头部出队列，并且删除
   public synchronized E poll(){
       if(queueData.size()==0)
           return null;
       E e = queueData.get(0);
       queueData.remove(0);
       return e;
   }
   //取头部出元素，不删除
   public E peek(){
       if(queueData.size()==0)
           return null;
       return queueData.get(0);
   }
}
