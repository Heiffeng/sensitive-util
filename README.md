# sensitive-util

# 简介

`sensitive-util` Java脱敏工具类。

1. 基于注解进行脱敏，也支持非注解形式脱敏。
2. 支持复杂对象脱敏，利用反射深度解析对象。
3. 可以很方便的新增脱敏字段类型。（在`FieldType`中新增枚举值）
4. 可以依据业务配置灵活控制脱敏。比如：每个用户有自己的脱敏字段配置，需要依据客户的配置，灵活脱敏

# 快速开始
1. 将此项目代码copy到自己的项目中。
2. 依据需求，新增或修改`FieldType`类枚举，定义自己的脱敏规则。

## 简单示例
```java
@Data
@ToString
class Person{
    private String name;
    @Sensitive(FieldType.MOBILE)
    private String mobile;
    @Sensitive(FieldType.ADDRESS)
    private String address;
    @Sensitive(FieldType.PASSWORD)
    private String password;
}

public class Test{
    public static void main(String[] args) {
        Person person = new Person();
        person.setName("张三");
        person.setMobile("18019295001");
        person.setPassword("PA23235454");
        person.setAddress("上海市松江区佘山镇");
        // 执行脱敏方法
        SensitiveUtil.apply(person);
        System.out.println(person);
    }
}
```
输出结果：`Person{name='张三', mobile='180****5001', address='上海市松江区***', password='**********'}`


# 更多示例

## 复杂对象脱敏演示
张三有个朋友叫李四。用`SensitiveUtil.apply(zhangsan);`脱敏张三的信息时。
李四这个朋友作为一个List<Person>集合存在与张三这个对象中，也会被脱敏。
`SensitiveUtil`工具类使用反射深度扫描`zhangsan`这个对象中一切被`@Sensitive`注解的字段。

```java
public class 复杂对象脱敏演示 {
    public static void main(String[] args) throws Exception {
        Person zhangsan = new Person();
        zhangsan.setName("张三");
        zhangsan.setMobile("18019295001");
        zhangsan.setPassword("PA23235454");
        zhangsan.setAddress("上海市松江区佘山镇");

        Person lisi = new Person();
        lisi.setName("李四");
        lisi.setMobile("18018732893");
        lisi.setPassword("HAHAHAHAHA");
        lisi.setAddress("上海市嘉定区南翔镇");

        zhangsan.setFriends(Arrays.asList(lisi));
        SensitiveUtil.apply(zhangsan);
        System.out.println(zhangsan);
    }

    @Data
    @ToString
    static class Person {
        private String name;
        @Sensitive(FieldType.MOBILE)
        private String mobile;
        @Sensitive(FieldType.ADDRESS)
        private String address;
        @Sensitive(FieldType.PASSWORD)
        private String password;

        private List<Person> friends;
    }
}

```
`Person{name='张三', mobile='180****5001', address='上海市松江区***', password='**********', friends=[Person{name='李四', mobile='180****2893', address='上海市嘉定区***', password='**********', friends=null}]}`
## 非注解方式脱敏

## 自定义字段脱敏

## 根据业务动态配置脱敏字段


