package com.telemetron.collector;

import com.telemetron.client.TelemetronMetricsOptions;
import com.telemetron.metric.DataPoint;
import com.telemetron.sender.Sender;
import io.vertx.core.spi.metrics.Metrics;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Contains shared logic for http client and server metrics
 */
abstract class HttpMetrics implements Metrics {

    /**
     * Instance of metrics sender
     */
    private final Sender sender;

    /**
     * Options to be used by the metrics builder
     */
    private final TelemetronMetricsOptions options;

    /**
     * @param sender  responsible for holding the metrics and sending them
     * @param options options to latter be used by the metrics builder
     */
    HttpMetrics(@Nonnull final Sender sender, @Nonnull final TelemetronMetricsOptions options) {
        this.sender = Objects.requireNonNull(sender);
        this.options = Objects.requireNonNull(options);
    }

    /**
     * adds a metric to the sender
     * @param dataPoint to be added
     */
    protected void addMetric(final DataPoint dataPoint) {
        this.sender.addMetric(dataPoint);
    }

    /**
     * @return options for building the datapoint
     */
    protected TelemetronMetricsOptions getOptions() {
        return options;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * @inheritDoc
     */
    @Override
    public void close() {

    }
}