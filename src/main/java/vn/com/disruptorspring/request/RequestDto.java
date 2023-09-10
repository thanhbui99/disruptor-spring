package vn.com.disruptorspring.request;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Request to purchase items
 */
public class RequestDto extends MessageDto {

    private static final long serialVersionUID = 5515305970509119810L;
    /**
     * Product ID
     */
    private final Long itemId;

    /**
     * User ID
     */
    private final String userId;

    public RequestDto(Long itemId, String userId) {
        super();
        this.itemId = itemId;
        this.userId = userId;
    }

    public Long getItemId() {
        return itemId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestDto that = (RequestDto) o;

        if (itemId != null ? !itemId.equals(that.itemId) : that.itemId != null) return false;
        return userId != null ? userId.equals(that.userId) : that.userId == null;
    }

    @Override
    public int hashCode() {
        int result = itemId != null ? itemId.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("itemId", itemId)
                .append("userId", userId)
                .toString();
    }
}
