package cmahy.simple.spring.webapp.shell.client.impl.adapter.service.io;

import cmahy.simple.spring.webapp.shell.client.impl.application.service.io.PrintStream;
import org.springframework.stereotype.Service;

@Service
public class PrintStreamImpl implements PrintStream {

    @Override
    public void write(String text) {

        System.out.println(text);

    }
}
