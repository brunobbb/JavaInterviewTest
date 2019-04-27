package com.atguigu;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1. 验证volatile的可见性
 *  1.1 假如 int number = 0； number变量之前没有添加volatile关键字修饰
 *  1.2 添加了volatile可以增加可见性
 * 2. 验证volatile不保证原子性
 *  2.1 原子性指的是什么意思？
 *      不可分割，完整性，即某个线程正在做某个具体业务时，中间不可以被加塞或者被分割，需要整体完整，要么同时成功，要么同时失败
 *
 *  2.2 volatile不保证原子性 代码演示
 *
 *  2.3 why 查看字节码文件，++分为三个步骤，获取/增加/赋值，在最后赋值时，由于那秒级别的延迟，会导致将本线程最新的值直接覆盖其他最新值
 *
 *  2.4 如何解决原子性
 *      1. 添加sync
 */
public class VolatileDemo {
    public static void main(String[] args) {
        //seeOKVolatileDemo();
        MyData myData = new MyData();

        for (int i = 0; i < 20; i++) {
            new Thread(()->{
                for (int j = 0; j < 1000; j++) {
                    myData.addPlusPlus();
                    myData.addAtomic();
                }
            },"a" + i).start();
        }

        //需要等待20个线程都全部计算玩，再用main方法取得最终的结果值
        //当当前活跃线程大于两个 则代表计算还未完成
        while(Thread.activeCount() > 2){
            //线程礼让 不占用资源
            Thread.yield();
        }

        System.out.println(Thread.currentThread().getName() + "\t" + myData.number);
        System.out.println(Thread.currentThread().getName() + "\t" + myData.atomicInteger);
    }

    private static void seeOKVolatileDemo() {
        MyData myData = new MyData();
        new Thread(()->{
            System.out.println(Thread.currentThread().getName() + " come in");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            myData.addTo60();
            System.out.println(Thread.currentThread().getName() + " updated data " + myData.number);
        },"a").start();

        //如果main线程获取number的值已变为60 则会跳出循环
        while (myData.number == 0){

        }
        System.out.println(Thread.currentThread().getName() + " mission is over");
    }
}

class MyData{
    //如果不加volatile main线程不会知道number值已作出修改
    volatile int number = 0;
    public void addTo60(){
        this.number = 60;
    }
    //注意，此时number前面是加了volatile关键字的，不保证原子性
    public void addPlusPlus(){
        this.number ++;
    }
    AtomicInteger atomicInteger = new AtomicInteger();
    public void addAtomic(){
        atomicInteger.getAndIncrement();
    }
}
