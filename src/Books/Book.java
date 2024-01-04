package Books;

import java.util.Objects;
import java.util.UUID;

/**
 * @author NickMo
 * @date 2023-12-01 22:45
 * @description 图书类，包含了图书所需要的关键字和方法
 */
public class Book {
    /**
     * uuid 为图书唯一标识符，是此类的实例唯一的标识
     * name 为图书的名字
     * type 为图书的类别
     * num 为图书的数量
     * */
    String uuid;
    String name;
    String type;
    String location;
    int num;

    public Book(String name, String type, int num, String location) {
        setName(name);
        setType(type);
        setNum(num);
        setLocation(location);
        setUuid(UUID.randomUUID().toString());
    }

    public String getUuid() {
        return uuid;
    }

    private void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return Objects.equals(getUuid(), book.getUuid()) && Objects.equals(getName(), book.getName())
                && Objects.equals(getType(), book.getType()) && Objects.equals(getNum(), book.getNum());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getName(), getType(), getNum());
    }

    @Override
    public String toString() {
        return "Book{" +
                "uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", num='" + num + '\'' +
                '}';
    }
}
