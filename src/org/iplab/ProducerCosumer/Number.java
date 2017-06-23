package org.iplab.ProducerCosumer;

/**
 * Created by js982 on 2017/6/5.
 */
public class Number {

    private int num = 0;
    boolean flag = true;

    public synchronized void produce() throws InterruptedException {
        while (flag) {
            this.wait();
        }
        num ++;
        System.out.println("生产：" + Thread.currentThread().getName() + " : " + num);
        flag = true;
        notifyAll();
    }

    public synchronized void consumer() throws InterruptedException {
        while (!flag) {
            this.wait();
        }
        System.out.println("消耗：" + Thread.currentThread().getName() + " : " + num);
        flag = false;
        notifyAll();
    }
}