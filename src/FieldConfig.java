import java.util.function.Function;

/**
 * 字段配置
 * @Author: SunAo
 */
public class FieldConfig {

    // 该字段是否需要脱敏
    private Boolean need;
    // 脱敏代码实现，在FieldType枚举中声明字段类型时会有一个默认脱敏方法实现。
    // 某些业务场景中，可能会依据不同的场景有不同的脱敏规则
    // 比如平台系统中，用户存在多个机构，每个机构需要有不同的脱敏规则。
    private Function<String,String> function;

    public Boolean getNeed() {
        return need;
    }

    public void setNeed(Boolean need) {
        this.need = need;
    }

    public Function<String, String> getFunction() {
        return function;
    }

    public void setFunction(Function<String, String> function) {
        this.function = function;
    }
}
