import java.util.Arrays;
import java.util.List;

public class 复杂对象脱敏演示 {

    public static void main(String[] args) throws Exception {
        // 这里有一个叫张三的人
        Person zhangsan = new Person();
        zhangsan.setName("张三");
        zhangsan.setMobile("18019295001");
        zhangsan.setPassword("PA23235454");
        zhangsan.setAddress("上海市松江区佘山镇");
        // 这里有一个叫李四的人
        Person lisi = new Person();
        lisi.setName("李四");
        lisi.setMobile("18018732893");
        lisi.setPassword("HAHAHAHAHA");
        lisi.setAddress("上海市嘉定区南翔镇");
        // 李四是张三的朋友
        zhangsan.setFriends(Arrays.asList(lisi));
        // 对张三进行脱敏
        SensitiveUtil.apply(zhangsan);
        System.out.println(zhangsan);
    }


    static class Person{

        private String name;
        @Sensitive(FieldType.MOBILE)
        private String mobile;
        @Sensitive(FieldType.ADDRESS)
        private String address;
        @Sensitive(FieldType.PASSWORD)
        private String password;

        private List<Person> friends;

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

        public List<Person> getFriends() {
            return friends;
        }

        public void setFriends(List<Person> friends) {
            this.friends = friends;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", address='" + address + '\'' +
                    ", password='" + password + '\'' +
                    ", friends=" + friends +
                    '}';
        }
    }


}
