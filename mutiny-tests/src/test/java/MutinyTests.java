import org.junit.jupiter.api.Test;

import io.smallrye.mutiny.Uni;

public class MutinyTests {
    
    @Test
    public void should_default_group_of_unis() {
       var a = Uni.join().all().andCollectFailures();
    }
}
