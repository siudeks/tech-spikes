package net.siudek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements ApplicationRunner {

    @Autowired
    private MySender source;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        while (true) {
            String value = System.console().readLine();
            source.sendMessage(value);
        }

    }

}