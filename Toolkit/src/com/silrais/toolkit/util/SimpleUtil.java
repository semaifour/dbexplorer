package com.silrais.toolkit.util;

public class SimpleUtil {

    public static final int INVALID_PARSE_INT_RESULT = -12345;
        
    public static boolean isSize0 (String str) {
        return str == null ? true : str.trim().length() == 0 ? true : false;
    }

    public static boolean isSize0 (Object obj) {
        return obj == null ? true : obj.toString().trim().length() == 0 ? true : false;
    }

    public static boolean isSize0(Object[] array) {
        return array == null ? true : array.length == 0 ? true : false;
    }

    public static boolean isSizeNot0(Object obj) {
        return !isSize0(obj);
    }

    /**
      * Parses a string to an integer. Return equivalent integer. Returns -12345
      * if error/invalid str.
      *
      */
    public static int parseInt(Object obj) {
        if (!isSize0(obj)) {
            try {
                if (obj instanceof Integer) {
                    return ((Integer) obj).intValue();
                } else {
                    return Integer.parseInt(obj.toString());
                }
            } catch (Exception e) { }
        }
        return INVALID_PARSE_INT_RESULT;
    }

    /**
      * Returns true if parameter points to NULL or the string in it is blank (" ") or null test ("null") or 0 length
      */

    public static boolean isnull(String str) {
        return isSize0(str) ? true : str.trim().equalsIgnoreCase("NULL") ? true : false;
    }

    public static boolean isnotnull(String str) {
        return !isnull(str);
    }


    /**
      * Return a NON-BLANK always. i.e NON-NULL or NULL but never BLANK.
      * 
      * NB - Non Blank
      * 
      * <br>Return null if,<br> 
      *  1. str == null <br>
      *  2. isnull(str) == true <br>
      * Otherwise returns the actual value.<br>
      *
      * <br>Useful scenario: read http parameters, read database cols where the
      * value is often blank spaces or "null" text.
      */
    public static String getNBStr(String str) {
        return getNNRD(str, null); 
    }
    
    /**
     * Returns a NON-NULL always. i.e NON-NULL or BLANK but never NULL.
     * 
     * NN - Not Null
     * 
     * @param str
     * @return
     */
    public static String getNNStr(String str) {
    	return getNNRD(str, "");
    }
    
    /**
     * Returns defaultStr if orgStr is NULL or BLANK. Otherwise just return orgStr.
     * 
     * NNRD - Not Null oR Default
     * 
     * @param orgStr
     * @param defaultStr
     * @return
     */
    public static String getNNRD(String orgStr, String defaultStr) {
       	return isnull(orgStr) ? defaultStr : orgStr;
    }

    public static boolean parseBoolean(Object value) {
        if (isSize0(value)) return false;
        if (value instanceof Boolean) return ((Boolean)value).booleanValue();
        if (value.toString().equalsIgnoreCase("TRUE")) return true;
        if (value.toString().equalsIgnoreCase("1")) return true;
        if (value.toString().equalsIgnoreCase("YES")) return true;
        return false;
    }

    public static boolean contains(int[] src, int val) {
        if (src != null ) { 
            for (int i = 0; i < src.length; i++) {
                return (val == src[i]);
            }
        }
        return false;
    }

}
