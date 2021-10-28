import java.util.function.Function;

/**
 * 字段类型枚举
 * @Author: SunAo
 * @Date: 2020/11/24 16:03
 */
public enum FieldType {
    // 手机号码
    MOBILE((value)->Convert.convertMobile(value)),
    // 地址
    ADDRESS((value)->Convert.convertAddress(value)),
    // 邮件
    EMAIL((value)->Convert.convertEmail(value));

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

}
