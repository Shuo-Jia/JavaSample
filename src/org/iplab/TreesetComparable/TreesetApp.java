package org.iplab.TreesetComparable;

import org.iplab.TestCode.Producer;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by js982 on 2017/6/6.
 */
public class TreesetApp {

    public static void main(String[] arg){
        TreeSet persons = new TreeSet();

        persons.add(new Person("A",10));
        persons.add(new Person("B",2));
        persons.add(new Person("C",3));
        persons.add(new Person("D",4));


        Iterator iterator = persons.iterator();

        while(iterator.hasNext()){
            Person p = (Person)iterator.next();
            System.out.println("Name:"+p.getName()+" "+"Age:"+p.getAge());
        }

    }
}
