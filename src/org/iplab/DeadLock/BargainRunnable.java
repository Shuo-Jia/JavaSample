package org.iplab.DeadLock;

/**
 * Created by js982 on 2017/6/3.
 */
public class BargainRunnable implements Runnable{
    boolean Flag;

    public BargainRunnable(boolean flag){
        this.Flag = flag;
    }
    @Override
    public void run(){
        if(Flag){
            while (true) {
                synchronized (BargainLock.locka) {
                    System.out.println(Thread.currentThread().getName() + " I have BargainLock.locka to give you price");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (BargainLock.lockb){
                        System.out.println(Thread.currentThread().getName() + " I have BargainLock.lockb to give you Goods");
                    }
                }
            }
        }
        else {
            while (true) {
                synchronized (BargainLock.lockb){
                    System.out.println(Thread.currentThread().getName() + " I have BargainLock.lockb to give you price");
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (BargainLock.locka) {
                        System.out.println(Thread.currentThread().getName() + " I have BargainLock.locka to give you price");
                    }
                }
            }
        }
    }
}
