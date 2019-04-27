package com.atguigu;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class ContainerNotSafeDemo {
    public static void main(String[] args) {

        List list = new CopyOnWriteArrayList();

        for (int i = 0; i < 4; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(list);
            },"PleaseInputThreadName").start();
        }
    }
}
