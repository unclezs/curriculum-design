package com.unclezs.dataStruct;

/*
 *@author unclezs.com
 *@date 2019.06.06 14:58
 */
public class MyStack<E> {
    private MyList<E> stackData=new MyList<>();
    //压栈
    public void push(E e){
        stackData.add(e);
    }
    //出栈
    public synchronized E pop(){
        int index=stackData.size()-1;
        E e =stackData.get(index);
        stackData.remove(index);
        return e;
    }
    //取出栈顶值
    public synchronized E peek(){
        int index=stackData.size()-1;
        E e =stackData.get(index);
        return e;
    }
    //是否为空
    public boolean empty(){
        return stackData.size()==0?true:false;
    }
}
