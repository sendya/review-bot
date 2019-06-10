package net.uncrash.reviewbot.api;

@FunctionalInterface
public interface CheckedFunction<T> {
    void accept(T t) throws Exception;
}