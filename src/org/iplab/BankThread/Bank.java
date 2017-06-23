package org.iplab.BankThread;

/**
 * Created by js982 on 2017/6/2.
 */
public class Bank {
    int Price;

    public synchronized void printmoney(int price){
        Price = Price + price;
        System.out.println("Now the price from "+Thread.currentThread().getName()+" is :"+Price);
    }
}
