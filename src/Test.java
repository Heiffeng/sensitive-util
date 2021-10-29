public class Test {

    public static void main(String[] args) throws Exception {
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

class Person{

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

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
