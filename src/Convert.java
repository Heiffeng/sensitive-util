public class Convert {


    private final static String DEFAULT_MASK = "*";
    /**
     * 脱敏规则
     * @Param origin 原始字符串
     * @param prefixNoMaskLen 左侧需要保留几位明文字段
     * @param suffixNoMaskLen 右侧需要保留几位明文字段
     * @param maskStr 用于遮罩的字符串, 如'*'
     * @Return 脱敏后结果
     */
    public static String maskValue(String value, int prefixNoMaskLen, int suffixNoMaskLen, String maskStr) {
        if (value == null || value.isEmpty() || value.equals("")) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0, n = value.length(); i < n; i++) {
            if (i < prefixNoMaskLen) {
                sb.append(value.charAt(i));
                continue;
            }
            if (i > (n - suffixNoMaskLen - 1)) {
                sb.append(value.charAt(i));
                continue;
            }
            sb.append(maskStr);
        }
        return sb.toString();
    }

    /**
     * 显示姓和最后一个字，其他隐藏为星号，比如：张*伦
     */
    public static String chineseName(String fullName) {
        if(fullName.length()>2)
            return maskValue(fullName, 1, 1, DEFAULT_MASK);
        else
            return maskValue(fullName, 1, 0, DEFAULT_MASK);
    }
    /**
     * 显示姓和最后一个字，其他隐藏为星号，比如：张**
     */
    public static String chineseNameFirst(String fullName) {
        return maskValue(fullName, 1, 0, DEFAULT_MASK);
    }


    /**
     * 显示前六位, 后四位，其他隐藏。比如：140101*******1234
     */
    public static String idCard(String id) {
        return maskValue(id, 6, 4, DEFAULT_MASK);
    }

    /**
     * 座机号码，显示后四位，其他隐藏，比如 ****1234
     */
    public static String fixedPhone(String num) {
        return maskValue(num, 0, 4, DEFAULT_MASK);
    }

    /**
     * 手机号码，显示前三位、后四位，其他隐藏，比如186****0590
     */
    public static String mobile(String num) {
        return maskValue(num, 3, 4, DEFAULT_MASK);
    }

    /**
     * 地址，只显示到地区，不显示详细地址，比如：太原市小店区****
     */
    public static String address(String address) {
        return maskValue(address, 6, 0, DEFAULT_MASK);
    }

    /**
     * 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示，比如：p**@163.com
     */
    public static String email(String email) {
        int index = email.indexOf('@');
        if (index <= 1) {
            return email;
        }
        return maskValue(email, 1, email.length()-index, DEFAULT_MASK);
    }

    /**
     * 前六位，后四位，其他用星号隐藏每位1个星号，比如：622260**********1234
     */
    public static String bankCard(String cardNum) {
        return maskValue(cardNum, 6, 4, DEFAULT_MASK);
    }

    /**
     * 前两位后一位，比如：晋A****5
     */
    public static String carNumber(String carNumber) {
        return maskValue(carNumber, 2, 1, DEFAULT_MASK);
    }

    /**
     * 全部字符都用*代替，比如：******
     */
    public static String all(String password) {
        return maskValue(password, 0, 0, DEFAULT_MASK);
    }

    /**
     * 密钥除了最后三位，全部都用*代替，比如：***xdS 脱敏后长度为6，如果明文长度不足三位，则按实际长度显示，剩余位置补*
     */
    public static String key(String key) {
        return maskValue(key, 0, 3, DEFAULT_MASK);
    }


}
