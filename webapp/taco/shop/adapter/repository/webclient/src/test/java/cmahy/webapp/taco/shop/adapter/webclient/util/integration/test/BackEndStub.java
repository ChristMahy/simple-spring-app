package cmahy.webapp.taco.shop.adapter.webclient.util.integration.test;

import okhttp3.mockwebserver.MockWebServer;

public enum BackEndStub {

    INSTANCE;

    private MockWebServer mockBackEnd;

    BackEndStub() {
        mockBackEnd = new MockWebServer();
    }

    public MockWebServer getMockBackEnd() {
        return mockBackEnd;
    }
}
