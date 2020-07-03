package com.changgou.token;

import org.junit.Test;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

/*****
 * @Author: www.itheima
 * @Date: 2019/7/7 13:48
 * @Description: com.changgou.token
 *  使用公钥解密令牌数据
 ****/
public class ParseJwtTest {

    /***
     * 校验令牌
     */
    @Test
    public void testParseToken(){
        //令牌
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2xlcyI6IlJPTEVfVklQLFJPTEVfVVNFUiIsIm5hbWUiOiJpdGhlaW1hIiwiaWQiOiIxIn0.A8dgzqHTjfhwd-NjL2aUBnJX2Yu1lvB7OQwec6_-LAyPpzcDGVUmWm0P9NRtjpi2u8HNEdmWpgLSNayZHSMC9_8fW4xoy47RPS_0vxw0brO9WvcTsXn2F4ucbX5I5UulxU0jJKGOsnZVBTs7tKWIGuAMkhQyOwJYBWZ48QHVCsIHVaRO8dnwZ9oUdSCMSVs6rvmf6hyqGUmzO5aObNXCZehYQL_fjATs29c9lvYgwYXxniVD_FXhI6wPhIyMurA2SXUxp0P6KANduRM6sVjrleQRSog6yhy-dBSkbalyqMO702nETlaPdoV-qYVLePtJ__VFZN_IUPhTI6iZE-RTtg";

        //公钥
        String publickey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1wKjnA5LVcsxPkGhuLXs9YQ2gZugk6kZBIO80ZGuIhKkyaqyok5jwyIIr3iFqq2tuZpvkVwhRPYvN6QlC6qNXcE43/mt2bCOckWryk5lV9egacoPM4NbZGiAvxwjGyJW5EYapY+VhMB/PU5dxF6UFvC2f+rqcBnHtLjj57o2u6mQ1TdsiUercHO6FA0o3RG3T4GdbwsXgjf6ea45ZY0HbP1SspR/yrTYJqHfK0q/T691SGAe8hJiobqOnWTgeBivE3vyQD/D/9XMugp8dlh1wBflpPGuPrAYpK3ZBfaThkx7l3A67ZCcVvDCByniEu37EPkTm/BiXrQO3y1CuWBX9QIDAQAB-----END PUBLIC KEY-----";

        //校验Jwt
        Jwt jwt = JwtHelper.decodeAndVerify(token, new RsaVerifier(publickey));

        //获取Jwt原始内容 载荷
        String claims = jwt.getClaims();
        System.out.println(claims);
        //jwt令牌
        String encoded = jwt.getEncoded();
        System.out.println(encoded);
    }

    @Test
    public void testDecodeBase64(){

    }
}
