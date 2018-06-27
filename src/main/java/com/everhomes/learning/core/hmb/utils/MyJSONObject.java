package training;

import java.lang.reflect.Field;

public class MyJSONObject {
	
	private StringBuilder jsonString = new StringBuilder();

	public static String parseJSONObject(Object object) throws IllegalArgumentException, IllegalAccessException {
		
		MyJSONObject json = new MyJSONObject();

		do {
			// 获取所有属性
			Class oClass = object.getClass();
			Field[] fields = oClass.getDeclaredFields();
			if (null == fields || fields.length == 0) {
				break;
			}

			for (Field field : fields) {
				field.setAccessible(true);
				Object val = field.get(object);
				if (null == val) {
					continue;
				}
				
				json.put(field.getName(), val);
			}

		} while (false);

		return json.toJSONString();
	}
	
	public String toJSONString() {
		return "{"+jsonString.toString()+"}";
	}
	
	private String turnJString(String value) {
		return "\""+value+"\"";
	}
	
	public void put(String key, Object value) {
		if (jsonString.length() > 0) {
			jsonString.append(",");
		}
		
		jsonString.append(turnJString(key));
		jsonString.append(":");
		jsonString.append(getValue(value));
	}
	
	private String getValue(Object value) {
		if (value instanceof Number) {
			return ""+value;
		}
		
		if (value instanceof String) {
			return turnJString((String)value);
		}
		
//		if (value instanceof Object) {
//			try {
//				return parseJSONObject(value);
//			} catch (IllegalArgumentException | IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		
		return value.toString();
	}
	
}
