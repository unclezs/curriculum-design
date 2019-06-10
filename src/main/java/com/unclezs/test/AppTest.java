package com.unclezs.test;


import com.unclezs.dataStruct.MyHashTable;
import com.unclezs.dataStruct.MyList;
import com.unclezs.utils.RandomUtils;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.SortedMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *@author unclezs.com
 *@date 2019.06.03 15:13
 */
public class AppTest {

    @Test
    void testHashTable(){
        MyHashTable<String,String> hashTable=new MyHashTable<>(1000,1,0.8,2);
        for (int i = 0; i <1000; i++) {
            hashTable.put(RandomUtils.getRandomString(11,""),"");
        }
        System.out.println("----------");
        System.out.println("容量"+hashTable.getCapacity());
        System.out.println("----------");
        System.out.println("冲突次数"+hashTable.getConflictsNum());
        System.out.println("----------");
        System.out.println("元素个数"+hashTable.getNum(1));
        // 9999,1,0.9,1 冲突次数8595
        // 9999,2,0.9,1 冲突次数8999
        // 9999,2,0.9,2 冲突次数3717  0-999
        // 9999,1,0.9,2 冲突次数4551
    }
    @Test
    void test(){
        MyHashTable<Integer,String> hashTable=new MyHashTable<>(10,2,0.8,1);
        hashTable.put(11,"123");
        hashTable.put(21,"231");
        hashTable.put(21,"333");
        hashTable.put(31,"222");
        System.out.println(hashTable.get(31));
        System.out.println(hashTable.get(21));
        System.out.println(hashTable.get(11));
    }
}
