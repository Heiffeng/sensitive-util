# sensitive-util

# 简介

`sensitive-util` Java脱敏工具类。

1. 基于注解进行脱敏，也支持非注解形式脱敏。
2. 可以很方便的新增脱敏字段类型。（在`FieldType`中新增枚举值）
3. 可以依据业务配置灵活控制脱敏。比如：每个用户有自己的脱敏字段配置，需要依据客户的配置，灵活脱敏

# 快速开始
1. 将此项目代码copy到自己的项目中。
2. 依据需求，新增或修改`FieldType`类枚举，定义自己的脱敏规则。

## 简单示例
```java
@Data
class Person{
    private String name;
    @Sensitive(FieldType.MOBILE)
    private String mobile;
    @Sensitive(FieldType.ADDRESS)
    private String address;
}

public class Test{
    public static void main(String[] args) {
        Person person = new Person();
        person.setName("张三");
        person.setMobile("18037170703");
        person.setAddress("上海市松江区佘山镇");
        SensitiveUtil.apply(person);
        System.out.println("Name:" + person.getName());
        System.out.println("Mobile:" + person.getMobile());
        System.out.println("Address:" + person.getAddress());
    }
}
```
