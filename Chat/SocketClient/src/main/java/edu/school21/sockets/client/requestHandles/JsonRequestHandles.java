package edu.school21.sockets.client.requestHandles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component("requestHandles")
public class JsonRequestHandles implements RequestHandles {
    @Override
    public Request processing(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        Request request = null;
        try {
            request = objectMapper.readValue(json, Request.class);
//            System.out.println(request);
        } catch (Exception ignore) {}
        return request;
    }
}
