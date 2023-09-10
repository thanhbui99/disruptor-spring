package vn.com.disruptorspring.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JSON {
    private static ObjectMapper instance;
    public static ObjectMapper getInstance() {
        if (instance == null) {
            instance = new ObjectMapper();
        }
        return instance;
    }
    public static String toJson(Object data)  {
        ObjectMapper mapper = getInstance();
        try {
            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            System.out.println("s");
            return null;
        }
    }

    public static  <T> T fromJson(String json, Class<T> clazz) throws JsonProcessingException {
        ObjectMapper mapper = getInstance();
        return mapper.readValue(json, clazz);
    }

    public static  <T> T fromJson(byte[] data, Class<T> clazz) throws IOException {
        ObjectMapper mapper = getInstance();
        return mapper.readValue(data, clazz);
    }
}
