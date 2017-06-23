package org.iplab.TestCode;

import java.util.Properties;
import java.util.Set;

/**
 * Created by js982 on 2017/6/2.
 */
public class Producer {
}

/*
死锁：常见情景之一：同步的嵌套。

*/

class Test implements Runnable
{
    private boolean flag;
    Test(boolean flag)
    {
        this.flag = flag;
    }

    public void run()
    {

        if(flag)
        {
            while(true)
                synchronized(MyLock.locka)
                {
                    System.out.println(Thread.currentThread().getName()+"..if   locka....");
                    synchronized(MyLock.lockb)				{

                        System.out.println(Thread.currentThread().getName()+"..if   lockb....");
                    }
                }
        }
        else
        {
            while(true)
                synchronized(MyLock.lockb)
                {
                    System.out.println(Thread.currentThread().getName()+"..else  lockb....");
                    synchronized(MyLock.locka)
                    {
                        System.out.println(Thread.currentThread().getName()+"..else   locka....");
                    }
                }
        }

    }

}

class MyLock
{
    public static final Object locka = new Object();
    public static final Object lockb = new Object();
}




class SampleTest
{
    public static void main(String[] args)
    {
        /*Test a = new Test(true);
        Test b = new Test(false);

        Thread t1 = new Thread(a);
        Thread t2 = new Thread(b);
        t1.start();
        t2.start();*/
        Property.propertiesDemo();
    }
}

class Property {

    public static void propertiesDemo(){
        //创建一个Properties集合。

        Properties prop  = new Properties();

        //存储元素。
        prop.setProperty("zhangsan","30");
        prop.setProperty("lisi","31");
        prop.setProperty("wangwu","36");
        prop.setProperty("zhaoliu","20");

        //修改元素。
        prop.setProperty("wangwu","26");

        //取出所有元素。
        Set<String> names = prop.stringPropertyNames();

        for(String name : names){
            String value = prop.getProperty(name);
            System.out.println(name+":"+value);
        }
    }
}
