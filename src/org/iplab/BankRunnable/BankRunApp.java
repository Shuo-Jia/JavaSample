package org.iplab.BankRunnable;


/**
 * Created by js982 on 2017/6/2.
 */
public class BankRunApp {

    public static void main(String[] args){
        BankRunnable zhonghangRun = new BankRunnable();
        Thread z1 = new Thread(zhonghangRun);
        Thread z2 = new Thread(zhonghangRun);
        z1.start();
        z2.start();
    }
}
