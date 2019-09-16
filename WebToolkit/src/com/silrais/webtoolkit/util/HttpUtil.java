package com.silrais.webtoolkit.util;


import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import com.silrais.toolkit.util.SimpleUtil;

public class HttpUtil {

    public static String getStrParam(HttpServletRequest req, String key) {
        return SimpleUtil.getNBStr(req.getParameter(key));
    }

    public static String getStrAttr(HttpServletRequest req, String key) {
        return SimpleUtil.getNBStr((String)req.getAttribute(key));
    }


    public static int getIntParam(HttpServletRequest req, String key) {
        return SimpleUtil.parseInt(req.getParameter(key));
    }


    public static int getIntAttr(HttpServletRequest req, String key) {
        return SimpleUtil.parseInt(req.getAttribute(key));
    }

    public static boolean getBooleanParam(HttpServletRequest req, String key) {
        return SimpleUtil.parseBoolean(req.getParameter(key));
    }


    public static boolean getBooleanAttr(HttpServletRequest req, String key) {
        return SimpleUtil.parseBoolean(req.getAttribute(key));
    }

    public static String getStrAttrB4Param(HttpServletRequest req, String key) {
        String tmp = getStrAttr(req, key);
        if (tmp == null) {
            tmp = getStrParam(req, key);
        }
        return tmp;
    }

    public static String getStrParamB4Attr(HttpServletRequest req, String key) {
        String tmp = getStrParam(req, key);
        if (tmp == null) {
            tmp = getStrAttr(req, key);
        }
        return tmp;
    }
    
    public static String[] getStrParams(HttpServletRequest req, String key) {
    	return req.getParameterValues(key);
    }

    public static int getIntAttrB4Param(HttpServletRequest req, String key) {
        int tmp = getIntAttr(req, key);
        if (tmp < 0) {
            tmp = getIntParam(req, key);
        }
        return tmp;
    }

    public static int getIntParamB4Attr(HttpServletRequest req, String key) {
        int tmp = getIntParam(req, key);
        if (tmp < 0) {
            tmp = getIntAttr(req, key);
        }
        return tmp;
    }


    /**
     * Forms a GET method http-parameter string. 
     * 
     * <pre>
     *  
     *  HttpUtil.toHttpQueryString("aaa", "1", "bbb", myObj, "ccc", number);
     * 
     *  The above returns string like "aaa=1&bbb=$myObject.toString()&ccc=$number".
     * 
     * </pre>
     * 
     */
    public static String toHttpQueryString(Object... kv) {
    	StringBuilder builder = new StringBuilder();
    	int index = 0;
    	while (index < (kv.length-1)) {
    		if (SimpleUtil.isSizeNot0(kv[index]) && SimpleUtil.isSizeNot0(kv[index+1])) {
    			builder.append(kv[index].toString()).append("=").append(URLEncoder.encode(kv[index+1].toString())).append("&");
    		}
    		index += 2;
    	}
    	return builder.toString();
    }
    
    
    /**
     *  Returns a GET method http-parameter string for the given keys from the values found in the given http-request object.
     * 
     * It's useful when once wants to append (forward) url parameters to the urls being formed in the current page.
     * 
     *  * <pre>
     *  
     *  HttpUtil.toHttpQueryString(req, "aaa", "bbb", "ccc");
     * 
     *  The above returns string like "aaa=$req.aaa&bbb=$req.bbbb&ccc=$req.ccc" 
     * 
     * </pre>
     * @param req
     * @param keys
     * @return
     */
    public static String toHttpQueryString(HttpServletRequest req, String...keys) {
       	StringBuilder builder = new StringBuilder();
    	for (int index = 0; index < keys.length; index++) {
    		builder.append(keys[index].toString()).append("=").append(URLEncoder.encode(getStrAttrB4Param(req, keys[index]))).append("&");
    	}
    	return builder.toString();
    }
}
