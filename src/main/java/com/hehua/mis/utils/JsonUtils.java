package com.hehua.mis.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * hewenjerry
 */
public class JsonUtils {
    public static String escape(final String str) {
        return JSONValue.escape(str);
    }

    public static JsonBuilder builder() {
        return new JsonBuilder();
    }

    public static class Getter {
        public static String getString(JSONObject obj, Object key, String valIfNull) {
            return tryGetString(obj, valIfNull, key);
        }

        public static String tryGetString(JSONObject obj, String valIfNull, Object key1, Object... keys) {
            if (obj == null) {
                return null;
            }
            Object value = null;
            if (obj.containsKey(key1)) {
                value = obj.get(key1);
            } else if (null != keys) {
                for (Object key : keys) {
                    if (obj.containsKey(key)) {
                        value = obj.get(key);
                    }
                }
            }
            return ObjectUtils.toString(value, valIfNull);
        }

        public static boolean getBool(JSONObject obj, Object key, boolean valIfNull) {
            return tryGetBoolean(obj, valIfNull, key);
        }

        public static Boolean getBoolean(JSONObject obj, Object key, Boolean valIfNull) {
            return tryGetBoolean(obj, valIfNull, key);
        }

        public static Boolean tryGetBoolean(JSONObject obj, Boolean valIfNull, Object key1, Object... keys) {
            Boolean b = BooleanUtils.toBooleanObject(tryGetString(obj, null, key1, keys));
            return b == null ? valIfNull : b;
        }
    }

    public static class JsonBuilder {
        JSONObject object = new JSONObject();

        public JsonBuilder put(Object key, Object value) {
            if (value == null) {
                this.object.put(key, null);
            } else {
                if (value instanceof String || //
                        value instanceof Double || //
                        value instanceof Float || //
                        value instanceof Number || //
                        value instanceof Boolean || //
                        value instanceof JSONAware || //
                        value instanceof Map || //
                        value instanceof List || //
                        value instanceof JSONAware) {
                    this.object.put(key, value);
                } else if (value instanceof Collection) {
                    this.object.put(key, new ArrayList((Collection) value));
                } else {
                    this.object.put(key, JSONValue.escape(value.toString()));
                }
            }
            return this;
        }

        @SuppressWarnings("unchecked")
        public JsonBuilder putIntIfGt0(final Object key, final Integer value) {
            if (null != value && value > 0) {
                this.object.put(key, value);
            }
            return this;
        }

        @SuppressWarnings("unchecked")
        public JsonBuilder putIntIfNotEmpty(final Object key, final Collection value) {
            if (CollectionUtils.isNotEmpty(value)) {
                this.object.put(key, value);
            }
            return this;
        }

        @SuppressWarnings("unchecked")
        public JsonBuilder putIntIfNotEmpty(final Object key, final Map value) {
            if (MapUtils.isNotEmpty(value)) {
                this.object.put(key, value);
            }
            return this;
        }

        @SuppressWarnings("unchecked")
        public JsonBuilder putIntIfNotNull(final Object key, final Integer value) {
            if (null != value) {
                this.object.put(key, value);
            }
            return this;
        }

        @SuppressWarnings("unchecked")
        public JsonBuilder putBooleanIfTrue(final Object key, final boolean value) {
            if (value) {
                this.object.put(key, value);
            }
            return this;
        }

        @SuppressWarnings("unchecked")
        public JsonBuilder putObjIfNotNull(final String key, final Object value) {
            if (value != null) {
                this.object.put(key, value);
            }
            return this;
        }

        @SuppressWarnings("unchecked")
        public JsonBuilder putStringIfNotBlank(final Object key, final String value) {
            if (!StringUtils.isBlank(value)) {
                this.object.put(key, value);
            }
            return this;
        }

        public JSONObject build() {
            return this.object;
        }

        @Override
        public String toString() {
            return build().toString();
        }

    }

    public static JsonParser parser() {
        return new JsonParser();
    }

    public static class JsonParser {

        JSONParser parser = new JSONParser();

        public Object parse(String s) {
            s = StringUtils.trimToNull(s);
            if (null == s) {
                return null;
            }
            try {
                return this.parser.parse(s);
            } catch (final ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

        public JSONObject parseJsonObject(final String s, final boolean allowArray) {
            final Object obj = this.parse(s);
            if (obj == null) {
                return null;
            }
            if (obj instanceof JSONObject) {
                return (JSONObject) obj;
            }

            if (allowArray && obj instanceof JSONArray) {
                final JSONArray array = (JSONArray) obj;
                if (array.size() == 0) {
                    return null;
                }
                final Object first = array.get(0);
                return first == null ? null : (first instanceof JSONObject ? (JSONObject) first : null);
            }
            return null;
        }

        public JSONArray parseJsonArray(final String string, final boolean allowObject) {
            String input = StringUtils.trimToNull(string);
            if (input == null) {
                return null;
            }
            if (allowObject) {
                boolean hasLeftSquareBrackets = input.startsWith("[");
                boolean hasRightSquareBrackets = input.endsWith("]");
                if (!hasLeftSquareBrackets || !hasRightSquareBrackets) {
                    StringBuffer sb = new StringBuffer();
                    if (!hasLeftSquareBrackets) {
                        sb.append("[");
                    }
                    sb.append(input);
                    if (!hasRightSquareBrackets) {
                        sb.append("]");
                    }
                    input = sb.toString();
                }
            }
            final Object obj = this.parse(input);

            if (obj instanceof JSONArray) {// false if obj is null
                return (JSONArray) obj;
            }
            return null;
        }
    }

}
