package vn.com.disruptorspring.cache;


import vn.com.disruptorspring.model.response.ResponseDto;

/**
 * Response result caching
 */
public interface ResponseCache {

    /**
     * Put the response result
     *
     * @param responseDto
     */
    void put(ResponseDto responseDto);

    /**
     * Get response result，and remove it from cache
     *
     * @param requestId
     * @return
     */
    ResponseDto getAndRemove(String requestId);

    ResponseDto get(String requestId);

    void remove(String requestId);

}
