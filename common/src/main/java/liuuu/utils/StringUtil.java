package liuuu.utils;

public class StringUtil {
    public static boolean isBlank(String s) {
        if (s == null || s.equals("")) {
            return false;
        }
        for (char c : s.toCharArray()) {
            if (!Character.isWhitespace(c)) {
                return false;
            }
        }
        return true;
    }

}
