package vn.com.disruptorspring.dataloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import vn.com.disruptorspring.memdb.Item;
import vn.com.disruptorspring.memdb.ItemRepository;
import vn.com.disruptorspring.utilities.JSON;

import java.util.List;

/**
 * On startup，Put the data in the database，load into memory
 */
@Component
public class ItemDataStartupLoader extends DataStartupLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemDataStartupLoader.class);

    private JdbcTemplate jdbcTemplate;

    private ItemRepository itemRepository;

    @Override
    protected void doLoad() {
        List<Item> items = jdbcTemplate.query("select id, INVENTORY from ITEM", (rs, rowNum) -> new Item(rs.getLong(1), rs.getInt(2)));

        items.forEach(item -> {
            itemRepository.put(item);
            LOGGER.info("Load Item from database: {}", JSON.toJson(item));
        });

    }

    @Override
    public int getPhase() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

}
