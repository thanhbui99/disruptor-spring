package vn.com.disruptorspring.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import vn.com.disruptorspring.cache.mem.ResponseCache;
import vn.com.disruptorspring.disruptor.producer.RequestDtoEventProducer;
import vn.com.disruptorspring.memdb.Item;
import vn.com.disruptorspring.model.request.RequestDto;
import vn.com.disruptorspring.model.response.ResponseDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CommandService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandService.class);

    private final ResponseCache responseCache;
    private final RequestDtoEventProducer requestDtoEventProducer;
    private final JdbcTemplate jdbcTemplate;


    public String doRequest(RequestDto requestDto) {
        try {
            responseCache.put(new ResponseDto(requestDto.getId(), null, false, ResponseDto.PROCESSING));
            requestDtoEventProducer.onData(requestDto);
            return requestDto.getId();
        } catch (Exception e) {
            LOGGER.error(ExceptionUtils.getStackTrace(e));
            throw new RuntimeException(e);
        }
    }

    public ResponseDto getResponse(String requestId) {
        ResponseDto responseDto = responseCache.get(requestId);
        if (responseDto != null && ResponseDto.SUCCESS.equals(responseDto.getStatus())){
            responseCache.remove(requestId);
        }
        return responseDto;
    }

    public List<Item> getOrdersByUser(String userId) {
        String sql = "select o.ITEM_ID as id, i.AMOUNT as amount,i.INVENTORY as inventory, count(o.ITEM_ID) as sum\n" +
                "from ITEM i\n" +
                "         inner join ITEM_ORDER o on i.id = o.ITEM_ID\n" +
                "where USER_ID = ? group by i.AMOUNT, o.ITEM_ID, i.INVENTORY";
        List<Item> itemId = jdbcTemplate.query(sql, new RowMapper<Item>() {
            @Override
            public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Item(rs.getLong("id"), rs.getInt("amount"), null, rs.getInt("sum"));
            }
        }, userId);
        return itemId;
    }
}
