package com.project.main;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class TestOther {

    public static void main(String[] args) {
        String a = "aabb";
        System.out.println(addressOf(a));
        System.gc();
        String b = new String("aa")+new String("bb");
        System.out.println(addressOf(a) + "   " +addressOf(b));
        System.out.println(a==b);
        System.out.println(a.equals(b));

        //false ??
//        System.out.println(a==b + a.equals(b));
    }

    private static Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取内存地址
    public static long addressOf(Object o){

        Object[] array = new Object[] { o };

        long baseOffset = unsafe.arrayBaseOffset(Object[].class);
        int addressSize = unsafe.addressSize();
        long objectAddress;
        switch (addressSize) {
            case 4:
                objectAddress = unsafe.getInt(array, baseOffset);
                break;
            case 8:
                objectAddress = unsafe.getLong(array, baseOffset);
                break;
            default:
                throw new Error("unsupported address size: " + addressSize);
        }
        return (objectAddress);
    }

}
