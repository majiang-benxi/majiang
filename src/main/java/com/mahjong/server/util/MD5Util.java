package com.mahjong.server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {

	/*
	 * 默认的密码字符串组合，用来将字节转换成 16 进制表示的字符,apache校验下载的文件的正确性用的就是默认的这个组合
	 */
	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };

	protected static MessageDigest messagedigest = null;

	static {
		try {
			messagedigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException nsaex) {
			nsaex.printStackTrace();
		}
	}

	/*
	 * 生成字符串的md5校验值
	 * 
	 */
	public static String getMD5String(String s) {
		return getMD5String(s.getBytes());
	}

	/*
	 * 判断字符串的md5校验码是否与一个已知的md5码相匹配
	 * 
	 */
	public static boolean checkPassword(String password, String md5PwdStr) {
		String s = getMD5String(password);
		return s.equals(md5PwdStr);
	}

	/*
	 * 生成文件的md5校验值
	 */
	public static String getFileMD5String(File file) throws IOException {
		InputStream fis;
		fis = new FileInputStream(file);
		byte[] buffer = new byte[1024];
		int numRead = 0;
		while ((numRead = fis.read(buffer)) > 0) {
			messagedigest.update(buffer, 0, numRead);
		}
		fis.close();
		return bufferToHex(messagedigest.digest());
	}

	/*
	 * JDK1.4中不支持以MappedByteBuffer类型为参数update方法，并且网上有讨论要慎用MappedByteBuffer，
	 * 原因是当使用 FileChannel.map 方法时，MappedByteBuffer 已经在系统内占用了一个句柄， 而使用
	 * FileChannel.close 方法是无法释放这个句柄的，且FileChannel有没有提供类似 unmap 的方法，
	 * 因此会出现无法删除文件的情况。 不推荐使用
	 * 
	 */
	public static String getFileMD5String_old(File file) throws IOException {
		FileInputStream in = new FileInputStream(file);
		FileChannel ch = in.getChannel();
		MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
		messagedigest.update(byteBuffer);

		if (in != null) {
			in.close();
		}
		return bufferToHex(messagedigest.digest());
	}

	public static synchronized String getMD5String(byte[] bytes) {
		messagedigest.update(bytes);
		return bufferToHex(messagedigest.digest());
	}

	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];// 取字节中高 4 位的数字转换, >>>
												// 为逻辑右移，将符号位一起右移,此处未发现两种符号有何不同
		char c1 = hexDigits[bt & 0xf];// 取字节中低 4 位的数字转换
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	public static String getJywBy17stirng(String s17) {
		if (17 != s17.length()) {
			return "0";
		}
		String[] ID_ICCID_array = new String[17];
		for (int i = 0; i < s17.length(); i++) {
			ID_ICCID_array[i] = String.valueOf(s17.charAt(i));
		}
		;
		int s = (Integer.parseInt(ID_ICCID_array[0]) + Integer.parseInt(ID_ICCID_array[10])) * 7
				+ (Integer.parseInt(ID_ICCID_array[1]) + Integer.parseInt(ID_ICCID_array[11])) * 9
				+ (Integer.parseInt(ID_ICCID_array[2]) + Integer.parseInt(ID_ICCID_array[12])) * 10
				+ (Integer.parseInt(ID_ICCID_array[3]) + Integer.parseInt(ID_ICCID_array[13])) * 5
				+ (Integer.parseInt(ID_ICCID_array[4]) + Integer.parseInt(ID_ICCID_array[14])) * 8
				+ (Integer.parseInt(ID_ICCID_array[5]) + Integer.parseInt(ID_ICCID_array[15])) * 4
				+ (Integer.parseInt(ID_ICCID_array[6]) + Integer.parseInt(ID_ICCID_array[16])) * 2
				+ Integer.parseInt(ID_ICCID_array[7]) * 1 + Integer.parseInt(ID_ICCID_array[8]) * 6
				+ Integer.parseInt(ID_ICCID_array[9]) * 3;
		int y = s % 11;
		String M = "F";
		String JYM = "10X98765432";
		M = JYM.substring(y, y + 1);
		return M;
	}

	public static void main(String[] args) throws IOException {
		String y = getMD5String("123456");
		System.out.println(y);
	}

	public final static String getMd5AndCharset(String s, String charset) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes(charset);
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
	
}
