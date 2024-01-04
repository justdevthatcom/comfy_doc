package ru.gmdev.comfy_doc.utils;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.BufferedReader;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.lang.model.SourceVersion;

public final class StringUtils {
  private static final String DEFAULT_DECIMAL_PATTERN = "0.00";
  private static final String DEFAULT_DOUBLE_PATTERN = "#.###";
  private static final String DEFAULT_MONEY_PATTERN = "#,##0.00";
  private static final Pattern CAMEL_PATTERN = Pattern.compile("\\p{Lu}(?=\\p{Ll})|(?<=\\p{Ll})\\p{Lu}$|([^\\p{L}\\p{N}]+)([\\p{L}\\p{N}])?");
  private static final Pattern pattern = Pattern.compile("[А-Я]{2}");
  private static final Map<Thread, DecimalFormat> _decimalFormats = new WeakHashMap();
//  private static Set<String> keywords = set.HashSet(new String[]{"parent", "context"});
  private static final Map<String, String> javaNames = new ConcurrentHashMap();
  private static final Pattern numericPattern = Pattern.compile("[0-9]+");
//  static final Map<String, String[][]> grammar_cases = MapBuilder.es("ИЖ", new String[][]{{"ца", "цы", "це", "цу", "цей", "це"}, {"га", "ги", "ге", "гу", "гой", "ге"}, {"ка", "ки", "ке", "ку", "кой", "ке"}, {"ха", "хи", "хе", "ху", "хой", "хе"}, {"жа", "жи", "же", "жу", "жой", "же"}, {"ша", "ши", "ше", "шу", "шой", "ше"}, {"ча", "чи", "че", "чу", "чой", "че"}, {"ща", "щи", "ще", "щу", "щой", "ще"}, {"ия", "ии", "ии", "ию", "ией", "ии"}, {"ь", "и", "и", "ь", "ью", "и"}, {"а", "ы", "е", "у", "ой", "е"}, {"я", "и", "е", "ю", "ей", "е"}}).e("ИМ", new String[][]{{"Лев", "Льва", "Льву", "Льва", "Львом", "Льве"}, {"Павел", "Павла", "Павлу", "Павла", "Павлом", "Павле"}, {"Гия", "Гии", "Гие", "Гию", "Гией", "Гие"}, {"Зия", "Зии", "Зие", "Зию", "Зией", "Зие"}, {"ж", "жа", "жу", "жа", "жем", "же"}, {"ш", "ша", "шу", "ша", "шем", "ше"}, {"ч", "ча", "чу", "ча", "чем", "че"}, {"щ", "ща", "щу", "ща", "щем", "ще"}, {"ц", "ца", "цу", "ца", "цем", "це"}, {"ия", "ии", "ии", "ию", "ией", "ие"}, {"я", "и", "е", "ю", "ей", "е"}, {"га", "ги", "ге", "гу", "гой", "ге"}, {"ка", "ки", "ке", "ку", "кой", "ке"}, {"ха", "хи", "хе", "ху", "хой", "хе"}, {"жа", "жи", "же", "жу", "жой", "же"}, {"ша", "ши", "ше", "шу", "шой", "ше"}, {"ча", "чи", "че", "чу", "чой", "че"}, {"ща", "щи", "ще", "щу", "щой", "ще"}, {"ца", "ци", "це", "цу", "цой", "це"}, {"а", "ы", "е", "у", "ой", "е"}, {"ий", "ия", "ию", "ия", "ием", "ии"}, {"й", "я", "ю", "я", "ем", "е"}, {"ь", "я", "ю", "я", "ем", "е"}, {"о", "", "", "", "", ""}, {"у", "", "", "", "", ""}, {"ы", "", "", "", "", ""}, {"э", "", "", "", "", ""}, {"е", "", "", "", "", ""}, {"ю", "", "", "", "", ""}, {"и", "", "", "", "", ""}, {"", "а", "у", "а", "ом", "е"}}).e("ОЖ", new String[][]{{"на", "ны", "не", "ну", "ной", "не"}, {"ызы", "ызы", "ызы", "ызы", "ызы", "ызы"}}).e("ОМ", new String[][]{{"ич", "ича", "ичу", "ича", "ичем", "иче"}, {"глы", "глы", "глы", "глы", "глы", "глы"}}).e("ФЖ", new String[][]{{"ева", "евой", "евой", "еву", "евой", "евой"}, {"ова", "овой", "овой", "ову", "овой", "овой"}, {"ина", "иной", "иной", "ину", "иной", "иной"}, {"ына", "ыной", "ыной", "ыну", "ыной", "ыной"}, {"ая", "ой", "ой", "ую", "ой", "ой"}}).e("ФМ", new String[][]{{"ов", "ова", "ову", "ова", "овым", "ове"}, {"ев", "ева", "еву", "ева", "евым", "еве"}, {"ёв", "ёва", "ёву", "ёва", "ёвым", "ёве"}, {"ин", "ина", "ину", "ина", "иным", "ине"}, {"ын", "ына", "ыну", "ына", "ыным", "ыне"}, {"цкий", "цкого", "цкому", "цкого", "цким", "цком"}, {"ский", "ского", "скому", "ского", "ским", "ском"}, {"цкой", "цкого", "цкому", "цкого", "цким", "цком"}, {"ской", "ского", "скому", "ского", "ским", "ском"}, {"ый", "ого", "ому", "ого", "ым", "ом"}, {"ич", "ича", "ичу", "ича", "ичем", "иче"}, {"ль", "ля", "лю", "ля", "лем", "ле"}, {"ой", "ого", "ому", "ого", "ым", "ом"}}).map();
  static final String MIN_INTEGER = String.valueOf(-2147483648);
  static final String MAX_INTEGER = String.valueOf(2147483647);
  static final String MIN_LONG = String.valueOf(-9223372036854775808L);
  static final String MAX_LONG = String.valueOf(9223372036854775807L);
  static final int NS_INTEGER = 1;
  static final int NS_LONG = 2;
  public static final char NULL_CHAR = '\u0000';
  public static final String NULL_STRING = "<null>";

  public StringUtils() {
  }

  public static int indexOf(String str, String substr) throws NullPointerException {
    if (substr.length() == 0 && str != null) {
      return 0;
    } else {
      int pos = -1;
      char[] chars = new char[substr.length()];
      substr.toUpperCase().getChars(0, substr.length(), chars, 0);
      int cursor = 0;

      for(int i = 0; i < str.length(); ++i) {
        char c = Character.toUpperCase(str.charAt(i));
        if (c == chars[cursor]) {
          ++cursor;
        } else {
          cursor = 0;
          pos = -1;
        }

        if (cursor == 1) {
          pos = i;
        }

        if (cursor == chars.length) {
          break;
        }
      }

      return cursor == chars.length ? pos : -1;
    }
  }

  public static void replaceAll(StringBuilder str, String strMatch, String newStr) {
    int pos = 0;

    while((pos = str.indexOf(strMatch, pos)) >= 0) {
      str.replace(pos, pos + strMatch.length(), newStr);
    }

  }

  public static String fillChars(int length, char c) {
    if (length <= 0) {
      return "";
    } else {
      char[] buf = new char[length];
      Arrays.fill(buf, c);
      return new String(buf);
    }
  }

  public static String dup(int count, String s) {
    char[] src = s.toCharArray();
    char[] buf = new char[count * src.length];

    for(int i = 0; i < count; ++i) {
      System.arraycopy(src, 0, buf, i * src.length, src.length);
    }

    return new String(buf);
  }

  public static StringBuffer alignStringRight(String str, int length, char c) {
    StringBuffer buf = new StringBuffer(str);

    for(int i = length - buf.length(); i > 0; --i) {
      buf.append(c);
    }

    return buf;
  }

  public static StringBuffer alignStringRight(String str, int length, char c, boolean fixlength) {
    StringBuffer buf = alignStringRight(str, length, c);
    if (fixlength) {
      while(buf.length() > length) {
        buf.delete(0, 1);
      }
    }

    return buf;
  }

  public static StringBuilder alignStringLeft(String str, int length, char c) {
    StringBuilder buf = str == null ? new StringBuilder() : new StringBuilder(str);

    for(int i = length - buf.length(); i > 0; --i) {
      buf.insert(0, c);
    }

    return buf;
  }

  public static StringBuilder alignStringLeft(String str, int length, char c, boolean fixlength) {
    StringBuilder buf = alignStringLeft(str, length, c);
    if (fixlength && buf.length() > length) {
      buf.setLength(length);
    }

    return buf;
  }

  public static StringBuilder alignStringLeftCut(String str, int length, char c) {
    StringBuilder buf = alignStringLeft(str, length, c);
    return buf.length() > length ? new StringBuilder(buf.substring(buf.length() - length)) : buf;
  }

  public static String formatNumValue(String value, int numLen, int maxLen) {
    StringBuffer s = new StringBuffer(value.trim());

    int apos;
    for(apos = 0; apos < s.length() && Character.isDigit(s.charAt(apos)); ++apos) {
    }

    while(apos < numLen && s.length() < maxLen) {
      s.insert(0, ' ');
      ++apos;
    }

    return s.toString();
  }

//  public static String escapeString(String s, Escaper escaper, boolean quote) {
//    if (s == null) {
//      return null;
//    } else {
//      StringBuilder buffer = new StringBuilder();
//      if (quote) {
//        buffer.append('\'');
//      }
//
//      for(int i = 0; i < s.length(); ++i) {
//        char c = s.charAt(i);
//        if (escaper.needsEscape(c)) {
//          buffer.append(escaper.getEscapeSequence(c));
//        }
//
//        buffer.append(c);
//      }
//
//      if (quote) {
//        buffer.append('\'');
//      }
//
//      return buffer.toString();
//    }
//  }

  public static DecimalFormat getDecimalFormat() {
    synchronized(_decimalFormats) {
      DecimalFormat format = (DecimalFormat)_decimalFormats.get(Thread.currentThread());
      if (format == null) {
        DecimalFormatSymbols DFSymbols = new DecimalFormatSymbols();
        DFSymbols.setDecimalSeparator('.');
        format = new DecimalFormat("0.00", DFSymbols);
        _decimalFormats.put(Thread.currentThread(), format);
      }

      return format;
    }
  }

  public static DecimalFormat getDoubleFormat() {
    synchronized(_decimalFormats) {
      DecimalFormat format = (DecimalFormat)_decimalFormats.get(Thread.currentThread());
      if (format == null) {
        DecimalFormatSymbols DFSymbols = new DecimalFormatSymbols();
        DFSymbols.setDecimalSeparator('.');
        format = new DecimalFormat("#.###", DFSymbols);
        _decimalFormats.put(Thread.currentThread(), format);
      }

      return format;
    }
  }

  public static DecimalFormat getMoneyFormat() {
    synchronized(_decimalFormats) {
      DecimalFormat format = (DecimalFormat)_decimalFormats.get(Thread.currentThread());
      if (format == null) {
        format = new DecimalFormat("#,##0.00");
        _decimalFormats.put(Thread.currentThread(), format);
      }

      return format;
    }
  }

  public static String escapeString(String s, boolean quote) {
    if (s == null) {
      return null;
    } else {
      StringBuffer buffer = new StringBuffer();
      if (quote) {
        buffer.append('\'');
      }

      for(int i = 0; i < s.length(); ++i) {
        char c = s.charAt(i);
        if ('\'' == c) {
          buffer.append("'");
        }

        buffer.append(c);
      }

      if (quote) {
        buffer.append('\'');
      }

      return buffer.toString();
    }
  }

  public static String toString(Object value) {
    return nvl(value);
  }

  public static String nvl(Object value) {
    return nvl(value, (String)null);
  }

  public static String nvl(Object source, String value) {
    if (source == null) {
      return value;
    } else if (!source.getClass().isArray()) {
      return source.toString();
    } else {
      int length = Array.getLength(source);
      int iMax = length - 1;
      if (iMax == -1) {
        return "[]";
      } else {
        StringBuilder b = new StringBuilder();
        b.append('[');
        int i = 0;

        while(true) {
          b.append(Array.get(source, i));
          if (i == iMax) {
            return b.append(']').toString();
          }

          b.append(", ");
          ++i;
        }
      }
    }
  }

  public static String nvlEmpty(String source) {
    return nvlEmpty(source, (String)null);
  }

  public static String nvlEmpty(String source, String value) {
    return isEmpty(source) ? value : source;
  }

  public static String nvl(String source, String value, boolean doTrim) {
    return source == null ? value : (doTrim ? ("".equals(source.trim()) ? value : source) : ("".equals(source) ? value : source));
  }

//  public static String toDelimitedString(char delimiter, String... strings) {
//    return toDelimitedString(delimiter, false, strings);
//  }

//  public static String toDelimitedString(char delimiter, boolean quote, String... strings) {
//    if (strings.length > 0) {
//      StringBuilder result = new StringBuilder();
//      String[] var4 = strings;
//      int var5 = strings.length;
//
//      for(int var6 = 0; var6 < var5; ++var6) {
//        String s = var4[var6];
//        if (s != null) {
//          if (result.length() != 0) {
//            result.append(delimiter);
//          }
//
//          if (quote && s.indexOf(delimiter) > -1) {
//            s = "\"" + escapeString(s, new Escaper() {
//              public boolean needsEscape(char c) {
//                return c == '"';
//              }
//
//              public String getEscapeSequence(char c) {
//                return "\\" + c;
//              }
//            }, false) + "\"";
//          }
//
//          result.append(s);
//        }
//      }
//
//      return result.toString();
//    } else {
//      return "";
//    }
//  }

//  public static String toDelimitedString(char delimiter, Transformer<String, String> transformer, String... strings) {
//    if (strings.length > 0) {
//      StringBuilder result = new StringBuilder();
//      String[] var4 = strings;
//      int var5 = strings.length;
//
//      for(int var6 = 0; var6 < var5; ++var6) {
//        String s = var4[var6];
//        if (s != null) {
//          if (result.length() != 0) {
//            result.append(delimiter);
//          }
//
//          result.append(transformer == null ? s : (String)transformer.transform(s));
//        }
//      }
//
//      return result.toString();
//    } else {
//      return "";
//    }
//  }

//  public static String toCommaString(String... strings) {
//    return toDelimitedString(',', strings);
//  }
//
//  public static String toCommaString(Collection<String> strings) {
//    return toDelimitedString(',', (String[])strings.toArray(new String[0]));
//  }

  public static String exReplace(String s) {
    String[] parts = s.substring(1).split(s.substring(0, 1));
    return parts.length < 3 ? s : parts[0].replaceAll(parts[1], parts[2]);
  }

  public static String truncate(String s, int maxLen) {
    if (maxLen <= 0) {
      return s == null ? null : "";
    } else {
      return s == null ? null : (s.length() <= maxLen ? s : s.substring(0, maxLen));
    }
  }

  public static String reduce(String s, int length, int startCharCount, String replacer) {
    if (s != null && s.length() > length) {
      if (length <= 0) {
        return "";
      } else {
        if (startCharCount > length) {
          startCharCount = length;
        }

        if (startCharCount < 0) {
          startCharCount = 0;
        }

        if (replacer == null) {
          replacer = "";
        }

        if (replacer.length() > length - startCharCount) {
          replacer = replacer.substring(0, length - startCharCount);
        }

        int slicePosition = s.length() - (length - startCharCount - replacer.length());
        return s.substring(0, startCharCount) + replacer + s.substring(slicePosition);
      }
    } else {
      return s;
    }
  }

  public static String reduceUUEncoded(String s, int length, String replacer) throws UnsupportedEncodingException {
    if (s == null) {
      return s;
    } else {
      int originalLength;
      for(originalLength = s.length(); URLEncoder.encode(reduce(s, originalLength, originalLength / 4, replacer), "utf-8").length() > length; --originalLength) {
      }

      return reduce(s, originalLength, originalLength / 4, replacer);
    }
  }

  public static String trimTrailing(String s) {
    int index;
    for(index = s.length() - 1; index >= 0 && Character.isWhitespace(s.charAt(index)); --index) {
    }

    return s.substring(0, index + 1);
  }

  public static String trimTrailing(String s, char ch) {
    int index;
    for(index = s.length() - 1; index >= 0 && s.charAt(index) == ch; --index) {
    }

    return s.substring(0, index + 1);
  }

  public static String trimLeft(String s, char ch) {
    int index;
    for(index = 0; index < s.length() && s.charAt(index) == ch; ++index) {
    }

    return s.substring(index);
  }

  public static String trim(String s, char ch) {
    int index;
    for(index = s.length() - 1; index >= 0 && s.charAt(index) == ch; --index) {
    }

    int start;
    for(start = 0; start < s.length() && s.charAt(start) == ch; ++start) {
    }

    return start < s.length() ? s.substring(start, index + 1) : "";
  }

  public static String unquote(String s, Character quote) {
    if (quote != null && s != null) {
      while(s.charAt(0) == quote && s.charAt(s.length() - 1) == quote) {
        s = s.substring(1, s.length() - 1);
      }

      return s;
    } else {
      return s;
    }
  }

  public static String capitalizeFirstChar(String s) {
    return s != null && s.length() != 0 ? Character.toUpperCase(s.charAt(0)) + s.substring(1) : s;
  }

  public static String deCapitalizeFirstChar(String s) {
    return s != null && s.length() != 0 ? Character.toLowerCase(s.charAt(0)) + s.substring(1) : s;
  }

  public static String toConstantName(String name) {
    if (name == null) {
      return null;
    } else {
      name = trim(name.trim().replaceAll("[^\\p{L}\\p{N}]+", "_").toUpperCase(), '_');
      return name.length() != 0 && !name.equals("_") ? (Character.isDigit(name.charAt(0)) ? "_" + name : name) : null;
    }
  }

//  public static String toJavaName(String name) {
//    if (name == null) {
//      return null;
//    } else {
//      String cachedJavaName = (String)javaNames.get(name);
//      if (cachedJavaName == null) {
//        String input = name.trim();
//        int length = input.length();
//        if (length > 0) {
//          Matcher matcher = CAMEL_PATTERN.matcher(input);
//          StringBuilder javaNameSb = new StringBuilder(length);
//
//          int lastMatchedPos;
//          String group2;
//          for(lastMatchedPos = 0; matcher.find(); lastMatchedPos = matcher.end()) {
//            int first = matcher.start();
//            String group1;
//            if (lastMatchedPos < first) {
//              group1 = input.substring(lastMatchedPos, first);
//              javaNameSb.append(group1.toLowerCase());
//            }
//
//            group1 = matcher.group(1);
//            if (group1 != null) {
//              group2 = matcher.group(2);
//              if (group2 != null) {
//                javaNameSb.append(Character.toUpperCase(group2.charAt(0)));
//              }
//            } else {
//              javaNameSb.append(Character.toUpperCase(matcher.group().charAt(0)));
//            }
//          }
//
//          String javaName;
//          if (lastMatchedPos < length) {
//            javaName = input.substring(lastMatchedPos, length);
//            javaNameSb.append(javaName.toLowerCase());
//          }
//
//          if (javaNameSb.length() > 0) {
//            javaName = javaNameSb.toString();
//            char first = Character.toUpperCase(javaNameSb.charAt(0));
//            javaNameSb.setCharAt(0, first);
//            group2 = javaNameSb.toString();
//            if (!SourceVersion.isKeyword(javaName) && !SourceVersion.isKeyword(group2) && !keywords.contains(javaName)) {
//              javaNames.put(name, javaName);
//              return javaName;
//            }
//
//            String corrJavaName = javaName + "S";
//            javaNames.put(name, corrJavaName);
//            return corrJavaName;
//          }
//        }
//      }
//
//      return cachedJavaName;
//    }
//  }

  public static boolean isEmpty(String s) {
    return isEmpty(s, true);
  }

  public static boolean isEmpty(String s, Boolean doTrim) {
    return doTrim ? s == null || s.trim().length() == 0 : s == null || s.length() == 0;
  }

//  public static String formatFio(String fio, String inputFormat, String outputFormat, int cse, boolean doShort) {
//    String[] spltFIO = fio.trim().replaceAll(" +", " ").split(" ");
//    Map<Character, String> m = new HashMap();
//
//    for(int i = 0; i < inputFormat.length(); ++i) {
//      Character c = inputFormat.charAt(i);
//      if (c == 1060 || c == 1048 || c == 1054) {
//        m.put(c, spltFIO[i]);
//      }
//    }
//
//    if (cse > 0 || doShort) {
//      char gender = getGenderFromSecondName((String)m.get('О'));
//      String lastName = (String)m.get('Ф');
//      if (gender != 0 && cse > 0) {
//        m.put('Ф', getNameInCase(lastName, "Ф" + gender, cse));
//      }
//
//      String firstName = (String)m.get('И');
//      if (doShort) {
//        m.put('И', Character.toUpperCase(firstName.charAt(0)) + "");
//      } else {
//        m.put('И', getNameInCase(firstName, "И" + gender, cse));
//      }
//
//      String secondName = (String)m.get('О');
//      if (doShort) {
//        m.put('О', Character.toUpperCase(secondName.charAt(0)) + "");
//      } else {
//        m.put('О', getNameInCase(secondName, "О" + gender, cse));
//      }
//    }
//
//    StringBuilder sb = new StringBuilder(255);
//
//    for(int i = 0; i < outputFormat.length(); ++i) {
//      Character c = outputFormat.charAt(i);
//      if (c != 1060 && c != 1048 && c != 1054) {
//        sb.append(c);
//      } else {
//        sb.append((String)m.get(c));
//      }
//    }
//
//    return sb.toString();
//  }

//  public static String getFIOInCaseAsSingleString(String FIOName, CharSequence signature, int Case) {
//    String[] spltFIO = FIOName.trim().replaceAll(" +", " ").split(" ");
//    if (spltFIO.length != 3) {
//      return toDelimitedString(' ', spltFIO);
//    } else {
//      String[] caseFio = getFIOInCase(spltFIO[getSignatureIdx(signature, 'И')], spltFIO[getSignatureIdx(signature, 'О')], spltFIO[getSignatureIdx(signature, 'Ф')], (Character)null, Case);
//      String[] result = new String[3];
//      result[getSignatureIdx(signature, 'И')] = caseFio[0];
//      result[getSignatureIdx(signature, 'О')] = caseFio[1];
//      result[getSignatureIdx(signature, 'Ф')] = caseFio[2];
//      return toDelimitedString(' ', result);
//    }
//  }
//
//  public static String getShortNameInCase(String fio, CharSequence signature, int cse) {
//    return getShortNameInCase(fio, signature, "ФИО", cse);
//  }

//  public static String getShortNameInCase(String fio, CharSequence signature, CharSequence resSignature, int cse) {
//    return getShortNameInCase(fio, signature, resSignature, cse, false);
//  }
//
//  public static String getShortNameInCase(String fio, CharSequence signature, CharSequence resSignature, int cse, boolean withoutScapeInInitials) {
//    String[] spltFIO = fio.trim().replaceAll("\\s+", " ").split(" (?!(оглы|кызы))");
//    if (spltFIO.length != 3) {
//      return toDelimitedString(' ', spltFIO);
//    } else {
//      String[] res = new String[3];
//      char gender = getGenderFromSecondName(spltFIO[getSignatureIdx(signature, 'О')]);
//      res[getSignatureIdx(resSignature, 'Ф')] = gender == 0 ? spltFIO[getSignatureIdx(signature, 'Ф')] : getNameInCase(spltFIO[getSignatureIdx(signature, 'Ф')], "Ф" + gender, cse);
//      res[getSignatureIdx(resSignature, 'И')] = Character.toUpperCase(spltFIO[getSignatureIdx(signature, 'И')].charAt(0)) + ".";
//      int nO = getSignatureIdx(signature, 'О');
//      int positionHyphen = getPositionHyphen(spltFIO[nO]);
//      int rnO = getSignatureIdx(resSignature, 'О');
//      if (positionHyphen == -1) {
//        res[rnO] = Character.toUpperCase(spltFIO[nO].charAt(0)) + ".";
//      } else if (spltFIO[nO].length() > 1) {
//        res[rnO] = Character.toUpperCase(spltFIO[nO].charAt(0)) + "-" + Character.toUpperCase(spltFIO[nO].charAt(positionHyphen + 1)) + ".";
//      }
//
//      String result = toDelimitedString(' ', res);
//      if (withoutScapeInInitials) {
//        result = result.replaceAll("\\.\\s(?=[А-ЯЁ]\\.)", ".");
//      }
//
//      return result;
//    }
//  }

  private static int getSignatureIdx(CharSequence signature, char sign) {
    for(int i = 0; i < signature.length(); ++i) {
      if (signature.charAt(i) == sign) {
        return i;
      }
    }

    return -1;
  }

  private static int getPositionHyphen(String patronymic) {
    return patronymic.indexOf(45);
  }

  public static char getGenderFromSecondName(String secondName) {
    if (isEmpty(secondName)) {
      return '\u0000';
    } else {
      char gender = 0;
      if (!secondName.endsWith("ич") && !secondName.endsWith("ИЧ") && !secondName.endsWith("глы") && !secondName.endsWith("ГЛЫ")) {
        if (secondName.endsWith("на") || secondName.endsWith("НА") || secondName.endsWith("ызы") || secondName.endsWith("ЫЗЫ")) {
          gender = 1046;
        }
      } else {
        gender = 1052;
      }

      return gender;
    }
  }

//  public static String[] getFIOInCase(String firstName, String secondName, String lastName, Character Gender, int Case) {
//    String[] res = new String[3];
//    char gender = getGenderFromSecondName(secondName);
//    if (gender == 0 && Gender != null) {
//      gender = Gender;
//    }
//
//    if (gender == 0) {
//      res[0] = firstName;
//      res[1] = secondName;
//      res[2] = lastName;
//      return res;
//    } else {
//      res[0] = getNameInCase(firstName, "И" + gender, Case);
//      res[1] = getNameInCase(secondName, "О" + gender, Case);
//      if (lastName != null) {
//        String[] split = lastName.split("-");
//
//        for(int i = 0; i < split.length; ++i) {
//          split[i] = getNameInCase(split[i], "Ф" + gender, Case);
//        }
//
//        res[2] = glue("-", split);
//      }
//
//      return res;
//    }
//  }

//  private static String getNameInCase(String name, String s, int Case) {
//    if (isEmpty(name)) {
//      return "";
//    } else {
//      String[][] cases = (String[][])grammar_cases.get(s);
//      String[][] var4 = cases;
//      int var5 = cases.length;
//
//      for(int var6 = 0; var6 < var5; ++var6) {
//        String[] c = var4[var6];
//        if (name.endsWith(c[0]) || name.endsWith(c[0].toUpperCase())) {
//          String ending = c[Case];
//          if (isEmpty(ending)) {
//            return name;
//          } else {
//            boolean isUpper = pattern.matcher(name).find();
//            return name.substring(0, name.length() - c[0].length()) + (isUpper ? ending.toUpperCase() : ending);
//          }
//        }
//      }
//
//      return name;
//    }
//  }

  public static String getNumeralEnding(long number, String end1, String end24, String other) {
    String s = Long.toString(number);
    int length = s.length();
    char lastDigit = s.charAt(length - 1);
    if (lastDigit == '1') {
      if (length == 1 || length > 1 && s.charAt(length - 2) != '1') {
        return end1;
      }
    } else if (lastDigit >= '2' && lastDigit <= '4' && (length == 1 || length > 1 && s.charAt(length - 2) != '1')) {
      return end24;
    }

    return other;
  }

  public static boolean parseBoolean(String s, boolean defValue) {
    if (isEmpty(s)) {
      return defValue;
    } else if (!"true".equalsIgnoreCase(s) && !"yes".equalsIgnoreCase(s) && !"да".equalsIgnoreCase(s)) {
      if (!"false".equalsIgnoreCase(s) && !"no".equalsIgnoreCase(s) && !"нет".equalsIgnoreCase(s)) {
        try {
          return Integer.parseInt(s) != 0;
        } catch (Exception var3) {
          return defValue;
        }
      } else {
        return false;
      }
    } else {
      return true;
    }
  }

//  public static String glue(String separator, String... s) {
//    return glue(separator, (Formatter)null, s);
//  }
//
//  public static <T> String glue(String separator, Formatter<T> f, T... s) {
//    StringBuilder buf = new StringBuilder();
//    Object[] var4 = s;
//    int var5 = s.length;
//
//    for(int var6 = 0; var6 < var5; ++var6) {
//      T obj = var4[var6];
//      String str = obj == null ? null : (f == null ? obj.toString() : f.formatValue(obj));
//      if (str != null && str.length() > 0) {
//        if (buf.length() > 0) {
//          buf.append(separator);
//        }
//
//        buf.append(str);
//      }
//    }
//
//    return buf.toString();
//  }

  public static String glue(char separator, Collection<String> s) {
    StringBuilder buf = new StringBuilder();
    Iterator var3 = s.iterator();

    while(var3.hasNext()) {
      String str = (String)var3.next();
      if (str != null && str.length() > 0) {
        if (buf.length() > 0) {
          buf.append(separator);
        }

        buf.append(str);
      }
    }

    return buf.toString();
  }

  public static String formatAmount(BigDecimal value) {
    return value == null ? "0.00" : value.setScale(2, 4).toString();
  }

//  public static String formatToLocalizedStrign(Object o) {
//    if (o == null) {
//      return "";
//    } else if (o instanceof Date) {
//      return DateUtilities.instance().formatDateTime((Date)o, "dd/MM/yyyy HH:mm");
//    } else if (o instanceof Boolean) {
//      return (Boolean)o ? "Да" : "Нет";
//    } else {
//      return o.toString();
//    }
//  }

  public static String getStackTrace(Throwable e) {
    if (e == null) {
      return null;
    } else {
      CharArrayWriter stream = new CharArrayWriter(1000);
      PrintWriter pw = new PrintWriter(stream);
      e.printStackTrace(pw);
      return stream.toString();
    }
  }

  public static StringBuilder append(StringBuilder sb, CharSequence value, String delimiter) {
    if (value != null && value.length() > 0) {
      if (sb.length() > 0 && !isEmpty(delimiter, false)) {
        sb.append(delimiter);
      }

      sb.append(value);
    }

    return sb;
  }

  public static boolean isEmpty(StringBuilder sb) {
    return sb == null || sb.length() == 0;
  }

  public static Long getLong(String s) {
    return s == null ? null : Long.valueOf(s);
  }

  public static String trim(String s) {
    return s == null ? null : s.trim();
  }

  public static void removeEmptyStrings(String[] strings) {
    for(int i = 0; i < strings.length; ++i) {
      if (isEmpty(strings[i])) {
        strings[i] = null;
      }
    }

  }

  public static String getEntityName(String name) {
    return name == null ? null : name.replaceAll(" {2,}", " ").replaceAll("\"|'", "").toUpperCase();
  }

  public static int compare(String str1, String str2, String charset) throws UnsupportedEncodingException {
    return compare(str1, str2, charset, true);
  }

  public static int compare(String str1, String str2, String charset, boolean nullsFirst) throws UnsupportedEncodingException {
    if (str1 == null) {
      return str2 == null ? 0 : (nullsFirst ? -1 : 1);
    } else if (str2 == null) {
      return nullsFirst ? 1 : -1;
    } else {
      byte[] arr1 = str1.trim().getBytes(charset);
      byte[] arr2 = str2.trim().getBytes(charset);
      int len = Math.min(arr1.length, arr2.length);

      for(int i = 0; i < len; ++i) {
        int code1 = arr1[i] & 255;
        int code2 = arr2[i] & 255;
        if (code1 != code2) {
          return code1 - code2;
        }
      }

      return arr1.length - arr2.length;
    }
  }

  public static int compareNums(String s1, String s2, String charset, boolean nullsFirst) throws UnsupportedEncodingException {
    if (s1 != null && s2 != null) {
      int len1 = s1.length();
      int len2 = s2.length();
      int n = len1 > len2 ? len1 : len2;

      for(int i = 0; i < n; ++i) {
        int code1 = 255 & (n - i > len1 ? 32 : s1.charAt(i - n + len1));
        int code2 = 255 & (n - i > len2 ? 32 : s2.charAt(i - n + len2));
        if (code1 != code2) {
          return code1 - code2;
        }
      }

      return 0;
    } else {
      return compare(s1, s2, charset, nullsFirst);
    }
  }

  private static StringBuilder toXmlName(String str) {
    if (str == null) {
      return new StringBuilder();
    } else {
      StringBuilder res = new StringBuilder(str.toLowerCase());

      int a;
      while((a = res.indexOf("_")) >= 0) {
        res.delete(a, a + 1);
        if (res.length() <= a) {
          break;
        }

        res.setCharAt(a, Character.toUpperCase(res.charAt(a)));
      }

      return res;
    }
  }

  public static String toXmlElementName(String metaName) {
    if (metaName == null) {
      return null;
    } else {
      StringBuilder res = toXmlName(metaName);
      res.setCharAt(0, Character.toUpperCase(res.charAt(0)));
      return res.toString();
    }
  }

  public static String toXmlTypeName(String metaName) {
    if (metaName == null) {
      return null;
    } else {
      StringBuilder res = toXmlName(metaName);
      res.setCharAt(0, Character.toUpperCase(res.charAt(0)));
      return res.append("Type").toString();
    }
  }

  public static String toXmlAttributeName(String metaName) {
    return metaName == null ? null : toXmlName(metaName).toString();
  }

  public static String extractBase64Encoded(String base64Encoded) throws IOException {
    StringBuilder buf = new StringBuilder(base64Encoded.length());
    BufferedReader r = new BufferedReader(new StringReader(base64Encoded));

    String line;
    do {
      line = r.readLine();
      if (line != null) {
        line = line.trim();
        if (line.length() > 0 && line.charAt(0) != '-') {
          buf.append(line);
        }
      }
    } while(line != null);

    return buf.toString();
  }

  public static String bytesToHex(byte[] data) {
    StringBuilder buf = new StringBuilder();
    byte[] var2 = data;
    int var3 = data.length;

    for(int var4 = 0; var4 < var3; ++var4) {
      byte b = var2[var4];
      int i = b & 255;
      String str = Integer.toHexString(i);
      if (str.length() == 1) {
        buf.append('0');
      }

      buf.append(str);
    }

    return buf.toString();
  }

  public static Long extractNumber(String s) {
    Matcher matcher = numericPattern.matcher(s);
    return matcher.find() ? Long.valueOf(matcher.group()) : null;
  }

//  public static String parseTemplateString(String s, Map<String, ?> values) throws IOException {
//    Templater t = new Templater(values);
//    return t.process(s);
//  }

  public static String trimSpace(String source) {
    return source == null ? null : source.replaceAll("\\s", "").trim();
  }

  public static String stringToNumberString(String s) {
    if (s != null && s.length() != 0) {
      s = s.replaceAll("\\s", "");
      s = s.replaceAll("[оОoO]", "0");
      s = s.replaceAll("[зЗ]", "3");
      s = s.replaceAll("[чЧ]", "4");
      s = s.replaceAll("[l!iI'\"|\\]]", "1");
      s = s.replaceAll("[,]", ".");
      s = s.replaceAll("[бb]", "6");
      s = s.replaceAll("[BвВ]", "8");
      s = s.replaceAll("[zZ]", "2");
      if (s.endsWith(".")) {
        s = s.substring(0, s.length() - 1);
      }

      return s;
    } else {
      return null;
    }
  }

  public static String compact(String s) {
    if (s == null) {
      return null;
    } else {
      return s.indexOf(32) < 0 ? s : compact((CharSequence)s);
    }
  }

  public static String compact(CharSequence s) {
    if (s == null) {
      return null;
    } else {
      StringBuilder buf = new StringBuilder();
      char c = ' ';

      for(int i = 0; i < s.length(); ++i) {
        char cc = s.charAt(i);
        if (c != ' ' || cc != ' ') {
          buf.append(cc);
        }

        c = cc;
      }

      return buf.toString();
    }
  }

  public static String compactSnils(String source) {
    return source == null ? null : source.replaceAll("\\-|\\s+", "");
  }

  private static boolean isNumber(String s, int NUMBER_SIZE) {
    String MIN_NUMBER = "";
    String MAX_NUMBER = "";
    switch(NUMBER_SIZE) {
      case 1:
        MIN_NUMBER = MIN_INTEGER;
        MAX_NUMBER = MAX_INTEGER;
        break;
      case 2:
        MIN_NUMBER = MIN_LONG;
        MAX_NUMBER = MAX_LONG;
    }

    if (s == null) {
      return false;
    } else {
      int len = s.length();
      boolean negative = false;
      int pos = len > 0 && (negative = s.charAt(0) == '-') ? 1 : 0;
      if (pos == len) {
        return false;
      } else {
        while(pos < len && s.charAt(pos) == '0') {
          ++pos;
        }

        if (pos == len) {
          return true;
        } else if ((!negative || len - pos <= MIN_NUMBER.length() - 1) && len - pos <= MAX_NUMBER.length()) {
          boolean needCheckRange = negative && len - pos == MIN_NUMBER.length() - 1 || len - pos == MAX_NUMBER.length();
          if (!needCheckRange) {
            while(pos < len) {
              char c = s.charAt(pos);
              if (c < '0' || c > '9') {
                return false;
              }

              ++pos;
            }
          } else {
            String rangeString = negative ? MIN_NUMBER : MAX_NUMBER;

            for(int i = negative ? 1 : 0; pos < len; ++i) {
              char c = s.charAt(pos);
              char r = 0;
              if (c < '0' || c > '9' || needCheckRange && c > (r = rangeString.charAt(i))) {
                return false;
              }

              if (needCheckRange &= c == r) {
              }

              ++pos;
            }
          }

          return true;
        } else {
          return false;
        }
      }
    }
  }

  public static boolean isInteger(String s) {
    return isNumber(s, 1);
  }

  public static boolean isLong(String s) {
    return isNumber(s, 2);
  }

  public static String declineWordByNumber(int number, String wordIp, String wordRp, String wordMn) {
    if (wordMn == null) {
      wordMn = wordRp;
    }

    if (number >= 5 && number <= 20) {
      return wordMn;
    } else {
      number %= 10;
      return number == 1 ? wordIp : (number >= 2 && number <= 4 ? wordRp : wordMn);
    }
  }

  public static String after(String value, String prefix) {
    int i;
    return value == null ? null : ((i = value.indexOf(prefix)) < 0 ? null : value.substring(i + prefix.length()));
  }

  public static String before(String value, String suffix) {
    int i;
    return value == null ? null : ((i = value.indexOf(suffix)) < 0 ? value : value.substring(0, i));
  }

  public static boolean equals(String s1, String s2, boolean ignoreCase, boolean trueIfNulls) {
    if (s1 != null && s2 != null) {
      return ignoreCase ? s1.equalsIgnoreCase(s2) : s1.equals(s2);
    } else {
      return trueIfNulls && s1 == null && s2 == null;
    }
  }

  public static String ident(String s, int ident, String identString) {
    if (s != null && !s.isEmpty()) {
      String[] split = s.split("\n");
      StringBuilder buf = new StringBuilder(s.length() * 2);
      String dup = dup(ident, identString);
      String[] var6 = split;
      int var7 = split.length;

      for(int var8 = 0; var8 < var7; ++var8) {
        String str = var6[var8];
        buf.append(dup);
        buf.append(str);
        buf.append("\n");
      }

      buf.delete(buf.length() - 1, buf.length());
      return buf.toString();
    } else {
      return s;
    }
  }

  public static String toLinearString(String s, boolean removeSpareSpaces) {
    if (s == null) {
      return null;
    } else {
      int i = 0;
      StringBuilder buf = new StringBuilder(s.length());

      while(true) {
        label49:
        while(i < s.length()) {
          char c = s.charAt(i++);
          if (c != '\n' && c != '\r' && (!removeSpareSpaces || c != ' ')) {
            buf.append(c);
          } else {
            char c1;
            do {
              do {
                do {
                  if (i >= s.length()) {
                    continue label49;
                  }

                  c1 = s.charAt(i++);
                } while(c1 == '\n');
              } while(c1 == '\r');
            } while(removeSpareSpaces && c1 == ' ');

            buf.append(' ');
            buf.append(c1);
          }
        }

        return buf.toString();
      }
    }
  }

  public static String upcase(String s) {
    return s == null ? null : s.toUpperCase();
  }

  public static String[] wordWrap(String message, int maxLen) {
    if (message != null && message.length() != 0) {
      String[] strings = message.split("\\s+");
      List<String> list = new ArrayList();
      StringBuilder buf = new StringBuilder();
      String[] var5 = strings;
      int var6 = strings.length;

      for(int var7 = 0; var7 < var6; ++var7) {
        String string = var5[var7];
        if (buf.length() + string.length() <= maxLen - 1) {
          if (buf.length() > 0) {
            buf.append(' ');
          }

          buf.append(string);
        } else if (string.length() <= maxLen) {
          list.add(buf.toString());
          buf.setLength(0);
          buf.append(string);
        } else {
          if (buf.length() < maxLen - 5) {
            if (buf.length() > 0) {
              buf.append(' ');
            }

            int bufLen = buf.length();
            buf.append(string.substring(0, maxLen - bufLen));
            string = string.substring(maxLen - bufLen);
          }

          list.add(buf.toString());
          buf.setLength(0);

          while(string.length() > maxLen) {
            list.add(string.substring(0, maxLen));
            string = string.substring(maxLen);
          }

          buf.append(string);
        }
      }

      if (buf.length() > 0) {
        list.add(buf.toString());
      }

      return (String[])list.toArray(new String[list.size()]);
    } else {
      return new String[0];
    }
  }

  public static String toCharset(String value, Charset c) {
    return c.equals(Charset.defaultCharset()) ? value : new String(value.getBytes(c), c);
  }

  public static String replaceControlChars(String s) {
    int length = s.length();
    char[] chars = new char[length];
    s.getChars(0, length, chars, 0);

    for(int j = 0; j < length; ++j) {
      if (chars[j] < ' ') {
        chars[j] = ' ';
      }
    }

    return new String(chars, 0, length);
  }

  public static String removeNullChars(String value) {
    if (value != null && value.length() != 0) {
      return value.indexOf(0) == -1 ? value : removeChar(value, '\u0000');
    } else {
      return value;
    }
  }

  public static String removeChar(String value, char c) {
    if (value != null && value.length() != 0) {
      int length = value.length();
      char[] chars = new char[length];
      value.getChars(0, length, chars, 0);
      int newLength = 0;

      for(int i = 0; i < length; ++i) {
        char curChar = chars[i];
        if (curChar != c) {
          chars[newLength++] = curChar;
        }
      }

      return newLength != length ? new String(chars, 0, newLength) : value;
    } else {
      return value;
    }
  }

  public static String formatMemorySize(long size) {
    if (size <= 0L) {
      return String.valueOf(size);
    } else if (size < 1024L) {
      return size + "B";
    } else if (size < 1048576L) {
      return formatMemorySize2Digits(size, 1024L) + "K";
    } else if (size < 1073741824L) {
      return formatMemorySize2Digits(size, 1048576L) + "M";
    } else {
      return size < 1099511627776L ? formatMemorySize2Digits(size, 1073741824L) + "G" : formatMemorySize2Digits(size, 1099511627776L) + "T";
    }
  }

  private static String formatMemorySize2Digits(long size, long div) {
    BigDecimal value = new BigDecimal(1.0D * (double)size / (double)div);
    BigDecimal decimal = value.setScale(2, 4);
    return decimal.setScale(0, 0).compareTo(decimal) == 0 ? String.valueOf(decimal.longValue()) : decimal.toString();
  }
}
