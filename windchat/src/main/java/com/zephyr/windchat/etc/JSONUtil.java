package com.zephyr.windchat.etc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

/**
 * json script을 object로 변환하는 소스 그러나 날짜 등 기본타입이 아닌 경우 변환에 문제가 있어 되도록 사용 금지
 * 
 * @author zephyr
 *
 */
public class JSONUtil {
    /**
     * json을 object로 변경하는 소스
     * 
     * @param target   변경할 object
     * @param resource json string
     */
	public static void callGetterFromJSONString(Object target, String resource) {
        try {
			if (target == null) {
				return;
			}
			JSONObject obj = new JSONObject(resource);
			Iterator<String> it = obj.keys();
			Class<? extends Object> targetClass = target.getClass();
			Method[] methods = targetClass.getMethods();
			while (it.hasNext()) {
				String key = it.next();
				Object value = obj.get(key);
				String setterMethodName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
				for (Method method : methods) {
                    if (method.getName().equals(setterMethodName)) {
						method.invoke(target, value);
						break;
					}
				}

			}
		} catch (JSONException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    /**
     * object를 json으로 변경
     * 
     * @param target 변경할 object
     * @return
     */
	public static JSONObject convertPojoToJSONObject(Object target) {
		JSONObject obj = new JSONObject();
		if (target == null) {
			return null;
		}

		Method[] methods = target.getClass().getDeclaredMethods();
		
		String getPrefix = "get";
		try {
			for (Method method : methods) {
				String methodName = method.getName();
				if (methodName.startsWith(getPrefix)) {

					Object value = method.invoke(target);
					String attrName = methodName.substring(getPrefix.length());
					obj.put(attrName, value);

				}

			}

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			// TODO Auto-generated catch block

			return null;
		}

		return obj;

	}
}
