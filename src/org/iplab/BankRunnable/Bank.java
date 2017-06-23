package org.iplab.BankRunnable;

/**
 * Created by js982 on 2017/6/2.
 */
public class Bank {
    private int Price;

    public synchronized void printmony(int price){
        Price = Price + price;
        System.out.println("Now the price from "+Thread.currentThread().getName()+" is :"+Price);
    }
}
