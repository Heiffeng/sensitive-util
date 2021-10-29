import java.util.HashMap;
import java.util.Map;

public class 动态开启和关闭字段的脱敏 {

    public static void main(String[] args) throws Exception {

        简单示例.Person person = new 简单示例.Person();
        person.setName("张三");
        person.setMobile("18019295001");
        person.setPassword("PA23235454");
        person.setAddress("上海市松江区佘山镇");

        // 脱敏开关配置
        Map<FieldType,FieldConfig> config = new HashMap<>();
        config.put(FieldType.CHINESE_NAME,new FieldConfig(false));
        config.put(FieldType.MOBILE,new FieldConfig(false));
        config.put(FieldType.ADDRESS,new FieldConfig(value->"<地址被隐藏>"));
        // 执行脱敏方法
        SensitiveUtil.apply(person,config);
        System.out.println(person);

    }


    static class Person{
        @Sensitive(FieldType.CHINESE_NAME)
        private String name;
        @Sensitive(FieldType.MOBILE)
        private String mobile;
        @Sensitive(FieldType.ADDRESS)
        private String address;
        @Sensitive(FieldType.PASSWORD)
        private String password;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
