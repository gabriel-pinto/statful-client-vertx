package com.telemetron.metric;

import com.telemetron.client.TelemetronMetricsOptions;

/**
 * Representation of an HttpClient DataPoint. Holds the that of a metric and builds
 */
public abstract class HttpDataPoint implements DataPoint {

    /**
     * Identifies if the metric is from a http server or http client
     */
    public enum Type {
        /**
         * To tag server metrics
         */
        SERVER("server"),
        /**
         * To tag client metrics
         */
        CLIENT("client");

        /**
         * String identifier of the type
         */
        private final String value;

        /**
         * @param value identifier of the type
         */
        Type(final String value) {
            this.value = value;
        }
    }

    /**
     * constant to transform milliseconds in unix timestamp
     */
    private static final long TIMESTAMP_DIVIDER = 1000L;

    /**
     * Telemetron options to be used when building the metric line
     */
    private final TelemetronMetricsOptions options;

    /**
     * Metric name
     */
    private final String metricName;

    /**
     * Name of the operation that you are tracking
     */
    private final String name;

    /**
     * Representation of the http verb request
     */
    private final String verb;

    /**
     * Duration of the request
     */
    private final long duration;

    /**
     * Http code to be added as tag
     */
    private final int responseCode;

    /**
     * Timestamp of metric creation
     */
    private final long unixTimeStamp;

    /**
     * Source of metric being collected (server vs client)
     */
    private final Type type;

    /**
     * constructor for a HttpClient Timer based metric, will calculate the unix timestamp of the metric on creation
     *
     * @param options      Telemetron options to be used when building the metric line
     * @param metricName   name of the metric
     * @param name         Name of the operation that you are tracking
     * @param httpVerb     Representation of the http verb request
     * @param duration     Duration of the request
     * @param responseCode Http code to be added as tag
     * @param type         if this metric belongs to http server or client
     */
    public HttpDataPoint(final TelemetronMetricsOptions options, final String metricName, final String name, final String httpVerb, final long duration,
                         final int responseCode,
                         final Type type) {

        this.options = options;
        this.metricName = metricName;
        this.name = name;
        this.verb = httpVerb;
        this.duration = duration;
        this.responseCode = responseCode;
        this.unixTimeStamp = this.getUnixTimeStamp();
        this.type = type;
    }

    protected MetricLineBuilder buildMetricLine() {

        return new MetricLineBuilder()
                .withPrefix(this.options.getPrefix())
                .withNamespace(this.options.getNamespace())
                .withMetricType("timer")
                .withMetricName(this.metricName)
                .withTag("transport", "http")
                .withTag("type", this.type.value)
                .withTag("verb", this.verb)
                .withTag("statusCode", String.valueOf(this.responseCode))
                .withValue(String.valueOf(this.duration))
                .withTimestamp(this.unixTimeStamp)
                .withAggregations(this.options.getTimerAggregations())
                .withAggregationFrequency(this.options.getTimerFrequency())
                .withApp(this.options.getApp());
    }

    /**
     * @return unix timestamp value
     */
    private long getUnixTimeStamp() {
        return System.currentTimeMillis() / TIMESTAMP_DIVIDER;
    }

    /**
     * @return string with the name of the operation that you are tracking
     */
    public String getName() {
        return name;
    }
}