package com.zengqiang.future.util;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {

    private static Logger log = LoggerFactory.getLogger(TokenUtil.class);

    /**
     * 创建jwt
     * @param id
     * @param subject
     * @param ttlMillis 过期的时间长度
     * @return
     * @throws Exception
     */
    public static String createJWT(String id, String subject, long ttlMillis) throws Exception {
        //指定签名的时候使用的签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Map<String,Object> claims = new HashMap<String,Object>();
        claims.put("uid", "uniqueID");
        claims.put("user_name", "admin");
        claims.put("nick_name","jiangmofeng");
        SecretKey key = generalKey();
        //为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder()
                //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值
                //一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                //设置jti(JWT ID)：是JWT的唯一标识，从而回避重放攻击。
                .setId(id)
                .setIssuedAt(now)
                //sub代表这个JWT的主体，即它的所有人。
                //用户信息
                .setSubject(subject)

                //设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, key);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    public static String createToken(String subject,Map<String,Object> claims,long activeMills){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey key=generalKey();
        JwtBuilder builder=Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setSubject(subject)
                .signWith(signatureAlgorithm,key);
        if (activeMills >= 0) {
            long expMillis = nowMillis + activeMills;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 解密jwt
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception{
        SecretKey key = generalKey();  //签名秘钥，和生成的签名的秘钥一模一样
        Claims claims = Jwts.parser()  //得到DefaultJwtParser
                .setSigningKey(key)         //设置签名的秘钥
                .parseClaimsJws(jwt).getBody();//设置需要解析的jwt
        return claims;
    }

    public static Claims checkToken(String token){

        SecretKey key = generalKey();  //签名秘钥，和生成的签名的秘钥一模一样
        Claims claims = Jwts.parser()  //得到DefaultJwtParser
                .setSigningKey(key)         //设置签名的秘钥
                .parseClaimsJws(token).getBody();//设置需要解析的jwt
        return claims;
    }

    public static boolean authenticate(String token) throws Exception{
        if(token==null){
            return false;
        }
        Claims claims=checkToken(token);
        if(claims==null){
            return false;
        }
        return true;
    }

    /**
     * 由字符串生成加密key
     * @return
     */
    public static SecretKey generalKey(){
        String stringKey = "zengqiang";
        byte[] encodedKey = Base64.decodeBase64(stringKey);//本地的密码解码[B@152f6e2
        // 根据给定的字节数组使用AES加密算法构造一个密钥
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }


    public static void main(String[] args){
        TokenUtil util=   new TokenUtil();
        String ab=null;
        try {
            ab = util.createJWT("jwt", "{id:100,name:aiqinhai}", 600000);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        System.out.println("签名之后的JWT:"+ab);
        String jwt="1234546gdfdvc.ertyyuggvh.retyfghj";
        Claims c=null;
        try {
            c = util.parseJWT(jwt);
            //注意：如果jwt已经过期了，这里会抛出jwt过期异常。
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(c.getId());
        System.out.println(c.getIssuedAt());
        System.out.println(c.getSubject());
        System.out.println("获取私有声明中的nick_name:"+c.get("nick_name"));
        System.out.println("获取私有声明中的user_name:"+c.get("user_name"));
        System.out.println(c.get("uid", String.class));
    }
}
