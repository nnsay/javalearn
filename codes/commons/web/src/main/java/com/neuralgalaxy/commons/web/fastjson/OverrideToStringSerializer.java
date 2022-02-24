package com.neuralgalaxy.commons.web.fastjson;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 修改fastjson string 单例带 "" 引号的问题
 *
 * @author haiker
 */
public class OverrideToStringSerializer implements ObjectSerializer {
    public static final OverrideToStringSerializer instance = new OverrideToStringSerializer();

    @Override
    public void write(JSONSerializer serializer, Object object,
                      Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull();
            return;
        }
        String strVal = object.toString();
        if (fieldName == null) {
            out.write(strVal);
        } else {
            out.writeString(strVal);
        }
    }

}
