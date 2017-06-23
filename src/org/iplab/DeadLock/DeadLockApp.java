package org.iplab.DeadLock;

/**
 * Created by js982 on 2017/6/3.
 */
public class DeadLockApp {

    public static void main(String arg[]){
        BargainRunnable bargA = new BargainRunnable(true);
        BargainRunnable bargB = new BargainRunnable(false);
        Thread B1 = new Thread(bargA);
        Thread B2 = new Thread(bargB);
        B1.start();
        B2.start();
    }
}
