package com.tjlcast.server.utils.cache;

import java.math.BigInteger;

/**
 * Created by tangjialiang on 2017/12/19.
 */
public class ExpensiveFunction implements Computable<String, BigInteger> {
    @Override
    public BigInteger compute(String arg) throws InterruptedException {

        Thread.sleep(1000);

        return new BigInteger(arg) ;
    }
}
