package vn.com.disruptorspring.disruptor.command.item;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import vn.com.disruptorspring.disruptor.command.base.CommandExecutor;
import vn.com.disruptorspring.utilities.JSON;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ItemAmountUpdateCommandExecutor implements CommandExecutor<ItemAmountUpdateCommandBuffer> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemAmountUpdateCommandExecutor.class);

    private static final String SQL = "UPDATE ITEM SET INVENTORY = ? WHERE ID = ?";

    private final JdbcTemplate jdbcTemplate;

    public ItemAmountUpdateCommandExecutor(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void execute(ItemAmountUpdateCommandBuffer commandBuffer) {


        List<ItemAmountUpdateCommand> commands = commandBuffer.get();
        if (CollectionUtils.isEmpty(commands)) {
            return;
        }

        List<Object[]> args = commands.stream().map(cmd -> new Object[]{cmd.getInventory(), cmd.getItemId()}).collect(toList());
        try {

            jdbcTemplate.batchUpdate(SQL, args);
            LOGGER.info("Executed ItemAmount thread: {} args {}", Thread.currentThread().getName(), JSON.toJson(args));

        } catch (Exception e) {

            commands.forEach(command -> LOGGER.error("Failed {}", JSON.toJson(command)));
            LOGGER.error(ExceptionUtils.getStackTrace(e));

        }
    }

}
