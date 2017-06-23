package org.iplab.TreesetComparable;

/**
 * Created by js982 on 2017/6/6.
 */
public class Person implements Comparable {
    private String name;
    private int age;

    public Person(String Name, int Age) {
        this.name = Name;
        this.age = Age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public int compareTo(Object obj) {
        Person p = (Person)obj;
        int D_value = this.age - p.age;
        return D_value == 0?this.name.compareTo(p.name):D_value;
    }

    /**
     * 增加判断两个对象是否相同的“重写”方法：hashcode和equal是否都相同
     */

    @Override
    public int hashCode() {
        return name.hashCode() + age * 25;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Person)) {
            throw new ClassCastException("类型错误，请输入Person类型");
        }
        Person p = (Person)obj;
        return this.name.equals(p.name) && this.age==p.age;
    }
}
