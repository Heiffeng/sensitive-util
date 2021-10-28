public class Test {

    public static void main(String[] args) throws Exception {
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

class Person{

    private String name;
    @Sensitive(FieldType.MOBILE)
    private String mobile;
    @Sensitive(FieldType.ADDRESS)
    private String address;

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
}
