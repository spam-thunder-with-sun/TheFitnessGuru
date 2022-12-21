package it.unitn.disi.sdeproject.beans;

import java.util.Objects;

public class ErrorMessage {
    private String errorMessage;

    public ErrorMessage(){
        this("&nbsp;");
    }

    public ErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage()
    {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return (Objects.equals(this.errorMessage, "&nbsp;")) ? "" : this.errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ErrorMessage that = (ErrorMessage) o;
        return Objects.equals(errorMessage, that.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(errorMessage);
    }
}
