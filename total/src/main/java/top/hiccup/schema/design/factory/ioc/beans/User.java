package top.hiccup.schema.design.factory.ioc.beans;

/**
 * Customer
 *
 * @author wenhy
 * @date 2020/10/2
 */
public class User {

    private String name;

    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "[user]: name = " + name + " , age = " + age;
    }

}