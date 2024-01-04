import Books.Book;
import Controller.Controller;
import Static.Static;
import User.User;
import Util.Output;
import User.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author NickMo
 * @date 2023-12-01 22:16
 * @description 程序入口Main函数
 */
public class Main {
    public static void main(String[] args) {

        //Output.outputUserTable();
        //Output.outputBookTable();
        final Boolean[] flag = {true};
        do {
            Map<String, Output.menuOutput.Func> funcMap = new HashMap<>();
            funcMap.put("登录", () -> {
                System.out.print("请输入用户名:");

                Scanner input = new Scanner(System.in);
                String name = input.next();

                List<User> users = Controller.getInstance().getDatabaseUtil_Obj().queryUser(Static.DATABASE_USER_COLUMN_NAME, name, false);

                if(!users.isEmpty())
                {
                    for (User user : users) {
                        System.out.println("Welcome!" + user.getUsername());
                    }
                } else {
                    System.out.println("用户不存在");
                    return;
                }
                //判断登录用户权限
                if(users.get(0).getPermissions() == Static.Permissions.ADMINISTRATOR)
                {
                    System.out.println("管理员登录成功");
                    Administrator admin_user = new Administrator();
                    login_admin(admin_user);
                } else if(users.get(0).getPermissions() == Static.Permissions.SUPERUSER) {
                    System.out.println("超级用户登录成功");
                    //Output.userMenuOutput.submitAndExecute();
                } else if(users.get(0).getPermissions() == Static.Permissions.GUEST){
                    System.out.println("用户登录成功");
                }
            });

            funcMap.put("注册", () -> {
                System.out.println("注册");
            });

            funcMap.put("忘记密码", () -> {
                System.out.println("忘记密码");
            });

            //退出这个dowhile循环
            funcMap.put("退出", () -> {
                System.out.println("退出");
                flag[0] = false;
            });

            Output.menuOutput.submitAndExecute(funcMap);
        }while (flag[0]);

    }

    public static void login_admin(Administrator admin_user){
        //管理员登录成功，输出管理员菜单
        final Boolean[] flag = {true};
        do {
            Map<String, Output.menuOutput.Func> funcMap = new HashMap<>();
            Scanner input = new Scanner(System.in);
            funcMap.put("添加书籍", () -> {
                CopyOnWriteArrayList<Book> books = new CopyOnWriteArrayList<Book>();
                System.out.println("请输入书籍名称:");
                String name = input.next();
                System.out.println("请输入书籍作者:");
                String author = input.next();
                System.out.println("请输入书籍位置:");
                String location = input.next();
                System.out.println("请输入书籍数量:");
                int count = input.nextInt();

                Book book = new Book(name, author, count, location);
                books.add(book);


                if (admin_user.addBooks(books)) {
                    System.out.println(book.getName() + " 添加成功");
                } else {
                    System.out.println(book.getName() + " 添加失败");
                }

            });

            funcMap.put("删除书籍", () -> {
                CopyOnWriteArrayList<Book> books = new CopyOnWriteArrayList<Book>();
                Output.outputBookTable();
                System.out.println("请输入要删除的书籍的uuid,如有多个请用英文状态逗号隔开:");

                String uuid = input.next();
                //用,分割字符串取出每一个uuid
                String[] uuids = uuid.split(",");
                for (String s : uuids) {
                    Book book = new Book(s, null, 0, null);
                    books.add(book);
                }

                Map<Book, Boolean> resultMap = admin_user.delBooks(books);
                //判断每一个书籍是否删除成功
                for (Map.Entry<Book, Boolean> entry : resultMap.entrySet()) {
                    if (entry.getValue()) {
                        System.out.println("删除成功");
                    } else {
                        System.out.println("删除失败");
                    }
                }
            });
            //添加用户
            funcMap.put("添加用户", () -> {
                System.out.println("请输入用户名:");
                String username = input.next();
                System.out.println("请输入手机:");
                String phone = input.next();
                System.out.println("请输入权限:");
                int permissions = input.nextInt();
                Guest guest = new Guest(username, phone, permissions);
                if (admin_user.addUser(guest)) {
                    System.out.println(guest.getUsername() + " 添加成功");
                } else {
                    System.out.println(guest.getUsername() + " 添加失败");
                }
            });
            //删除用户
            funcMap.put("删除用户", () -> {
                Output.outputUserTable();
                System.out.println("请输入要删除的用户的uuid,如有多个请用英文状态逗号隔开:");
                String uuid = input.next();
                //用,分割字符串取出每一个uuid
                String[] uuids = uuid.split(",");
                for (String s : uuids) {
                    if(admin_user.delUser(s))
                    {
                        System.out.println(s + " 删除成功");
                    }else {
                        System.out.println(s + " 删除失败");
                    }
                }
            });

            //减少书籍数量
            funcMap.put("减少书籍数量", () -> {
                Output.outputBookTable();
                System.out.println("请输入要减少数量的书籍的uuid,如有多个请用英文状态逗号隔开:");
                String uuid = input.next();
                //用,分割字符串取出每一个uuid
                String[] uuids = uuid.split(",");
                CopyOnWriteArrayList<Book> books = new CopyOnWriteArrayList<Book>();
                for (String s : uuids) {
                    books.add(new Book(s, null, 0,  null));
                }

                Map<Book, Boolean> map =  admin_user.decBooks(books);
                //判断每一个书籍是否删除成功 并输入xx书籍 成功失败
                for (Map.Entry<Book, Boolean> entry : map.entrySet()) {
                    if (entry.getValue()) {
                        System.out.println(entry.getKey().getName() + " 减少成功");
                    } else {
                        System.out.println(entry.getKey().getName() + " 减少失败");
                    }
                }
            });
            //增加书籍数量
            funcMap.put("增加书籍数量", () -> {
                Output.outputBookTable();
                System.out.println("请输入要增加数量的书籍的uuid,如有多个请用英文状态逗号隔开:");
                String uuid = input.next();
                //用,分割字符串取出每一个uuid
                String[] uuids = uuid.split(",");
                CopyOnWriteArrayList<Book> books = new CopyOnWriteArrayList<Book>();
                for (String s : uuids) {
                    books.add(new Book(s, null,  0, null));
                }

                Map<Book, Boolean> map =  admin_user.incBooks(books);
                //判断每一个书籍是否删除成功 并输入xx书籍 成功失败
                for (Map.Entry<Book, Boolean> entry : map.entrySet()) {
                    if (entry.getValue()) {
                        System.out.println(entry.getKey().getName() + " 增加成功");
                    } else {
                        System.out.println(entry.getKey().getName() + " 增加失败");
                    }
                }
            });
            //处理书籍借阅
            funcMap.put("处理书籍借阅", () -> {
                Output.outputBookTable();
                System.out.println("请输入要处理借阅的书籍的uuid,如有多个请用英文状态逗号隔开:");
            });


            //处理书籍归还
            funcMap.put("处理书籍归还", () -> {
                Output.outputBookTable();
                System.out.println("请输入要处理归还的书籍的uuid,如有多个请用英文状态逗号隔开:");
            });

            //查看所有书籍
            funcMap.put("查看所有书籍", Output::outputBookTable);
            //查看所有用户
            funcMap.put("查看所有用户", Output::outputUserTable);

            //退出这个dowhile循环
            funcMap.put("退出", () -> {
                System.out.println("退出");
                flag[0] = false;
            });


            Output.menuOutput.submitAndExecute(funcMap);


        } while (flag[0]);



    }



}
