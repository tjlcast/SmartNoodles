package com.tjlcast.server.utils.cache;

/**
 * Created by tangjialiang on 2017/12/19.
 */
public interface Computable<A, V> {
    V compute(A arg) throws InterruptedException ;
}
