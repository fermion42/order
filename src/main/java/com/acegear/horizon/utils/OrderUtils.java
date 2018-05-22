package com.acegear.horizon.utils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.UUID;

/**
 * Created by zxx on 2017/1/4.
 */
public class OrderUtils {
    private static SecureRandom random = new SecureRandom();

    /**
     * 根据指定长度随机订单号
     *
     * @param length
     * @return
     */
    public static String randomString(int length) {
        String str = new BigInteger(130, random).toString(32);
        return str.substring(0, length);
    }

    public static String newOrderId() {
        return randomString(20);
    }

    /**
     * @param request
     * @return
     */
    public static String getXFFIp(HttpServletRequest request) {
        String xIp = request.getHeader("X-Forwarded-For");
        if (org.apache.commons.lang.StringUtils.isBlank(xIp)) {
            xIp = request.getRemoteAddr();
        } else {
            String[] ips = xIp.trim()
                              .split(",");
            if (ips.length > 0) {
                xIp = ips[0];
            }
        }
        return xIp;
    }
}
