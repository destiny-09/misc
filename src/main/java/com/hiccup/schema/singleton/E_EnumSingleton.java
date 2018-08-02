package com.hiccup.schema.singleton;

/**
 * 单例模式：枚举方式
 * （JVM会保证enum不能被反射并且构造器方法只执行一次，因此该单例是线程安全的）
 *
 * @author wenhy
 * @date 2018/1/2
 */
public class E_EnumSingleton {

    private E_EnumSingleton() { }

    private enum InnerEnum {
        /**
         * 站位枚举值
         */
        enumFactory;
        private E_EnumSingleton instance;
        private InnerEnum() {
            instance = new E_EnumSingleton();
        }
        public E_EnumSingleton getInstance() {
            return instance;
        }
    }

    public static E_EnumSingleton getInstance() {
        return InnerEnum.enumFactory.getInstance();
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                E_EnumSingleton e1 = E_EnumSingleton.getInstance();
                System.out.println(e1.hashCode());
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                E_EnumSingleton e2 = E_EnumSingleton.getInstance();
                System.out.println(e2.hashCode());
            }
        });
        t1.start();
        t2.start();
    }

}
