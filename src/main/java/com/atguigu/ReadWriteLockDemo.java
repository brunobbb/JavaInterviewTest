package com.atguigu;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行。
 *
 * 但是如果有一个线程想去写共享资源，就不应该再有其他线程可以对该资源进行读或写
 *
 * 小总结:
 *      读 - 读 => 能共享
 *      读 - 写 => 不能共享
 *      写 - 写 => 不能共享
 *
 *
 * 没有加读写锁之前:
 * 0	正在写操作。。
 * 2	正在写操作。。
 * 1	正在写操作。。
 * 3	正在写操作。。
 * 4	正在写操作。。
 * 0	正在读操作。。
 * 1	正在读操作。。
 * 2	正在读操作。。
 * 3	正在读操作。。
 * 4	正在读操作。。
 * 1	读操作完成	null
 * 4	读操作完成	4
 * 3	读操作完成	null
 * 4	写操作完成
 * 3	写操作完成
 * 0	写操作完成
 * 0	读操作完成	null
 * 2	写操作完成
 * 1	写操作完成
 * 2	读操作完成	null
 *
 * 加了读写锁之后:
 * 0	正在写操作。。
 * 0	写操作完成
 * 1	正在写操作。。
 * 1	写操作完成
 * 2	正在写操作。。
 * 2	写操作完成
 * 3	正在写操作。。
 * 3	写操作完成
 * 4	正在写操作。。
 * 4	写操作完成
 * 0	正在读操作。。
 * 0	读操作完成	0
 * 1	正在读操作。。
 * 1	读操作完成	1
 * 2	正在读操作。。
 * 2	读操作完成	2
 * 3	正在读操作。。
 * 3	读操作完成	3
 * 4	正在读操作。。
 * 4	读操作完成	4
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 0; i < 5; i++) {
            final String str = String.valueOf(i);
            new Thread(()->{
                myCache.write(str,str);
            },String.valueOf(i)).start();
        }
        for (int i = 0; i < 5; i++) {
            final String str = String.valueOf(i);
            new Thread(()->{
                myCache.read(str);
            },String.valueOf(i)).start();
        }

        myCache.clear();
    }
}

class MyCache{
    private volatile Map<String, Object> map = new HashMap<>();
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void write(String key,Object value){
        rwLock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName() + "\t正在写操作。。");
            try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
            map.put(key,value);
            System.out.println(Thread.currentThread().getName() + "\t写操作完成");
        }finally {
            rwLock.writeLock().unlock();
        }

    }

    public void read(String key) {
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t正在读操作。。");
            //try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e) { e.printStackTrace(); }
            Object o = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t读操作完成\t" + o);
        } finally {
            rwLock.readLock().unlock();
        }
    }
    public void clear(){
        map.clear();
    }
}