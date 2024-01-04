package Util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Arrays;

public class Table {
    private final String[]  headers;
    private final String[][] rows;

    public Table(){
        this.headers = new String[]{"no headers"};
        this.rows = new String[][]{};
    }

    public Table(String[] headers, String[][] rows) {
        this.headers = headers;
        this.rows = rows;
    }

    public Table(String[] headers) {
        this.headers = headers;
        this.rows = new String[][]{};
    }

    // 构造方法，动态生成默认表头
    public Table(String[][] rows) {
        if (rows.length > 0 && rows[0] != null) {
            int numColumns = rows[0].length;
            this.headers = generateDefaultHeaders(numColumns);
        } else {
            this.headers = new String[]{"no headers"};
        }
        this.rows = rows;
    }

    // 辅助方法，生成默认表头
    private String[] generateDefaultHeaders(int numColumns) {
        String[] defaultHeaders = new String[numColumns];
        for (int i = 0; i < numColumns; i++) {
            defaultHeaders[i] = "Column " + (i + 1);
        }
        return defaultHeaders;
    }

    // 辅助方法，计算字符串的宽度
    private int getStringWidth(String str) {

        /*
        int width = 0;
        for (char c : str.toCharArray()) {
            width += (Character.isIdeographic(c)) ? 2 : 1;
        }
        return width;
        */

//        int width = 0;
//        Matcher matcher = Pattern.compile("[^\\x00-\\xff]").matcher(str);
//
//        while (matcher.find()) {
//            width += 2; // 中文字符宽度为2
//        }
//
//        width += str.length(); // 非中文字符宽度为1
//        return width;

        return str.length();
    }

    // 辅助方法，确保字符串对齐
    private String alignString(String str, int width) {
        StringBuilder result = new StringBuilder();
        int currentWidth = 0;
        for (char c : str.toCharArray()) {
            int charWidth = (Character.UnicodeScript.of(c) == Character.UnicodeScript.HAN) ? 2 : 1;
            currentWidth += charWidth;
            result.append(c);
            if (currentWidth >= width) {
                break;
            }
        }

        return result.toString();
    }

    public void print() {

        // 计算每一列的最大宽度
        int[] columnWidths = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            int finalI = i;

            //这部分使用Java流（streams），其中包含rows数组中第 i 列的每个元素的长度。它将每一行映射到指定列的字符串长度。
            columnWidths[i] = Math.max(headers[i].length(), Arrays.stream(rows)
                    .mapToInt(row -> getStringWidth(row[finalI]))
                    .max()
                    .orElse(0));

        }

        // 打印分割线
        for (int width : columnWidths) {
            System.out.print("+" + "-".repeat(width + 4));
        }
        System.out.println("+");

        // 打印表头
        for (int i = 0; i < headers.length; i++) {
            System.out.printf("| %-"+(columnWidths[i] + 2)+"s ", headers[i]);
        }
        System.out.println("|");

        // 打印分割线
        for (int width : columnWidths) {
            System.out.print("+" + "-".repeat(width + 4));
        }
        System.out.println("+");

        // 打印表格内容
        for (String[] row : rows) {
            for (int i = 0; i < row.length; i++) {
                System.out.printf("| %-"+(columnWidths[i] + 2)+"s ", row[i]);
            }
            System.out.println("|");
        }

        // 打印分割线
        for (int width : columnWidths) {
            System.out.print("+" + "-".repeat(width + 4));
        }
        System.out.println("+");
    }
}
