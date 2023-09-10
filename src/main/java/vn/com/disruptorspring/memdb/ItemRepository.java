package vn.com.disruptorspring.memdb;

/**
 * 商品内存数据库
 */
public interface ItemRepository {

  void put(Item item);

  Item get(Long id);

}
