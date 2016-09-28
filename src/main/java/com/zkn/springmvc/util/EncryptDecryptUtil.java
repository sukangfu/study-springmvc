package com.zkn.springmvc.util;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import com.zkn.springmvc.util.exception.CustomException;

/**
 * 加解密工具类
 */
public class EncryptDecryptUtil {
	private static final String UTF_8 = "UTF-8";
	
	private EncryptDecryptUtil(){
		//隐藏构造方法
	}
	
	/**
	 * 该方法将指定的字符串用MD5算法加密后返回。
	 * @param s
	 * @return
	 */
	public static String getMD5Encoding(String s) {
		try{ 
			byte[] input = s.getBytes(UTF_8); 
			//声明16进制字母 
			char[] hexChar = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'}; 
			//获得一个MD5摘要算法的对象 
			MessageDigest md=MessageDigest.getInstance("MD5"); 
			md.update(input); 
			/* 
			 MD5算法的结果是128位一个整数,在这里javaAPI已经把结果转换成字节数组了 
			 */ 
			byte[] tmp = md.digest();//获得MD5的摘要结果 
			char[] str = new char[32]; 
			byte b=0; 
			for(int i=0;i<16;i++){ 
				b=tmp[i]; 
				str[2*i] = hexChar[b>>>4 & 0xf];//取每一个字节的低四位换成16进制字母 
				str[2*i+1] = hexChar[b & 0xf];//取每一个字节的高四位换成16进制字母 
			} 
			return new String(str); 
		}catch(Exception e){ 
			throw new CustomException("解密数据失败",e);
		} 
	}
	
	// 解密数据
	public static String decrypt(String message) {
		try {
			byte[] bytesrc = stringToBytes(message);
		
			Cipher cipher = Cipher.getInstance(Constants.CIPHER);
			DESKeySpec desKeySpec = new DESKeySpec(Constants.KEY.getBytes(UTF_8));
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			IvParameterSpec iv = new IvParameterSpec(Constants.KEY.getBytes(UTF_8));

			cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

			byte[] retByte = cipher.doFinal(bytesrc);
			return new String(retByte, UTF_8);
		} catch (Exception e) {
			throw new CustomException("解密数据失败",e);
		}
		
	}

	// 加密数据
	public static byte[] encrypt(String message){
		try{
			Cipher cipher = Cipher.getInstance(Constants.CIPHER);

			DESKeySpec desKeySpec = new DESKeySpec(Constants.KEY.getBytes(UTF_8));

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
			IvParameterSpec iv = new IvParameterSpec(Constants.KEY.getBytes(UTF_8));
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

			return cipher.doFinal(message.getBytes(UTF_8));
		}catch(Exception e){
			throw new CustomException("加密数据失败",e);
		}
		
	}

	// String转Byte数组
	public static byte[] stringToBytes(String temp) {
		byte[] digest = new byte[temp.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = temp.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}

		return digest;
	}

	// Byte数组转String
	public static String bytesToString(byte[] b) {
		StringBuilder hexString = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2){
				plainText = "0" + plainText;
			}
			hexString.append(plainText);
		}

		return hexString.toString();
	}
}
