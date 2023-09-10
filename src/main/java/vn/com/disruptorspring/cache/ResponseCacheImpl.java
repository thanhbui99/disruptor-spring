package vn.com.disruptorspring.cache;


import org.javatuples.Pair;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.com.disruptorspring.model.response.ResponseDto;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class ResponseCacheImpl implements ResponseCache {
    // request_id -> response, expire time
    private static final ConcurrentMap<String, Pair<ResponseDto, Long>> RESPONSE_MAP = new ConcurrentHashMap<>();

    private static final Long ONE_MINUTE = 1000 * 60L;

    @Override
    public void put(ResponseDto responseDto) {
        if (responseDto == null) return;
        RESPONSE_MAP.put(responseDto.getRequestId(), new Pair<>(responseDto, System.currentTimeMillis() + ONE_MINUTE));
    }

    @Override
    public ResponseDto getAndRemove(String requestId) {
        Pair<ResponseDto, Long> pair = RESPONSE_MAP.remove(requestId);
        return pair == null ? null : pair.getValue0();
    }

    @Override
    public ResponseDto get(String requestId) {
        Pair<ResponseDto, Long> pair = RESPONSE_MAP.get(requestId);
        return pair == null ? null : pair.getValue0();
    }

    @Override
    public void remove(String requestId) {
        RESPONSE_MAP.remove(requestId);
    }

    /**
     * Clear cache regularly, 5 minutes
     */
    @Scheduled(fixedDelay = 1000 * 60L * 5)
    public void flush() {

        Long now = System.currentTimeMillis();

        RESPONSE_MAP.keySet().stream()
                .filter(s -> RESPONSE_MAP.get(s).getValue1() < now)
                .forEach(RESPONSE_MAP::remove);

    }

}
