package org.iplab.BankThread;

/**
 * Created by js982 on 2017/6/2.
 */
public class BankThreadApp {
    public static void main(String arg[]){
        Thread z1 = new BankThread();
        Thread z2 = new BankThread();
        z1.start();
        z2.start();
    }
}
