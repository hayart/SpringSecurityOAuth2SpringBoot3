package am.developer.outh.payload.response;

import java.util.List;

public class ErrorMessageResponse {

    private List<ErrorMessage> errorMessages;

    public ErrorMessageResponse() {
    }

    public ErrorMessageResponse(List<ErrorMessage> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public List<ErrorMessage> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<ErrorMessage> errorMessages) {
        this.errorMessages = errorMessages;
    }

    @Override
    public String toString() {
        return "ErrorMessageResponse{" +
                "errorMessages=" + errorMessages +
                '}';
    }
}
