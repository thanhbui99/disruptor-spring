package vn.com.disruptorspring.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ResponseDto {
    public static final String SUCCESS = "SUCCESS";
    public static final String PROCESSING = "PROCESSING";
    public static final String FAILED = "FAILED";

    private static final long serialVersionUID = -4690648814874030736L;
    /**
     * The id of the associated RequestDto
     */
    private final String requestId;

    /**
     * wrong information
     */
    protected String errorMessage;

    /**
     * Whether the request was successfully processed
     */
    protected boolean success;

    protected String status;

    public ResponseDto(String requestId) {
        super();
        this.requestId = requestId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResponseDto that = (ResponseDto) o;

        if (success != that.success) return false;
        if (!Objects.equals(requestId, that.requestId)) return false;
        return Objects.equals(errorMessage, that.errorMessage);
    }

    @Override
    public int hashCode() {
        int result = requestId != null ? requestId.hashCode() : 0;
        result = 31 * result + (errorMessage != null ? errorMessage.hashCode() : 0);
        result = 31 * result + (success ? 1 : 0);
        return result;
    }
}

