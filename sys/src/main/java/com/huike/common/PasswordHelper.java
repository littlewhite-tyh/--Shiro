package com.huike.common;

import com.huike.domain.AdminUser;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 把明码password转换成MDS
 * 加密的 并再次返回adminUser对象
 */
public class PasswordHelper {

    public static void generateMD5Pwd(AdminUser adminUser){
//        adminUser.setPasswordSalt((ByteSource.Util.bytes(adminUser.getAccount()).toString()));
//        String newPassword = new SimpleHash("MD5",
//                                                            adminUser.getPassword(),
//                                                            adminUser.getPasswordSalt(),
//                                                2).toString();
//        adminUser.setPassword(newPassword);
        Object salt = ByteSource.Util.bytes(adminUser.getAccount());
        int hashIterations = 2;
        String newPassword = new SimpleHash("MD5", adminUser.getPassword(), salt, hashIterations).toString();
        adminUser.setPassword(newPassword);

    }
}
