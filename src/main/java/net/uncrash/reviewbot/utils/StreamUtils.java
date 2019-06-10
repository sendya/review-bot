package net.uncrash.reviewbot.utils;

import net.uncrash.reviewbot.api.CheckedFunction;

import java.util.function.Consumer;

/**
 * @author jjandxa
 */
public class StreamUtils {

    public static <T> Consumer<T> tryCatch(CheckedFunction<T> t) {
        return it -> {
            try {
                t.accept(it);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }
}
