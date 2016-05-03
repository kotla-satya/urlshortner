package com.demo.urlshortner.util;

/**
 * Created by skotla on 4/17/16.
 */
public class Base62Util {

    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static final int BASE = ALPHABET.length();

    public static String fromBase10(long i) {
        StringBuilder sb = new StringBuilder("");
        while (i > 0) {
            i = fromBase10(i, sb);
        }
        return sb.reverse().toString();
    }

    private static long fromBase10(long i, final StringBuilder sb) {
        long rem = i % BASE;
        sb.append(ALPHABET.charAt((int) rem));
        return i / BASE;
    }

    public static long toBase10(String str) {
        return toBase10(new StringBuilder(str).reverse().toString().toCharArray());
    }

    private static long toBase10(char[] chars) {
        long n = 0;
        for (int i = chars.length - 1; i >= 0; i--) {
            n += toBase10(ALPHABET.indexOf(chars[i]), i);
        }
        return n;
    }

    private static long toBase10(int n, int pow) {
        return n * (long) Math.pow(BASE, pow);
    }

    public static void main(String[] args) {
        System.out.print(Base62Util.fromBase10(1));
    }
}
