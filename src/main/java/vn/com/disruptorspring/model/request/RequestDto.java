package vn.com.disruptorspring.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import vn.com.disruptorspring.utilities.StrongUuidGenerator;

import java.util.Objects;

/**
 * Request to purchase items
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {

    private static final long serialVersionUID = 5515305970509119810L;

    /**
     * Product ID
     */
    private Long itemId;

    /**
     * User ID
     */
    private String userId;

    /**
     * Request ID
     */
    private String id;

    public RequestDto(Long itemId, String userId) {
        this.itemId = itemId;
        this.userId = userId;
        this.id = StrongUuidGenerator.getNextId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestDto that = (RequestDto) o;

        if (!Objects.equals(itemId, that.itemId)) return false;
        return Objects.equals(userId, that.userId);
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
