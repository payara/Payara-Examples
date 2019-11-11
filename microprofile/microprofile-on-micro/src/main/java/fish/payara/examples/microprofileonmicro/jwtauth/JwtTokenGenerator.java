package fish.payara.examples.microprofileonmicro.jwtauth;

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.eclipse.microprofile.jwt.Claims;

/**
 *
 * @author Andrew Pielage <andrew.pielage@payara.fish>
 */
public class JwtTokenGenerator {
    
    public static String generateJwtString(String jsonResource) throws Exception {
        byte[] byteBuffer = new byte[16384];
        Thread.currentThread().getContextClassLoader()
                       .getResource(jsonResource)
                       .openStream()
                       .read(byteBuffer);

        JSONParser parser = new JSONParser(JSONParser.DEFAULT_PERMISSIVE_MODE);
        JSONObject jwtJson = (JSONObject) parser.parse(byteBuffer);
        
        long currentTimeInSecs = (System.currentTimeMillis() / 1000);
        long expirationTime = currentTimeInSecs + 1000;
       
        jwtJson.put(Claims.iat.name(), currentTimeInSecs);
        jwtJson.put(Claims.auth_time.name(), currentTimeInSecs);
        jwtJson.put(Claims.exp.name(), expirationTime);
        
        SignedJWT signedJWT = new SignedJWT(new JWSHeader
                                            .Builder(JWSAlgorithm.RS256)
                                            .keyID("/privateKey.pem")
                                            .type(JOSEObjectType.JWT)
                                            .build(), JWTClaimsSet.parse(jwtJson));
        
        signedJWT.sign(new RSASSASigner(readPrivateKey("privateKey.pem")));
        
        return signedJWT.serialize();
    }
    
    public static PrivateKey readPrivateKey(String resourceName) throws Exception {
        byte[] byteBuffer = new byte[16384];
        int length = Thread.currentThread().getContextClassLoader()
                                    .getResource(resourceName)
                                    .openStream()
                                    .read(byteBuffer);
        
        String key = new String(byteBuffer, 0, length).replaceAll("-----BEGIN (.*)-----", "")
                                                      .replaceAll("-----END (.*)----", "")
                                                      .replaceAll("\r\n", "")
                                                      .replaceAll("\n", "")
                                                      .trim();

        return KeyFactory.getInstance("RSA")
                         .generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(key)));
    }
}
