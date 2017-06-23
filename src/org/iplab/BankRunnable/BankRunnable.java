package org.iplab.BankRunnable;


/**
 * Created by js982 on 2017/6/2.
 */
public class BankRunnable implements Runnable {
    Bank zhonghang = new Bank();
    @Override
    public void run(){
        for (int i = 0;i < 10;i++){
            zhonghang.printmony(100);
        }
    }
}
