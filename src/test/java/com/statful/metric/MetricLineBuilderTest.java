package com.statful.metric;

import com.google.common.collect.Lists;
import com.statful.client.Aggregation;
import com.statful.client.AggregationFreq;
import com.statful.client.MetricType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class MetricLineBuilderTest {

    private MetricLineBuilder victim;

    @Before
    public void setup() {
        victim = new MetricLineBuilder();
        victim.withPrefix("prefix");
        victim.withNamespace("namespace");
        victim.withMetricType(MetricType.TIMER);
        victim.withMetricName("execution");
        victim.withTag("tagName", "tagValue");
        victim.withTimestamp(1);
        victim.withValue("value");
        victim.withSampleRate(100);
    }

    @Test
    public void testBuildWithoutAggregations() {
        String result = victim.build();
        String expected = "prefix.namespace.timer.execution,tagName=tagValue value 1 100";
        assertEquals(expected, result);
    }

    @Test
    public void testBuildWithoutAggregationsWithoutTags() {

        victim = new MetricLineBuilder();
        victim.withPrefix("prefix");
        victim.withNamespace("namespace");
        victim.withMetricType(MetricType.TIMER);
        victim.withMetricName("execution");
        victim.withTimestamp(1);
        victim.withValue("value");
        victim.withSampleRate(100);

        String result = victim.build();
        String expected = "prefix.namespace.timer.execution value 1 100";
        assertEquals(expected, result);
    }

    @Test
    public void testBuildWithoutAggregationsWithFrequencies() {

        victim.withAggregationFrequency(AggregationFreq.FREQ_120);

        String result = victim.build();
        String expected = "prefix.namespace.timer.execution,tagName=tagValue value 1 100";
        assertEquals(expected, result);
    }

    @Test
    public void testBuildWithAggregationsAndFrequencies() {

        victim.withAggregations(Lists.newArrayList(Aggregation.AVG));
        victim.withAggregationFrequency(AggregationFreq.FREQ_120);

        String result = victim.build();
        String expected = "prefix.namespace.timer.execution,tagName=tagValue value 1 avg,120 100";
        assertEquals(expected, result);
    }

    @Test
    public void testBuildWithApplication() {
        victim.withApp("test_app");

        String result = victim.build();
        String expected = "prefix.namespace.timer.execution,app=test_app,tagName=tagValue value 1 100";
        assertEquals(expected, result);
    }

    @Test
    public void testBuildWithSampleRate() {
        victim.withSampleRate(50);

        String result = victim.build();
        String expected = "prefix.namespace.timer.execution,tagName=tagValue value 1 50";
        assertEquals(expected, result);
    }
}
