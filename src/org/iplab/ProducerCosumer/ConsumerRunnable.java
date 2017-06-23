package org.iplab.ProducerCosumer;

/**
 * Created by js982 on 2017/6/5.
 */
public class ConsumerRunnable implements Runnable {

    private Number number;

    public ConsumerRunnable(Number num) {
        this.number = num;
    }

    @Override
    public void run() {
            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    number.consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }
}

