package User;

import Books.Book;
import Static.Static;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author NickMo
 * @date 2023-12-01 22:22
 * @description 该类是所有该系统用户的父类，封装了一些基础的字段的和操作
 */
public class User {

    /**
     * uuid 为用户唯一标识符，在该程序中不与其他uuid重复
     * username 为用户名
     * Permissions 为用户权限
     * bookList 为该用户借走的书
     * */
    String uuid;
    String username;
    String phone;
    Static.Permissions Permissions;
    List<Book> bookList = new CopyOnWriteArrayList<Book>();

    public User() {
        setUuid(UUID.randomUUID().toString());
        setPermissions(Static.Permissions.GUEST);
    }

    public User(Static.Permissions permissions) {
        setUuid(UUID.randomUUID().toString());
        setPermissions(permissions);
    }

    public User(String uuid, String phone, String username, Static.Permissions permissions) {
        setPhone(phone);
        setUuid(uuid);
        setUsername(username);
        setPermissions(permissions);
    }

    public User(String uuid, String username, Static.Permissions permissions) {
        setUuid(uuid);
        setUsername(username);
        setPermissions(permissions);
    }



    public User(String uuid,String username, String phone,int permissions) {
        setUuid(uuid);
        setPhone(phone);
        setUsername(username);
        setPermissions(Static.Permissions.values()[permissions]);
    }

    public User(String username, String phone, int permissions) {
        setUuid(UUID.randomUUID().toString());
        setUsername(username);
        setPhone(phone);
        setPermissions(Static.Permissions.values()[permissions]);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUuid() {
        return uuid;

    }
    private void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public Static.Permissions getPermissions() {
        return Permissions;
    }

    public void setPermissions(Static.Permissions permissions) {
        Permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(getUuid(), user.getUuid()) && Objects.equals(getUsername(), user.getUsername()) && Objects.equals(getPhone(), user.getPhone()) && getPermissions() == user.getPermissions();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUuid(), getUsername(), getPhone(), getPermissions());
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid='" + uuid + '\'' +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", Permissions=" + Permissions +
                ", bookList=" + bookList +
                '}';
    }
}