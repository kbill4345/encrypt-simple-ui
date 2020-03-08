package com.yln.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


public class ThreeDES {
	private static final String IV = "";

	/**
	 * 默认的加密key值, 如果没有传入加密的key，则是用默认的key
	 * */
	private static final String DEFAULTKEY = "2bec458463cd4fa5b847c21c99605a2f";
	/**
	 * 常量 start
	 * */
	private static final String UTF_8 = "UTF-8";
	private static final String DES = "DES";
	private static final String DESEDE = "DESede";
	private static final String DESKEY = "DES/CBC/PKCS5Padding";
	private static final String DESEDEKey = "DESede/ECB/PKCS5Padding";


	/**
	 * DESCBC加密
	 * @param src 数据源
	 * @param key 密钥，长度必须是8的倍数
	 * @return 返回加密后的数据
	 * @throws Exception
	 * */
	public static String encryptDESCBC(final String src, final String key) throws Exception {
		final DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(UTF_8));
		final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		final SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		final IvParameterSpec iv = new IvParameterSpec(IV.getBytes(UTF_8));
		final Cipher cipher = Cipher.getInstance(DESKEY);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		final byte[] b = cipher.doFinal(src.getBytes(UTF_8));
		return new String(Base64.encodeBase64(b));
	}

	/**
	 * DESCBC解密
	 * @param src 数据源
	 * @param key 密钥，长度必须是8的倍数
	 * @return 返回解密后的原始数据
	 * @throws Exception
	 * */
	public static String decryptDESCBC(final String src, final String key) throws Exception {
		final byte[] bytesrc = Base64.decodeBase64(src);
		final DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(UTF_8));
		final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		final SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		final IvParameterSpec iv = new IvParameterSpec(IV.getBytes(UTF_8));
		final Cipher cipher = Cipher.getInstance(DESKEY);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		final byte[] retByte = cipher.doFinal(bytesrc);
		return new String(retByte);

	}

	/**
	 *  3DESECB加密,key必须是长度大于等于 3*8 = 24 位
	 * */
	public static String encryptThreeDESECB(String src, final String key) {
		try {
			src = URLEncoder.encode(src, UTF_8);
			final DESedeKeySpec dks = new DESedeKeySpec(key.getBytes(UTF_8));
			final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DESEDE);
			final SecretKey securekey = keyFactory.generateSecret(dks);
			final Cipher cipher = Cipher.getInstance(DESEDEKey);
			cipher.init(Cipher.ENCRYPT_MODE, securekey);
			final byte[] b = cipher.doFinal(src.getBytes());
			return new String(Base64.encodeBase64(b)).replaceAll("\r", "").replaceAll("\n", "");
		} catch (Exception e) {
			return src;
		}
	}

	public static String encryptThreeDESECB(String src) {
		return encryptThreeDESECB(src, DEFAULTKEY);
	}

	/**
	 * 3DESECB解密,key必须是长度大于等于 3*8 = 24 位
	 * */
	public static String decryptThreeDESECB(final String src, final String key) {
		try {
			final byte[] bytesrc = Base64.decodeBase64(src);
			final DESedeKeySpec dks = new DESedeKeySpec(key.getBytes(UTF_8));
			final SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DESEDE);
			final SecretKey securekey = keyFactory.generateSecret(dks);
			final Cipher cipher = Cipher.getInstance(DESEDEKey);
			cipher.init(Cipher.DECRYPT_MODE, securekey);
			final byte[] retByte = cipher.doFinal(bytesrc);
			return new String(retByte);
		} catch (Exception e) {
			return src;
		}
	}

	public static String decryptThreeDESECB(final String src)  {
		try {
			return URLDecoder.decode(decryptThreeDESECB(src, DEFAULTKEY),"utf-8");
		} catch (UnsupportedEncodingException e) {
			return src;
		}
	}

	public static void main(String[] args) {
		System.out.println(decryptThreeDESECB("/qXXgPpWT7ubI5opnHDH+0HWsaIQ3nFl"));
	}
}
