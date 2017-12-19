package com.tjlcast.server.utils.cache;

import java.util.concurrent.*;

/**
 * Created by tangjialiang on 2017/12/19.
 */
public class Memoizer<A, V> implements Computable<A, V> {
    private final ConcurrentMap<A, Future<V>> cache ;
    private final Computable<A, V> c ;


    public Memoizer(Computable<A, V> c) {
        this.c = c;
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        while(true) {
            Future<V> f = cache.get(arg) ;
            if (f == null) {
                Callable<V> eval = new Callable<V>() {
                    public V call() throws InterruptedException {
                        return c.compute(arg);
                    }
                };
                FutureTask<V> ft = new FutureTask<V>(eval) ;
                f = cache.putIfAbsent(arg, ft) ;
                if (f == null) { f = ft; ft.run(); }
            }

            try {
                return f.get();
            } catch (CancellationException e) {
                cache.remove(arg, f) ;
            } catch (ExecutionException e) {
                // throw launderThrowable(e.getCause()) ;
            }
        }
    }
}
