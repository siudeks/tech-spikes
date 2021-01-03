package siudeks;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Yield {
    
    public static <T> Stream<T> of(Consumer<Consumer<T>> yieldBody) {
        return Stream.generate(() -> {
            Supplier<T> result = () -> {
                
            }
            return () -> {
                yieldBody.accept(t);
                return 
            }
        });
    }

}