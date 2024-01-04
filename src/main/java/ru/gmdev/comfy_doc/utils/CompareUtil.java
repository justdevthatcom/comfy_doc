package ru.gmdev.comfy_doc.utils;
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigDecimal;

public abstract class CompareUtil {
  public CompareUtil() {
  }

  public static int compare(short a, short b) {
    return Short.compare(a, b);
  }

  public static int compare(int a, int b) {
    return Integer.compare(a, b);
  }

  public static int compare(long a, long b) {
    return Long.compare(a, b);
  }

  public static int compare(float a, float b) {
    return Float.compare(a, b);
  }

  public static int compare(double a, double b) {
    return Double.compare(a, b);
  }

  public static <T extends Comparable<? super T>> int compare(T o1, T o2) {
    return compare(o1, o2, true);
  }

  public static <T extends Comparable<? super T>> int compare(T o1, T o2, boolean nullsFirst) {
    if (o1 == null) {
      return o2 == null ? 0 : (nullsFirst ? -1 : 1);
    } else if (o2 == null) {
      return nullsFirst ? 1 : -1;
    } else {
      return o1.compareTo(o2);
    }
  }

  public static <T extends Comparable<? super T>> boolean equals(T o1, T o2) {
    return compare(o1, o2) == 0;
  }

  public static boolean equals(Object o1, Object o2) {
    if (o1 == o2) {
      return true;
    } else if (o1 != null && o2 != null) {
      return o1.getClass().isArray() && o2.getClass().isArray() ? equalsArrays(o1, o2) : o1.equals(o2);
    } else {
      return false;
    }
  }

  public static <T extends Comparable<? super T>> T max(T o1, T o2, boolean nullsFirst) {
    return compare(o1, o2, !nullsFirst) > 0 ? o1 : o2;
  }

  public static <T extends Comparable<? super T>> T min(T o1, T o2, boolean nullsFirst) {
    return compare(o1, o2, nullsFirst) > 0 ? o2 : o1;
  }

  private static boolean equalsArrays(Object a1, Object a2) {
    int length = Array.getLength(a1);
    if (Array.getLength(a2) != length) {
      return false;
    } else {
      int i = 0;

      while(true) {
        if (i >= length) {
          return true;
        }

        Object o1 = Array.get(a1, i);
        Object o2 = Array.get(a2, i);
        if (o1 == null) {
          if (o2 != null) {
            break;
          }
        } else if (!o1.equals(o2)) {
          break;
        }

        ++i;
      }

      return false;
    }
  }

  public static boolean equalsString(String o1, String o2) {
    if (StringUtils.isEmpty(o1) && StringUtils.isEmpty(o2)) {
      return true;
    } else {
      return o1 == null ? false : o1.equalsIgnoreCase(o2);
    }
  }

  public static boolean equalsOrNullString(String o1, String o2) {
    return !StringUtils.isEmpty(o1) && !StringUtils.isEmpty(o2) ? o1.equalsIgnoreCase(o2) : true;
  }

  public static <T> T nvl(T... values) {
    Object[] var1 = values;
    int var2 = values.length;

    for(int var3 = 0; var3 < var2; ++var3) {
      T val = (T)var1[var3];
      if (val != null) {
        return val;
      }
    }

    return null;
  }

  public static int compareNullAsZero(BigDecimal b1, BigDecimal b2) {
    if (b1 == null) {
      return b2 == null ? 0 : -b2.signum();
    } else {
      return b2 == null ? b1.signum() : b1.compareTo(b2);
    }
  }

  public static boolean equalsOrNull(Object o1, Object o2) {
    return o1 == null || o2 == null || o1.equals(o2);
  }

  public static int compareArrays(Object v1, Object v2, String compareCharset) throws UnsupportedEncodingException {
    if (v1 == null) {
      return v2 == null ? 0 : -1;
    } else if (v2 == null) {
      return 1;
    } else {
      byte[] b1 = convertToByteArray(v1, compareCharset);
      byte[] b2 = convertToByteArray(v2, compareCharset);
      int len = Math.min(b1.length, b2.length);

      for(int i = 0; i < len; ++i) {
        int c = b1[i] - b2[i];
        if (c < 0) {
          return -1;
        }

        if (c > 0) {
          return 1;
        }
      }

      if (b1.length != len) {
        return 1;
      } else if (b2.length != len) {
        return -1;
      } else {
        return 0;
      }
    }
  }

  public static byte[] convertToByteArray(Object v1, String charset) throws UnsupportedEncodingException {
    if (v1 == null) {
      return new byte[0];
    } else if (v1 instanceof byte[]) {
      return (byte[])((byte[])v1);
    } else if (v1 instanceof String) {
      return ((String)v1).getBytes(charset);
    } else {
      throw new ClassCastException("Can't convert " + v1.getClass().getName() + " to byte array");
    }
  }

  public static int compareStreams(InputStream v1, InputStream v2) throws IOException {
    if (v1 == null) {
      return v2 == null ? 0 : -1;
    } else if (v2 == null) {
      return 1;
    } else {
      byte[] buf1 = new byte[8192];
      byte[] buf2 = new byte[8192];

      while(true) {
        int c1 = FileUtils.getBytesFromStream(v1, buf1);
        int c2 = FileUtils.getBytesFromStream(v2, buf2);
        if (c1 <= 0 && c2 <= 0) {
          return 0;
        }

        if (c1 < 0) {
          c1 = 0;
        }

        if (c2 < 0) {
          c2 = 0;
        }

        if (c1 != c2) {
          return c1 - c2;
        }

        try {
          int c = compareArrays(buf1, buf2, (String)null);
          if (c != 0) {
            return c;
          }
        } catch (UnsupportedEncodingException var7) {
        }
      }
    }
  }
}
