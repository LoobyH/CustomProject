package com.goldmantis.wb.viewdemo.utils;


import android.util.Log;

import com.goldmantis.wb.viewdemo.model.ModeBeen;
import com.goldmantis.wb.viewdemo.model.PostPic;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * gson工具类，配合gson使用
 *
 * @author wuch
 */
public class GsonUtils {

    private static final String TAG = "JSON ERROR!";

    public static String EMPTY_JSON_OBJECT = "{}";

    public static String EMPTY_JSON_ARRAY = "[]";

    private static GsonBuilder builder;

    private static Gson gson;

    private static GsonBuilder builderExclude;
    
    private static Gson gsonExclude;
    
    static {
        builder = new GsonBuilder();
        gson = builder.create();
        
        builderExclude = new GsonBuilder();
        gsonExclude = builderExclude.excludeFieldsWithoutExposeAnnotation().create();
    }
    

    /**
     * json序列化
     *
     * @param target 对象
     * @return 失败会返回{}或者[]
     */
    public static String toJson(Object target) {
        String result = EMPTY_JSON_OBJECT;
        try {
            if (target != null) {
                result = getGson(target.getClass()).toJson(target);
            }
        } catch (Exception e) {
            if (target instanceof Collection<?>
                    || target instanceof Iterator<?>
                    || target instanceof Enumeration<?>
                    || target.getClass().isArray()) {
                result = EMPTY_JSON_ARRAY;
            } else {
                result = EMPTY_JSON_OBJECT;
            }

        }
        return result;
    }
    
    /**
     * json反序列化
     *
     * @param <T>
     * @param source
     * @param cls
     * @return
     */
    public static <T> T fromJson(String source, com.google.common.reflect.TypeToken<ModeBeen<PostPic>> cls) {
        try {
           return (T)getGson().fromJson(new StringReader(source), (Type) cls);
        } catch (Exception e) {
        }

        return null;
    }

    /**
     * json反序列化
     *
     * @param source
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String source, TypeToken<T> type) {
        try {
            JsonReader reader = new JsonReader(new StringReader(source));
            reader.setLenient(true);
        	//return (T)getGson().fromJson(new StringReader(source), type.getType());
        	return (T)getGson().fromJson(reader, type.getType());
        } catch (Exception e) {
            Log.e("HQQ","fromJson:  "+e.toString()+ " mess  "+ e.getMessage());
        }

        return  null;
    }

//    /**
//     * json反序列化
//     *
//     * @param reader
//     * @param type
//     * @param <T>
//     * @return
//     */
//    public static <T> T fromJson(Reader reader, TypeToken<T> type) {
//        try {
//        	return (T)getGson().fromJson(reader, type.getType());
//        } catch (Exception e) {
//        }
//
//        return null;
//    }

    private static Gson getGson() {
        return gson;
    }
    
    private static Gson getGson(Class<?> clazz) {
        if (clazz.getAnnotation(GsonExclude.class) != null) {
            return gsonExclude;
        }else{
            return gson;
        }
    }


}
