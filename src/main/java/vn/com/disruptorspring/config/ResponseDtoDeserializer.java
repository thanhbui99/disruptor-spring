package vn.com.disruptorspring.config;

import org.apache.kafka.common.serialization.Deserializer;
import vn.com.disruptorspring.model.response.ResponseDto;
import vn.com.disruptorspring.utilities.JSON;

import java.io.IOException;

public class ResponseDtoDeserializer implements Deserializer<ResponseDto> {

    @Override
    public ResponseDto deserialize(String topic, byte[] data) {
        try {
            return JSON.fromJson(data, ResponseDto.class);
        } catch (IOException e) {
            return null;
        }
    }
}
