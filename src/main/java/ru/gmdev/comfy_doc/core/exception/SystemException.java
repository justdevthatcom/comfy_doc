package ru.gmdev.comfy_doc.core.exception;

public class SystemException extends RuntimeException {
  private String message;

  public SystemException(String s) {
    super(s);
    this.message = s;
  }
  public SystemException(String s, Object... args) {
    this(parseMessage(s, args));
  }

  private static String parseMessage(String s, Object... args) {
    return args != null && args.length > 0 ? String.format(s, args) : s;
  }
}
