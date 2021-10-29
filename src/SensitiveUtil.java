import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 脱敏工具类
 * 通过配置Config实现手机号电话,QQ微信邮箱，地址等脱敏开关
 * 当Config全部是关闭状态时，调用不做任何操作
 * @Author: SunAo
 * @Date: 2020/11/20 17:38
 */
public class SensitiveUtil<T> {

    // 需要脱敏的数据
    private T data;

    // 脱敏开关配置
    private Map<FieldType,FieldConfig> config;

    private SensitiveUtil(T data, Map<FieldType,FieldConfig> config) {
        this.data = data;
        this.config = config;
    }

    /**
     * 调用此方法进行脱敏
     * @param data 需要脱敏的数据
     * @param config 脱敏配置
     * @param <T>
     * @throws Exception
     */
    public static <T> void apply(T data, Map<FieldType,FieldConfig> config) throws Exception {
        new SensitiveUtil<T>(data,config).convert();
    }
    public static <T> void apply(T data) throws Exception {
        new SensitiveUtil<T>(data,new HashMap<>()).convert();
    }

    /**
     * 不使用注解的方式脱敏
     * @param data
     * @param config
     * @param fieldTypeFunction
     * @param <T>
     * @throws Exception
     */
    public static <T> void parse(T data, Map<FieldType,FieldConfig> config, Function<String,FieldType> fieldTypeFunction) throws Exception {
        WithoutAnnotationUtil.create(data,config,fieldTypeFunction).convert();
    }
    public static <T> void parse(T data, Map<FieldType,FieldConfig> config) throws Exception {
        WithoutAnnotationUtil.create(data,config,FieldType::parseFieldType).convert();
    }
    public static <T> void parse(T data, Function<String,FieldType> fieldTypeFunction) throws Exception {
        WithoutAnnotationUtil.create(data,new HashMap<>(),fieldTypeFunction).convert();
    }
    public static <T> void parse(T data) throws Exception {
        WithoutAnnotationUtil.create(data,new HashMap<>(),FieldType::parseFieldType).convert();
    }

    /**
     * 开始做脱敏工作
     */
    public void convert() throws Exception {
        convert(data);
    }

    private <T> void convert(T data) throws Exception {
        if(data == null) {
            return;
        }
        if(data instanceof Collection){
            Collection collection = (Collection) data;
            if(collection != null && !collection.isEmpty()){
                for (Object item : collection) {
                    convert(item);
                }
            }
        }else if(data instanceof Map){
            Map map = (Map) data;
            if(map != null && !map.isEmpty()){
                for (Object key : map.keySet()) {
                    convert(map.get(key));
                }
            }
        }else{
            convertObject(data);
        }
    }

    private <T> void convertObject(T data) throws Exception {
        if(data == null) {
            return;
        }
        Class<? extends Object> clazz = data.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            DataMethods methods = null;
            // 放过基本数据类型
            if(field.getType().isPrimitive()){
                continue;
            }
            if(field.getType().equals(String.class)){
                // 判断字段是否被注解了
                Sensitive fieldType = field.getAnnotation(Sensitive.class);
                if(fieldType == null){
                    continue;
                }
                methods = DataMethods.init(data,field);
                if(methods.isNull()){
                    continue;
                }
                String value = (String) methods.getMethod.invoke(data);
                if(value != null && !value.isEmpty() && !value.trim().equals("")){
                    FieldType type = fieldType.value();
                    methods.setMethod.invoke(data,type.convert(value,config.get(type)));
                }
            }
            // 判断字段是否是POJO对象或者Collection或Map
            if(Collection.class.isAssignableFrom(field.getType())){
                methods = DataMethods.init(data,field);
                if(methods.isNull()) continue;
                Collection collection = (Collection) methods.getMethod.invoke(data);
                if(collection != null && !collection.isEmpty()){
                    for (Object item : collection) {
                        convert(item);
                    }
                }
            }else if(Map.class.isAssignableFrom(field.getType())){
                methods = DataMethods.init(data,field);
                if(methods.isNull()) continue;
                Map map = (Map) methods.getMethod.invoke(data);
                if(map != null && !map.isEmpty()){
                    for (Object key : map.keySet()) {
                        convert(map.get(key));
                    }
                }
            }else if(!field.getType().getName().startsWith("java.")){
                methods = DataMethods.init(data,field);
                if(methods.isNull()) continue;
                Object obj = methods.getMethod.invoke(data);
                if(null != obj){
                    convertObject(obj);
                }
            }
        }
    }

    private static class DataMethods{
        Method getMethod;
        Method setMethod;
        public static <T> DataMethods init(T data, Field field){
            DataMethods dataMethods = new DataMethods();
            for (Method method : data.getClass().getMethods()) {
                method.setAccessible(true);
                if(!method.getName().startsWith("get")&&!method.getName().startsWith("set")){
                    continue;
                }
                if(method.getName().equalsIgnoreCase("get"+field.getName())&&method.getParameterCount()==0){
                    dataMethods.getMethod = method;
                }
                if(method.getName().equalsIgnoreCase("set"+field.getName())&&method.getParameterCount()==1){
                    dataMethods.setMethod = method;
                }
            }
            return dataMethods;
        }
        public boolean isNull(){
            return getMethod == null || setMethod == null;
        }
    }


    /**
     * 脱敏工具类,不使用注解的方式实现
     * @param <T>
     */
    public static class WithoutAnnotationUtil<T>{

        // 需要脱敏的数据
        private T data;

        // 脱敏开关配置
        private Map<FieldType,FieldConfig> config;

        private Function<String,FieldType> fieldTypeFunction;

        private WithoutAnnotationUtil(T data, Map<FieldType,FieldConfig> config, Function<String,FieldType> fieldTypeFunction) {
            this.data = data;
            this.fieldTypeFunction = fieldTypeFunction;
            this.config = config;
        }

        /**
         * 生成工具类
         * @param data 需要脱敏的数据
         * @param config 脱敏开关配置
         * @param <T>
         * @return
         */
        public static <T> WithoutAnnotationUtil<T> create(T data, Map<FieldType,FieldConfig> config, Function<String,FieldType> fieldTypeFunction){
            return new WithoutAnnotationUtil<T>(data,config,fieldTypeFunction);
        }

        public void convert() throws Exception {
            convert(data);
        }

        private <T> void convert(T data) throws Exception {
            if(data == null) {
                return;
            }
            if(data instanceof Collection){
                Collection collection = (Collection) data;
                if(collection != null && !collection.isEmpty()){
                    for (Object item : collection) {
                        convert(item);
                    }
                }
            }else if(data instanceof Map){
                Map map = (Map) data;
                if(map != null && !map.isEmpty()){
                    for (Object key : map.keySet()) {
                        convert(map.get(key));
                    }
                }
            }else{
                convertObject(data);
            }
        }

        /**
         * 脱敏一个POJO对象
         * @param data 需要脱敏的数据
         * @param <T>
         * @throws Exception
         */
        private <T> void convertObject(T data) throws Exception {
            Class<? extends Object> clazz = data.getClass();
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                method.setAccessible(true);
                boolean isZeroParameter = method.getParameterCount() == 0;
                boolean isGetMethod = method.getName().startsWith("get");
                boolean isReturnVoid = method.getReturnType().getName().equals("void");
                boolean isPrimitiveReturn = method.getReturnType().isPrimitive();
                if(isZeroParameter && isGetMethod && !isReturnVoid && !isPrimitiveReturn) {
                    if(method.getReturnType().equals(String.class)){
                        FieldType fieldType = fieldTypeFunction.apply(method.getName().replace("get","").toLowerCase());
                        if(fieldType != null){
                            String value = (String) method.invoke(data);
                            if(value != null && !value.isEmpty()){
                                Method setMethod = clazz.getMethod(method.getName().replace("get", "set"), String.class);
                                setMethod.invoke(data,fieldType.convert(value,config.get(fieldType)));
                            }
                        }
                    }else if(Collection.class.isAssignableFrom(method.getReturnType())) {
                        Collection collection = (Collection) method.invoke(data);
                        if(collection != null && !collection.isEmpty()){
                            for (Object item : collection) {
                                convert(item);
                            }
                        }
                    }else if(Map.class.isAssignableFrom(method.getReturnType())){
                        Map map = (Map) method.invoke(data);
                        if(map != null && !map.isEmpty()){
                            for (Object key : map.keySet()) {
                                convert(map.get(key));
                            }
                        }
                    }else if(!method.getReturnType().getPackage().getName().startsWith("java.")){
                        Object obj = method.invoke(data);
                        if(null != obj){
                            convertObject(obj);
                        }
                    }
                }
            }
        }
    }
}
