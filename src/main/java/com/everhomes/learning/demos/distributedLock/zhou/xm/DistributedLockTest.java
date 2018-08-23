package com.everhomes.learning.demos.distributedLock.zhou.xm;

/**
 * @author feiyue
 * @className DistributedLockTest
 * @description
 * @date 2018/8/21
 **/
public class DistributedLockTest {

    public static void main(String[] args){
        Runnable runnable = new Runnable() {
            public void run() {
                DistributedLock lock = null;
                try {
                    lock = new DistributedLock("10.1.10.41:2181", "test1");
                    lock.lock();
                    secskill();
                    System.out.println(Thread.currentThread().getName() + "正在运行");
                } finally {
                    if (lock != null) {
                        lock.unlock();
                    }
                }
            }
        };

        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(runnable);
            t.start();
        }
    }

    static int n = 500;

    public static void secskill() {
        System.out.println(--n);
    }

}
