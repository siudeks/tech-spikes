package net.siudek;

import com.microsoft.azure.spring.integration.core.AzureHeaders;
import com.microsoft.azure.spring.integration.core.api.Checkpointer;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Header;

@EnableBinding(MySink.class)
public class MyListener {

    @StreamListener(MySink.INPUT)
    public void handleMessage(final String message, final @Header(AzureHeaders.CHECKPOINTER) Checkpointer checkpointer) {
        checkpointer.success().handle((r, ex) -> {
            if (ex == null) {
                System.out.println(String.format("Hello %s", message));
            }
            return null;
        });
    }
}