package com.statful.sender;

import com.statful.client.StatfulMetricsOptions;
import com.statful.client.Transport;
import io.vertx.core.Context;
import io.vertx.core.Vertx;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class SenderFactoryTest {

    private SenderFactory victim;

    private Context context;
    private Vertx vertx;

    @Before
    public void setup() {
        context = mock(Context.class);
        vertx = mock(Vertx.class);

        victim = new SenderFactory();
    }

    @SuppressWarnings("all")
    @Test(expected = NullPointerException.class)
    public void testNullVertx() {
        victim.create(null, null, null);
    }

    @SuppressWarnings("all")
    @Test(expected = NullPointerException.class)
    public void testNullContext() {
        victim.create(vertx, null, null);
    }

    @SuppressWarnings("all")
    @Test(expected = NullPointerException.class)
    public void testNullOptions() {
        victim.create(vertx, context, null);
    }

    @Test
    public void testHttpSenderCreation() {
        StatfulMetricsOptions options = new StatfulMetricsOptions();
        options.setTransport(Transport.HTTP);

        assertTrue(victim.create(vertx, context, options) instanceof HttpSender);
    }

    @Test
    public void testSenderCreationTransport() {
        StatfulMetricsOptions options = new StatfulMetricsOptions();
        options.setTransport(Transport.UDP);

        assertTrue(victim.create(vertx, context, options) instanceof UDPSender);
    }
}