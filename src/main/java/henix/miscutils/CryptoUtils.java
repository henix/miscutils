package henix.miscutils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.io.Charsets;

public final class CryptoUtils {

	public static byte[] hmac_sha256(String content, String key) {
		try {
			SecretKeySpec spec = new SecretKeySpec(key.getBytes(Charsets.UTF_8), "HmacSHA256");
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(spec);
			return mac.doFinal(content.getBytes(Charsets.UTF_8));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (InvalidKeyException e) {
			throw new RuntimeException(e);
		}
	}
}
