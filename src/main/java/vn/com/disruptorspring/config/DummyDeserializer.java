package vn.com.disruptorspring.config;

import org.apache.kafka.common.serialization.Deserializer;
import vn.com.disruptorspring.model.Dummy;
import vn.com.disruptorspring.utilities.JSON;

import java.io.IOException;

public class DummyDeserializer implements Deserializer<Dummy> {

    @Override
    public Dummy deserialize(String topic, byte[] data) {
        try {
            return JSON.fromJson(data, Dummy.class);
        } catch (IOException e) {
            return null;
        }
    }
}
