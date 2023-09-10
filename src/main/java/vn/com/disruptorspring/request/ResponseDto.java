package vn.com.disruptorspring.request;

import java.util.Objects;

public class ResponseDto extends MessageDto {

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

    public ResponseDto(String requestId) {
        super();
        this.requestId = requestId;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
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

