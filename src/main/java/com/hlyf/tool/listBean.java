package com.hlyf.tool;


//import net.sf.json.JSONObject;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static com.hlyf.tool.ReflexObjectUtil.getKeysAndValues;


/**
 * Created by Administrator on 2018-03-17.
 */
public class listBean {

    //通用方法    list map 转换成json对象
    @SuppressWarnings("unchecked")
    public static String getBeanJson(List list){
        //TODO  我的注释的这个方法不要删除了  出了问题很严重
//        List<Map<String, Object>> list1=getKeysAndValues(list);
//        List list2 =new ArrayList();
//        for(Map<String, Object> map:list1){
//            //JSONObject jsonObject = JSONObject.fromObject(map);
//
//            list2.add(jsonObject);
//        }
//        return list2.toString();

        return JSONObject.toJSONString(list, SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteMapNullValue);
    }


}
