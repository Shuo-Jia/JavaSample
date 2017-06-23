package org.iplab.BankThread;

/**
 * Created by js982 on 2017/6/2.
 */
public class BankThread extends Thread {

    Bank zhonghang = new Bank();
    @Override
    public void run(){
        for (int i = 0; i < 10; i++){
            zhonghang.printmoney(100);
        }
    }
}
