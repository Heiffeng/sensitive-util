/**
 * 脱敏工具
 * @Author: SunAo
 * @Date: 2020/11/20 16:32
 * @Description:
 */
public class Convert {

    public static final String ONE = "*";
    public static final String TWO = "**";
    public static final String FOUR = "****";
    public static final String SIX = "******";

    public static final char X = '*';

    /**
     * 手机号透明
     * @param mobile 手机号
     * @param isOpen 是否为敏感数据
     * @return
     */
    public static String convertMobile(String mobile){
        if( mobile == null || "".equals(mobile) ){
            return mobile;
        }
        if( mobile.length() == 11 ){
            return mobile.substring(0, 3) + FOUR + mobile.substring(7);
        }
        if( mobile.length() > 11 ){
            return mobile.substring(0, 3) + FOUR + mobile.substring(mobile.length()-4);
        }
        if( mobile.length() < 11 ){
            if( mobile.length() <= 2 ){
                return getXXXByLength(mobile.length());
            }
            if( mobile.length() > 2 ){
                return mobile.substring(0,1) + getXXXByLength(mobile.length()-2) + mobile.substring(mobile.length()-1);
            }
        }
        return mobile;
    }

    /**
     * 地址透明
     * @param address 地址
     * @param isOpen 是否为敏感数据
     * @return
     */
    public static String convertAddress(String address){
        if(address == null || "".equals(address)){
            return address;
        }
        return getXXXByLength(address.length());
    }

    /**
     * 邮箱透明
     * @param email 邮箱
     * @param isOpen 是否为敏感数据
     * @return
     */
    public static String convertEmail(String email){
        if( email == null || "".equals(email) ){
            return email;
        }
        int lastIndexOf = email.lastIndexOf("@");
        if( lastIndexOf == -1 ){
            return getXXXByLength(email.length());
        }
        String suffix = email.substring(lastIndexOf);
        String value = email.substring(0, lastIndexOf);
        if( value.length() <= 2 ){
            return  getXXXByLength(value.length()) + suffix;
        }
        if( value.length() >= 3 ){
            return value.subSequence(0, 1) + getXXXByLength(value.length()-2) + value.substring(value.length() - 1) + suffix;
        }
        return email;
    }

    /**
     * 固定电话透明
     * @param telephone 固定电话
     * @param isOpen 是否为敏感数据
     * @return
     */
    public static String convertTelephone(String telephone, boolean isOpen){
        if(isOpen){
            if( telephone == null || "".equals(telephone) ){
                return telephone;
            }
            if(telephone.length()<=4){
                return getXXXByLength(telephone.length());
            }else{
                return getXXXByLength(telephone.length()-4)+telephone.substring(telephone.length()-4);
            }
        }
        return telephone;
    }

    /**
     * 微信号透明
     * @param wx 微信号
     * @param isOpen 是否为敏感数据
     * @return
     */
    public static String convertWx(String wx, boolean isOpen){
        if(isOpen){
            if( wx == null || "".equals(wx) ){
                return wx;
            }
            if( wx.length() <= 2 ){
                return getXXXByLength(wx.length());
            }
            return wx.substring(0,1) + getXXXByLength(wx.length()-2) + wx.substring(wx.length() - 1);
        }
        return wx;
    }

    /**
     * QQ号透明
     * @param qq QQ号
     * @param isOpen 是否为敏感数据
     * @return
     */
    public static String convertQq(String qq, Boolean isOpen){
        if(isOpen){
            if( qq == null || "".equals(qq) ){
                return qq;
            }
            if( qq.length() <= 2 ){
                return getXXXByLength(qq.length());
            }
            return qq.substring(0,1) + getXXXByLength(qq.length()-2) + qq.substring(qq.length() - 1);
        }
        return qq;
    }

    /**
     * 企业微信透明
     * @param qywx 企业微信
     * @param isOpen  是否为敏感数据
     * @return
     */
    public static String convertQywx(String qywx, Boolean isOpen){
        if(isOpen){
            if( qywx == null || "".equals(qywx) ){
                return qywx;
            }
            if( qywx.length() <= 2 ){
                return getXXXByLength(qywx.length());
            }
            return qywx.substring(0,1) + getXXXByLength(qywx.length()-2) + qywx.substring(qywx.length() - 1);
        }
        return qywx;
    }


    private static String getXXXByLength(int length){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(X);
        }
        return sb.toString();
    }
}
