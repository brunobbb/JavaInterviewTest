package com.atguigu;

public class SingletonDemo {
    //禁止指令重排
    public static volatile SingletonDemo instance = null;

    public SingletonDemo(){
        System.out.println(Thread.currentThread().getName() + "\t 被创造了");
    }

    //也可使用synchronized 但会对该对象整个方法 加锁 太重
    //使用 DCL (Double Check Lock双端检锁机制): 在加锁的前后都判断一下
    //
    public static SingletonDemo getInstance(){
        if (instance == null){
            synchronized (SingletonDemo.class){
                if (instance == null){
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        //单线程模式下的操作
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());
//        System.out.println(SingletonDemo.getInstance() == SingletonDemo.getInstance());


        //多线程模式下的操作
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                SingletonDemo.getInstance();
            },String.valueOf(i)).start();
        }
    }
}
