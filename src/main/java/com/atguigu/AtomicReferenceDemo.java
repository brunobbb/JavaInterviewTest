package com.atguigu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 对某个类进行原子引用
 */
public class AtomicReferenceDemo {
    public static void main(String[] args) {
        User zhangsan = new User("zhangsan", 22);
        User lisi = new User("lisi", 24);

        AtomicReference<User> atomicReference = new AtomicReference<User>();

        atomicReference.set(zhangsan);

        System.out.println(atomicReference.compareAndSet(zhangsan, lisi) + "\t" + atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(zhangsan, lisi) + "\t" + atomicReference.get().toString());
    }
}

@AllArgsConstructor
@Data
@ToString
class User{
    String name;
    int age;
}

