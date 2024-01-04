package Util;
import Database.Database;
import Static.Static;
import User.User;

import java.util.*;

import Util.Table;
public class Output {

    //输出用户信息
    public static void outputUsers(List<User> users) {
        try {
            String[] headers = {"uuid", "username", "phone","permissions"};
            String[][] rows = new String[users.size()][4];
            for (int i = 0; i < users.size(); i++) {
                rows[i][0] = users.get(i).getUuid();
                rows[i][1] = users.get(i).getUsername();
                rows[i][3] = users.get(i).getPermissions().toString();
                rows[i][2] = users.get(i).getPhone();
            }
            Table table = new Table(headers,rows);
            table.print();
        } catch (NullPointerException e) {
            System.out.println("没有用户");
        }
    }

    //打印数据库中所有Book信息
    public static void outputBookTable()
    {
        List<String> headers = Database.getInstance().getAllColumns("tb_Book");
        List<String> IDrowList = Database.getInstance().getRow ("tb_Book","id", String.class);
        List<String> uuidrowList = Database.getInstance().getRow ("tb_Book","uuid", String.class);
        List<String> namerowList = Database.getInstance().getRow ("tb_Book","name", String.class);
        List<String> typerowList = Database.getInstance().getRow ("tb_Book","type", String.class);
        List<String> locationrowList = Database.getInstance().getRow ("tb_Book","location", String.class);
        List<Integer> numrowList = Database.getInstance().getRow ("tb_Book","num", Integer.class);

        String[][] rows = new String[IDrowList.size()][headers.size()];
        for (int i = 0; i < IDrowList.size(); i++) {
            for (int j = 0; j < headers.size(); j++) {
                switch (headers.get(j)) {
                    case "id" -> rows[i][j] = IDrowList.get(i);

                    case "uuid" -> rows[i][j] = uuidrowList.get(i);

                    case "name" -> rows[i][j] = namerowList.get(i);

                    case "type" -> rows[i][j] = typerowList.get(i);

                    case "location" -> rows[i][j] = locationrowList.get(i);

                    case "num" -> rows[i][j] = numrowList.get(i).toString();
                }

            }
        }
        String[] headers1 = new String[headers.size()];
        for (int i = 0; i < headers.size(); i++) {
            headers1[i] = headers.get(i);
        }
        Table table = new Table(headers1,rows);
        table.print();
    }


    /**
     * 打印数据库中所有User信息
     */
    public static void outputUserTable()
    {
        List<String> headers = Database.getInstance().getAllColumns("tb_User");
        List<String> IDrowList = Database.getInstance().getRow ("tb_User","id", String.class);
        List<String> uuidrowList = Database.getInstance().getRow ("tb_User","uuid", String.class);
        List<String> usernamerowList = Database.getInstance().getRow ("tb_User","username", String.class);
        List<String> phonerowList = Database.getInstance().getRow ("tb_User","phone", String.class);
        List<Integer> permissionsrowList = Database.getInstance().getRow ("tb_User","Permissions", Integer.class);

        String[][] rows = new String[IDrowList.size()][headers.size()];
        for (int i = 0; i < IDrowList.size(); i++) {
            for (int j = 0; j < headers.size(); j++) {
                switch (headers.get(j)) {
                    case "id" -> rows[i][j] = IDrowList.get(i);

                    case "uuid" -> rows[i][j] = uuidrowList.get(i);

                    case "username" -> rows[i][j] = usernamerowList.get(i);

                    case "phone" -> rows[i][j] = phonerowList.get(i);

                    case "Permissions" -> rows[i][j] = Static.Permissions.values()[permissionsrowList.get(i)].name();
                }

            }
            //System.out.println(Static.Permissions.values()[permissionsrowList.get(i)].name());
        }
        String[] headers1 = new String[headers.size()];
        for (int i = 0; i < headers.size(); i++) {
            headers1[i] = headers.get(i);
        }
        Table table = new Table(headers1,rows);
        table.print();
    }


    public  static <T> void outputList(List<T> rowList){

        String[][] rows = new String[rowList.size()][1];
        for (int i = 0; i < rowList.size(); i++) {
            if(rowList.get(i) == null)
            {
                rows[i][0] = "null";
                continue;
            }
            rows[i][0] = rowList.get(i).toString();
        }
        Table table = new Table(rows);
        table.print();

    }

    public static class menuOutput {

        @FunctionalInterface
        public interface Func {
            void Execute();
        }

        public static void submitAndExecute(Map<String, Func> param) {

            List<String> keyList = new ArrayList<>(param.keySet());

            for (int i = 0; i < keyList.size(); i++) {
                System.out.println((i + 1) + ". " + keyList.get(i));
            }

            Scanner input = new Scanner(System.in);

//            int selectedOption;
//            do {
//                System.out.print("请输入你的选择: ");
//                while (!input.hasNextInt()) {
//                    System.out.println("错误输入! 请输入菜单对应的序号!");
//                    input.next(); // 跳过无效输入
//                }
//                selectedOption = input.nextInt();
//            } while (selectedOption < 1 || selectedOption > keyList.size());

            int selectedOption = 0;
            do {
                System.out.print("请输入你的选择: ");
                while (!input.hasNextLine()) {
                    input.nextLine(); // 跳过无效输入
                }
                String line = input.nextLine();
                try {
                    selectedOption = Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    System.out.println("错误输入! 请输入菜单对应的序号!");
                    continue;
                }
            } while (selectedOption < 1 || selectedOption > keyList.size());

            param.get(keyList.get(selectedOption - 1)).Execute();

            // 关闭Scanner
            //input.close();

        }
    }



}
