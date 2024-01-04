package Util;

/**
 * @author NickMo
 * @date 2023-12-02 19:59
 */
public class Algorithm {
    private final static Algorithm INSTANCE = new Algorithm();

    public static Algorithm getInstance() {
        return INSTANCE;
    }

    private Algorithm() {}

    public int[] kmpNext(String dest){
        int[] next = new int[dest.length()];
        next[0] = 0;

        for(int i = 1,j = 0; i < dest.length(); i++){
            while(j > 0 && dest.charAt(j) != dest.charAt(i)){
                j = next[j - 1];
            }
            if(dest.charAt(i) == dest.charAt(j)){
                j++;
            }
            next[i] = j;
        }
        return next;
    }

    /**
     * @description 该函数的作用是匹配长字符串str中是否包含子串dest
     * */
    public boolean kmp(String str, String dest){

        int[] next = getInstance().kmpNext(dest);

        for(int i = 0, j = 0; i < str.length(); i++){
            while(j > 0 && str.charAt(i) != dest.charAt(j)){
                j = next[j-1];
            }
            if(str.charAt(i) == dest.charAt(j)){
                j++;
            }
            if(j == dest.length()){
                return true;
            }
        }
        return false;
    }
}
