package siudeks;

import org.junit.jupiter.api.Test;

public class YieldTests {
    @Test
    public void myTest() {
        Yield
            .of(next -> {
                for (int i = 0; i < 10; i++ ) 
                    for (int j = i; j < 10; j++ ) 
                        next.accept(i*j); })
            .count();
        
    }
}