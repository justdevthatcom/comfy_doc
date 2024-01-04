package ru.gmdev.comfy_doc.utils;

import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import ru.gmdev.comfy_doc.core.exception.SystemException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TypeConverter {
  private static final Map<Class, Converter> converters = new HashMap();

  public static Boolean getBoolean(Object value) {
    return (Boolean)BOOLEAN.convert(value);
  }

  public static TypeConverter.Converter<Boolean> BOOLEAN = new TypeConverter.Converter<Boolean>(Boolean.class) {
    protected Boolean convertObject(Object v) {
      if (v instanceof Number) {
        return ((Number)v).intValue() == 1;
      } else {
        this.conversionError(v);
        return null;
      }
    }

    protected Boolean convertString(String s) {
      s = s.trim();
      return "yes".equalsIgnoreCase(s) || "1".equalsIgnoreCase(s) || "true".equalsIgnoreCase(s);
    }
  };

  public static TypeConverter.Converter<String> STRING = new TypeConverter.Converter<String>(String.class) {
    protected String convertObject(Object v) {
//      return v instanceof Collection ? ParamsParser.toCommaDelimited((Collection)v, (Filter)null, true) : v.toString();
      return "not implemented";
    }

    protected String convertString(String s) {
      return s;
    }
  };

  public abstract static class Converter<T> {
    protected Class<T> c;

    protected Converter(Class<T> c) {
      this.c = c;
      TypeConverter.converters.put(c, this);
    }

    protected Object getCollectionValue(Object v) {
      if (v instanceof Collection) {
        Collection<?> collection = (Collection)v;
        if (collection.isEmpty()) {
          return null;
        } else {
          return collection.size() == 1 ? collection.iterator().next() : v;
        }
      } else if (v != null && v.getClass().isArray()) {
        Iterator<?> it = IteratorUtils.getIterator(v);
        Object value = it.hasNext() ? it.next() : null;
        return it.hasNext() ? v : value;
      } else if (v instanceof JSONArray) {
        JSONArray array = (JSONArray)v;
        if (array.length() == 0) {
          return null;
        } else if (array.length() == 1) {
          try {
            return array.get(0);
          } catch (Exception var4) {
            throw new SystemException("Error converting json array.", new Object[]{var4});
          }
        } else {
          return v;
        }
      } else {
        return v;
      }
    }

    public final T convert(Object v) {
      if (v == null) {
        return null;
      } else {
        v = this.getCollectionValue(v);
        if (v == null) {
          return null;
        } else {
          T res = this.convertInt(v);
          if (res != null) {
            return res;
          } else if (v instanceof String) {
            String s = (String)v;
            return StringUtils.isBlank(s) ? null : this.convertString(s);
          } else {
            return this.convertObject(v);
          }
        }
      }
    }

    protected abstract T convertObject(Object var1);

    protected abstract T convertString(String var1);

    protected Boolean conversionError(Object v) {
      throw new SystemException("Can't convert \"" + v + "\" to " + this.c.getName());
    }

    protected T convertInt(Object v) {
      return this.c.isInstance(v) ? (T)v : null;
    }
  }
}
