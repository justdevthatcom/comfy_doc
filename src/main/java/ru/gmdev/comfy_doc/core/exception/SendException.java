package ru.gmdev.comfy_doc.core.exception;

public class SendException extends RuntimeException {

  private String message;
  private boolean fatal;

  public boolean isFatal() {
    return this.fatal;
  }

  public SendException(boolean fatal, Throwable inner) {
    this.message = inner.getMessage();
    this.fatal = fatal;
  }

  public SendException(Throwable inner) {
    super("Error while send: " + inner.getMessage(), inner);
  }
}
