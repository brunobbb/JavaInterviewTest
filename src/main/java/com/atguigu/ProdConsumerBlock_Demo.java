package com.atguigu;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ProdConsumerBlock_Demo {
    public static void main(String[] args) {
        MyBlockData myBlockData = new MyBlockData(new ArrayBlockingQueue(3));

        new Thread(()->{
            try {
                myBlockData.ProdData();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"aa").start();

        new Thread(()->{
            try {
                myBlockData.Consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"bb").start();

        try { TimeUnit.SECONDS.sleep(4); } catch (InterruptedException e) { e.printStackTrace(); }

        myBlockData.stop();
    }
}

class MyBlockData{
    //使用volatile保证FALG在多线程下的可见性
    private volatile boolean FALG = true;
    //使用原子整型 确保线程安全
    private AtomicInteger atomicInteger = new AtomicInteger();


    //*********************非常重要！*********************
    private BlockingQueue<String> blockingQueue;
    //以接口创建对象，再用构造方法将具体实现类传入，增强其扩展性！
    public MyBlockData(BlockingQueue blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void ProdData() throws InterruptedException {
        String data;
        boolean resFlag;
        System.out.println(Thread.currentThread().getName() + "\t 生产者启动");
        while (FALG){
            //i++
            data = atomicInteger.incrementAndGet() + "";
            //向阻塞队列中每隔两秒生产数据
            resFlag = blockingQueue.offer(data, 2, TimeUnit.SECONDS);
            if (resFlag){
                System.out.println(Thread.currentThread().getName() + "\t正在生产数据 " + data + "成功");
            }else {
                System.out.println(Thread.currentThread().getName() + "\t正在生产数据 " + data + "失败");
            }
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        System.out.println("暂停了，生产者准备停止了");
    }

    public void Consumer() throws InterruptedException {
        String result;
        System.out.println(Thread.currentThread().getName() + "\t 消费者启动");
        while (FALG){
            result = blockingQueue.poll(2, TimeUnit.SECONDS);
            if (result == null || result.trim() == ""){
                FALG = false;
                System.out.println(Thread.currentThread().getName() + "\t 获取数据失败，等待两秒");
                //return;
            }else{
                System.out.println(Thread.currentThread().getName() + "\t正在消费数据 " + result);
            }
        }
        System.out.println("暂停了，消费者准备停止了");
    }
    public void stop(){
        FALG = false;
    }
}