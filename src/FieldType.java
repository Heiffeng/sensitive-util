import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 字段类型枚举
 * @Author: SunAo
 * @Date: 2020/11/24 16:03
 */
public enum FieldType {
    // 手机号码，脱敏后：186****0590
    MOBILE(Convert::mobile),
    // 地址，脱敏后：太原市小店区****
    ADDRESS(Convert::address),
    // 邮件，脱敏后：p**@163.com
    EMAIL(Convert::email),
    // 银行卡，脱敏后：622260**********1234
    BANKCARD(Convert::bankCard),
    // 身份证，脱敏后：140101*******1234
    IDCARD(Convert::idCard),
    // 密码，脱敏后：******
    PASSWORD(Convert::all),
    // 中文名，脱敏后：张*伦
    CHINESE_NAME(Convert::chineseName);

    // 默认脱敏实现
    private Function<String,String> defaultFunction;

    FieldType(Function<String, String> defaultFunction) {
        this.defaultFunction = defaultFunction;
    }

    public String convert(String value, FieldConfig fieldConfig){
        // 如果此字段不需要脱敏，则字节返回。
        if(fieldConfig != null && fieldConfig.getNeed() != null && fieldConfig.getNeed() == Boolean.FALSE){
            return value;
        }
        // 如果存在自定义脱敏规则，则使用自定义，否则使用默认。
        Function<String, String> function =
                (fieldConfig != null && fieldConfig.getFunction() != null) ? fieldConfig.getFunction() : defaultFunction;
        return function.apply(value);
    }


    private final static Map<String,FieldType> fieldTypeMap = new HashMap<>();
    static{
        fieldTypeMap.put("mobile",MOBILE);
        fieldTypeMap.put("address",ADDRESS);
        fieldTypeMap.put("email",EMAIL);
        fieldTypeMap.put("bankcard",BANKCARD);
        fieldTypeMap.put("idcard",IDCARD);
        fieldTypeMap.put("password",PASSWORD);
        fieldTypeMap.put("chineseName",CHINESE_NAME);
    }
    public static FieldType parseFieldType(String fieldName){
        if(fieldTypeMap.containsKey(fieldName)){
            return fieldTypeMap.get(fieldName);
        }
        return null;
    }

}
