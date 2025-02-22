package cmahy.simple.spring.webapp.resource.impl.adapter.api.stream.visitor;

import org.springframework.stereotype.Service;

@Service
public class StreamVisitorFactory {

    public StreamVisitorImpl create() {
        return new StreamVisitorImpl();
    }
}
