package com.xunxin.util.app;

public class EscapeUnescape {
	
	public static String escape(String src) {  
        int i;  
        char j;  
        StringBuffer tmp = new StringBuffer();  
        tmp.ensureCapacity(src.length() * 6);  
        for (i = 0; i < src.length(); i++) {  
            j = src.charAt(i);  
            if (Character.isDigit(j) || Character.isLowerCase(j)  
                    || Character.isUpperCase(j))  
                tmp.append(j);  
            else if (j < 256) {  
                tmp.append("%");  
                if (j < 16)  
                    tmp.append("0");  
                tmp.append(Integer.toString(j, 16));  
            } else {  
                tmp.append("%u");  
                tmp.append(Integer.toString(j, 16));  
            }  
        }  
        return tmp.toString();  
    }  
 
    public static String unescape(String src) {  
        StringBuffer tmp = new StringBuffer();  
        tmp.ensureCapacity(src.length());  
        int lastPos = 0, pos = 0;  
        char ch;  
        while (lastPos < src.length()) {  
            pos = src.indexOf("%", lastPos);  
            if (pos == lastPos) {  
                if (src.charAt(pos + 1) == 'u') {  
                    ch = (char) Integer.parseInt(src  
                            .substring(pos + 2, pos + 6), 16);  
                    tmp.append(ch);  
                    lastPos = pos + 6;  
                } else {  
                    ch = (char) Integer.parseInt(src  
                            .substring(pos + 1, pos + 3), 16);  
                    tmp.append(ch);  
                    lastPos = pos + 3;  
                }  
            } else {  
                if (pos == -1) {  
                    tmp.append(src.substring(lastPos));  
                    lastPos = src.length();  
                } else {  
                    tmp.append(src.substring(lastPos, pos));  
                    lastPos = pos;  
                }  
            }  
        }  
        return tmp.toString();  
    }  
    
    
    public static void main(String[] args) {
		String str = "alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2017102509517770&biz_content=%7B%22out_trade_no%22%3A%2220171113112205795000%22%2C%22passback_params%22%3A%2213%7C18%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E5%BE%AA%E5%BF%83%E7%A7%91%E6%8A%80%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%2210.00%22%7D&charset=UTF-8&format=JSON&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Fwww.xunxinkeji.cn%2Fapi%2Fpay%2Falipay_notify&sign=O%2BocIS%2Birt4OL%2BRo8mlwI%2F0J9wy8kdl8tneB4UYIL9O9Dkr1Nz%2BMi5c6wFs9XzeCx0NKkNw5%2FAQ5c89DskaX10segw6RF3ZF4M0QFQi8xf6l3Rkp4QyUAVfI%2BqKezgy4RF7EL%2Fpuc8Bw%2BpVK%2FdPn%2BobclhjO3XM%2FC9p1h7DUWpoyEwvmXLy54wToIkRcGnKyN25DcqGeOUlxebvrts1lu7U2fOaH3wOGrO2qhyKf6Vr9dnC8dR6kd9qZOXbAOP7iwY%2FXyf5%2F67gD0vY5EAib5M1s%2FXaoGM8DrNEoqe5NyEnLfOB%2B%2BqTBoFr8WHxIEdflIzDLoE2nYa7sfgCy4ULuXg%3D%3D&sign_type=RSA2&timestamp=2017-11-13+11%3A22%3A21&version=1.0";
		String escape = escape(str);
		String unescape = unescape(str);
    	System.out.println(escape);
    	System.out.println(unescape);
	}
    
    
}
