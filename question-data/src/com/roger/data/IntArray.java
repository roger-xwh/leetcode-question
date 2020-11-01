package com.roger.data;

import java.security.SecureRandom;

public class IntArray {
    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        String[] array = new String[20];
        for (int i = 0; i < array.length; i++) {
            array[i] = String.valueOf(random.nextInt(array.length * 10) + 1);
        }
        System.out.println(String.join(",", array));
    }
}
