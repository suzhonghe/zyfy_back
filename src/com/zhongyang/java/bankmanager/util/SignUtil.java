package com.zhongyang.java.bankmanager.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.IOUtils;
import com.zhongyang.java.system.uitl.SystemPro;

/**
 * 主要包含签名、验签功能。
 * <p>
 * Created by wuxinw on 2017/5/3.
 */
public class SignUtil {
	
	private static Logger logger=Logger.getLogger(SignUtil.class);
	
	static{
		Map<String, Object> sysMap = SystemPro.getProperties();
		PRIVATE_KEY_PATH=(String) sysMap.get("PRIVATE_KEY_PATH");
		PUBLIC_KEY_PATH=(String) sysMap.get("PUBLIC_KEY_PATH");
		PRIVATE_KEY_PASSWD=(String) sysMap.get("PRIVATE_KEY_PASSWD");
	}
	
	public static String PRIVATE_KEY_PATH;
	public static String PUBLIC_KEY_PATH;
	public static String PRIVATE_KEY_PASSWD;
	/**
	 * 使用示例
	 *
	 * @param args
	 * @throws FileNotFoundException
	 * @throws KeyStoreException
	 */

	public static void main(String[] args) throws FileNotFoundException, KeyStoreException {
//		String body = "{\"recode\":\"31111\",\"remsg\":\"失败：error_no[200020],error_info[签名错误]\",\"signdata\":\"VjCOOFUEgO+65WYUSmLe6qOY56GJ5ter0qIcsMVo/tAKBuDgENMTb62hh0c/Jndfij9nMQZNta968JUW9LMxKThFUo7u/jADLU4gGVSOw84ibaZumqineAbYXIsbXRw5Oi1MTH4mKE4Tre35nxXQf73tNglVNaqy52e30l1FY2LNsgh7v5L3RPArfQL3kBrTootbjVNnzJkmPgF+7uoDk2G4fr1d8nqmMMuxVBUNmE/sL9WYhZ6moaC0NE+eLKA+rCHrZqudpN+hhZ7oORXnGHrfAOhuOE8o53PrNK5LDRjlCIaqjVszP1DQOY5qQFA/n05f3yfNQltBSZSDoGm0Jw==\"}";
//		Map<String, Object> parse = new HashMap<String, Object>();
//		parse.put("aa", 11111);
//		parse.put("vv", 11111);
//		String s = params2PlainText(parse);
//		String sign = sign(s);
//		System.out.println(sign);
//		System.out.println(s);
		String s="0|5|12|3|2018-01-15 09:00:43|[{\"cust_no\":\"2017083016403708311035280\",\"fee_int\":0.0600,\"financ_amt\":100000.00,\"financ_int\":0.1200,\"open_branch\":\"100002\",\"payee_name\":\"阿里巴巴短期借款第14期\",\"reg_date\":\"2017-09-15\",\"reg_time\":\"09:00:43\",\"withdraw_account\":\"22222222222222222\"}]|0.1200|32935982240882641730|1|0|2017-09-15|09:00:43|0|XIB-ZYFY-A-90079279|7D0D80CC-C7A2-412B-A5A0-08F2C6220891|阿里巴巴短期借款第14期|0|1|2017-09-15 09:00:43|100000.00|2017-09-15 09:00:43|1";
		String signdata0="fbvl655H87dT/K48LPioIHzGkl/b096igCpOgsTLyiJqK0B57eqLVsjMtwdlnYimwSrww6Sqk043J3vg1D7KVUvmDKsftze5wOdffJ+zJkT62PEMBp8qtGBC2De/o28SSVXGh4i8FB7co6beQ+KwTRjk/RFIxYyKaJUz0qWzjVELxxjvRE52IQigk+YBwrbu1yFHdJemiffUA9oY0ziSNA7gVsATmpPdIwxOT/hifFF0vrWGyDIMtn+dhZvAchwPJ6lqj/oNIvcxbIx7mYc3MqsIGeMBJT9kalzEmv5uLDCiOpj6T6tBzlhFRkJZdm/4/ZnqO4H3r36AJtdhL4AYWQ==";
		boolean b = verifySign(s, signdata0);
		System.out.println(b);
	}
	/**
	 * 签名
	 *
	 * @return
	 */
	public static String sign(Map<String, Object> params) {
		String sign = sign(params2PlainText(params));
		logger.info("签名：" + sign);
		return sign;
	}

	/**
	 * 签名
	 *
	 * @param plainText
	 * @return
	 */
	public static String sign(String plainText) {
		try {
			Signature sig = Signature.getInstance("SHA1WithRSA");
			sig.initSign(getPrivateKey());
			sig.update(plainText.getBytes());
			byte[] b = sig.sign();
			return new String(Base64.encodeBase64(b));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static boolean verifySign(Map<String, Object> params, String signedText) {
		return verifySign(params2PlainText(params), signedText);
	}

	/**
	 * 使用公钥验签
	 *
	 * @param plainText
	 * @param signedText
	 * @return
	 */
	public static boolean verifySign(String plainText, String signedText) {
		try {
			signedText = signedText.replaceAll(" ", "+");
			Signature sig = Signature.getInstance("SHA1WithRSA");
			X509Certificate certificate = loadCertificate();
			sig.initVerify(certificate);
			sig.update(plainText.getBytes());
			byte[] b = Base64.decodeBase64(signedText.getBytes());
			return sig.verify(b);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取私钥
	 *
	 * @return
	 */
	private static PrivateKey getPrivateKey() {
		String path = SignUtil.class.getClassLoader().getResource(PRIVATE_KEY_PATH).getPath();
		KeyStore ks = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			ks = KeyStore.getInstance("PKCS12");
			ks.load(fis, PRIVATE_KEY_PASSWD.toCharArray());
			fis.close();
			String keyAlias = null;
			if (ks.aliases().hasMoreElements()) {
				keyAlias = ks.aliases().nextElement();
			}
			return (PrivateKey) ks.getKey(keyAlias, PRIVATE_KEY_PASSWD.toCharArray());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.close(fis);
		}
		return null;
	}

	/**
	 * 参数转为签名原文
	 *
	 * @param params
	 * @return
	 */
	private static String params2PlainText(Map<String, Object> params) {
		TreeMap<String, Object> sortedParams = new TreeMap<>();
		sortedParams.putAll(params);
		StringBuilder plainText = new StringBuilder();
		for (String key : sortedParams.keySet()) {
			if (sortedParams.get(key) instanceof String || sortedParams.get(key) instanceof Number) {
				plainText.append("|").append(sortedParams.get(key));
			} else {
				plainText.append("|").append(JSONObject.toJSONString(sortedParams.get(key)));
			}
		}
		plainText.deleteCharAt(0);
		String str = plainText.toString();
		logger.info("签名原文：" + str);
		return str;
	}

	/**
	 * 获取公钥
	 *
	 * @return
	 * @throws Exception
	 */
	private static X509Certificate loadCertificate() throws Exception {
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		URL path = SignUtil.class.getClassLoader().getResource(PUBLIC_KEY_PATH);
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream is = null;
		if (path != null)
			try {
				is = new FileInputStream(path.getFile());
			} catch (FileNotFoundException e) {
				is = classLoader.getResourceAsStream(PUBLIC_KEY_PATH);
			}
		else {
			is = new FileInputStream(PUBLIC_KEY_PATH);
		}
		// ByteArrayInputStream bis = new ByteArrayInputStream(("-----BEGIN
		// CERTIFICATE-----\n" +
		// "MIIDazCCAlOgAwIBAgIJAK5832wV7o2XMA0GCSqGSIb3DQEBCwUAMEwxCzAJBgNV" +
		// "BAYTAkNOMQ8wDQYDVQQIDAZGdUppYW4xDzANBgNVBAcMBlhpYU1lbjEMMAoGA1UE" +
		// "CgwDWElCMQ0wCwYDVQQDDARpZnNwMB4XDTE3MDEyMzA4NDkzM1oXDTIwMDEyMzA4" +
		// "NDkzM1owTDELMAkGA1UEBhMCQ04xDzANBgNVBAgMBkZ1SmlhbjEPMA0GA1UEBwwG" +
		// "WGlhTWVuMQwwCgYDVQQKDANYSUIxDTALBgNVBAMMBGlmc3AwggEiMA0GCSqGSIb3" +
		// "DQEBAQUAA4IBDwAwggEKAoIBAQDndP4f+tuaC+66rHtlmTBollIqd96XIMeV76BR" +
		// "HLbbzorpXEb4GylcIJgXCaqEHMzWpfWSVMKykdfr/3JQVzabFRcqoKHxrziW79G/" +
		// "ZouGiB+uj5vOA5gT8uY+D7An9yrPxZdx2s+jMuGDv/lEi/LqZzAqlPatXADinUA9" +
		// "yPrc/FTxR3FSXDqE59X1ooVC+Fartm9kl5HDCxEWmVLjB61oeib1VYOMkrUXW3OD" +
		// "C2rQ+2N96yALWZ//a/mtDocf9fUrpjjvwshdxo7veY93bg3tWSEzsc49VMFxUp1k" +
		// "p3cLu/9OHdW2Z7fK+WD/SV65ZeGBG6EvBsDigyQSyFpxyV7ZAgMBAAGjUDBOMB0G" +
		// "A1UdDgQWBBSvuzuf1P3VdILS14VC5VufHNOuQTAfBgNVHSMEGDAWgBSvuzuf1P3V" +
		// "dILS14VC5VufHNOuQTAMBgNVHRMEBTADAQH/MA0GCSqGSIb3DQEBCwUAA4IBAQAB" +
		// "P7nOhefVaMxpUMakNDU8PCTQ/mJukA2HXL+13di+jZWNmk/RrVz977qTQU2LXPNY" +
		// "gnhLw1azvGJ0HcheeglsdUB1X/xjPaO/VDusjWKt80gD5U6BfFqMIFDouaDZsclc" +
		// "NU6jFBwrd//Gpcb5rZZBhhLoMIHkcys1U0ayyVzB/jhVgWhATlYG7/O36uYLToaD" +
		// "RGjzvve5gjHXwVJpFzgIgXJf76zdHDwPRjSIFYfWMmuUfY0SMxqaQBylU2W7+NwJ" +
		// "eFfgOM0jQkRoSQox1xFzA4bNNC9C3EMcHVO5gc8UhzK9rgy+0aK1p/fR5zxV3uUi" +
		// "5FPY5TyfmVTHD8htDXSB\n" +
		// "-----END CERTIFICATE-----").getBytes());
		X509Certificate certificate = (X509Certificate) factory.generateCertificate(is);
		is.close();
		return certificate;
	}

}
