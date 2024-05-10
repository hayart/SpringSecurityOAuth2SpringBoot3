package am.developer.outh.payload.response;

import java.util.Objects;


public class ErrorMessage {
    private String errorCode;
    private String errorLabel;

    public ErrorMessage() {
    }

    public ErrorMessage(String errorCode, String errorLabel) {
        this.errorCode = errorCode;
        this.errorLabel = errorLabel;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorLabel() {
        return errorLabel;
    }

    public void setErrorLabel(String errorLabel) {
        this.errorLabel = errorLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorMessage that = (ErrorMessage) o;
        return Objects.equals(errorCode, that.errorCode) && Objects.equals(errorLabel, that.errorLabel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorCode, errorLabel);
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "errorCode='" + errorCode + '\'' +
                ", errorLabel='" + errorLabel + '\'' +
                '}';
    }
}
