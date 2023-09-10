package vn.com.disruptorspring.disruptor.command.order;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import vn.com.disruptorspring.disruptor.command.base.CommandExecutor;
import vn.com.disruptorspring.utilities.JSON;
import vn.com.disruptorspring.utilities.StrongUuidGenerator;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class OrderInsertCommandExecutor implements CommandExecutor<OrderInsertCommandBuffer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderInsertCommandExecutor.class);

    private static final String SQL = "INSERT INTO homedb.ITEM_ORDER (ID, ITEM_ID, USER_ID) VALUES (?, ?, ?)";

    private final JdbcTemplate jdbcTemplate;

    public OrderInsertCommandExecutor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void execute(OrderInsertCommandBuffer commandBuffer) {

        List<OrderInsertCommand> commands = commandBuffer.get();
        if (CollectionUtils.isEmpty(commands)) {
            return;
        }

        List<Object[]> args = commands.stream().map(cmd -> new Object[]{StrongUuidGenerator.getNextId(), cmd.getItemId(), cmd.getUserId()}).collect(toList());

        try {
            jdbcTemplate.batchUpdate(SQL, args);
            LOGGER.info("Executed OrderInsert thread: {} size {}", Thread.currentThread().getName(), commands.size());

        } catch (Exception e) {
            commands.forEach(command -> LOGGER.error("Failed {}", JSON.toJson(command)));
            LOGGER.error(ExceptionUtils.getStackTrace(e));
        }
    }
}
