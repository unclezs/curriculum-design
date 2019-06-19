package com.unclezs.test;


import com.unclezs.dataStruct.*;
import com.unclezs.queryTel.User;
import com.unclezs.utils.RandomUtils;
import com.unclezs.utils.ResourceLoader;
import org.junit.jupiter.api.Test;
import sun.reflect.generics.tree.Tree;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *@author unclezs.com
 *@date 2019.06.03 15:13
 */
public class AppTest {

    @Test
    void testHashTable() throws Exception {
        MyHashTable<String,String> hashTable=new MyHashTable<>(1635,1,0.999,1);
        readData(hashTable);
        System.out.println("----------");
        System.out.println("容量"+hashTable.getCapacity());
        System.out.println("----------");
        System.out.println("冲突次数"+hashTable.getConflictsNum());
        System.out.println("----------");
        System.out.println("元素个数"+hashTable.getNum(1));
        System.out.println("平均查找在、长度"+(hashTable.getConflictsNum()+0.0)/hashTable.getNum(1));
    }
    @Test
    void test(){
        SortTree<Integer> set=new SortTree<>();
        set.add(1);
        set.add(4);
        set.add(3);
        set.add(5);
        set.add(7);
        set.add(6);
        set.inorder(set.first(),null);
        MyQueue<SortTree<Integer>.Node<Integer>> queue2 = set.inorder(set.first(),null);
//        while (queue.size()!=0){
//            System.out.println(queue.poll());
//        }
        while (queue2.size()!=0){
            System.out.println(queue2.poll());
        }
    }
    //层次遍历
    void levelRead(SortTree st){
        MyQueue<SortTree.Node> queue=new MyQueue<>();
        queue.add(st.first());
        int n=0;
        while (queue.size()!=0){
            int len=queue.size();
            System.out.print("第"+n+++"层:"+len+"个----------  ");
            for (int i = 0; i < len; i++) {
                SortTree.Node node = queue.poll();
                System.out.print(node.getElement()+" ");
                if(node.getLeft()!=null)
                    queue.add(node.getLeft());
                if(node.getRight()!=null){
                    queue.add(node.getRight());
                }
            }
            System.out.println();
        }
    }
    void readData(MyHashTable hashTable) throws Exception {
        String path="C:\\Users\\uncle\\Desktop\\sf.txt";
        BufferedReader r=new BufferedReader(new InputStreamReader(new FileInputStream(path),"gb2312"));
        String tmp;
        while ((tmp=r.readLine())!=null){
            User user=new User();
            user.setName(tmp.split(" ")[0]);
            user.setAddress(tmp.split(" ")[2]);
            user.setTel(tmp.split(" ")[1]);
//            hashTable.put(user.getName(),user);
            hashTable.put(user.getTel(),user);
//            System.out.println(user);
        }
        r.close();
    }

    //图测试
    @Test
    void testGraph(){
        Graph<Integer> graph=new Graph<>(5);
        graph.insertVertex("A");
        graph.insertVertex("B");
        graph.insertVertex("C");
        graph.insertVertex("D");
        graph.insertVertex("E");
        graph.insertEdge(0,2,1);
        graph.insertEdge(0,1,1);
        graph.insertEdge(1,2,1);
        graph.insertEdge(1,3,1);
        graph.insertEdge(1,4,1);
        graph.showGraph();
    }
    @Test
    void testFile(){
        String path = ResourceLoader.class.getResource("/trainInfo").getPath();
        File file = new File(path.substring(1));
        System.out.println(path.substring(1));
        System.out.println(file.exists()+"-------");
    }
}
