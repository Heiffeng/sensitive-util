import java.util.HashMap;
import java.util.Map;

public class 非注解方式脱敏 {

    public static void main(String[] args) throws Exception {
        Person person = new Person();
        person.setName("洛空");
        person.setMobile("18017648954");
        person.setAddress("杭州市余杭区知北路128号");

        Map<String,Object> params = new HashMap<>();
        params.put("bankcard","62226043567892341234");
        params.put("idcard","14010156783451234");
        person.setParams(params);
        // 脱敏
        SensitiveUtil.parse(person);
        System.out.println(person);
    }


    static class Person{
        private String name;
        private String mobile;
        private String address;
        private Map<String,Object> params;

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

        public Map<String, Object> getParams() {
            return params;
        }

        public void setParams(Map<String, Object> params) {
            this.params = params;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", address='" + address + '\'' +
                    ", params=" + params +
                    '}';
        }
    }
}
