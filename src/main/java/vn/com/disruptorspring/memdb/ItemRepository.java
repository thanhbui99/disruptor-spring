package vn.com.disruptorspring.memdb;

/**
 * Product memory database
 */
public interface ItemRepository {

    void put(Item item);

    Item get(Long id);

}
