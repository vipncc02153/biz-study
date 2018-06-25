import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ss on 2018/6/24.
 */
public class BeanToJson {
    public static String MapToJson(Object bean){
        Map<String,String> map = beanToJson(bean);
        if(!map.isEmpty()) {
            String json = "json:{\n";
            for (Map.Entry<String, String> entry : map.entrySet()) {
                json += "    " + entry.getKey() + ":" + entry.getValue() + "\n";
            }
            json += "}";
            return json;
        }else{
            return "undefine";
        }

    }
    public static Map<String,String> beanToJson(Object bean){
        Class<? extends Object> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Map<String, String> result = new HashMap<String,String>();

        PropertyDescriptor pd = null;//new PropertyDescriptor(field.getClass(),clazz);

        Method getMethod = null;//pd.getReadMethod();

        try{
            for (Field field: fields) {
                pd = new PropertyDescriptor(field.getName(), clazz);

                if (null != pd) {

                    getMethod = pd.getReadMethod();
                    Object invoke = getMethod.invoke(bean);
                    if (invoke != null) {
                        if (field.getGenericType().toString().equals("class java.math.BigDecimal")) {
                            invoke = ((BigDecimal) invoke).setScale(6, BigDecimal.ROUND_HALF_DOWN);
                        }
                        result.put(field.getName(), invoke.toString());
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
