package com.blit.lp.tools.Encoder;

import java.io.UnsupportedEncodingException;

public class AjaxEncoder {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "小欧";
		String encode = encode(str);
		System.out.println("encode:" + encode);
		String decode = decode(encode);
		System.out.println("decode:" + decode);
		
	}
	
	static private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-|".toCharArray();  
	static private byte[] codes = new byte[256];  
	
	static 
	{  
		for (int i = 0; i < 256; i++)  
			codes[i] = -1;  
		for (int i = 'A'; i <= 'Z'; i++)
			codes[i] = (byte) (i - 'A');  
		for (int i = 'a'; i <= 'z'; i++)  
			codes[i] = (byte) (26 + i - 'a');  
		for (int i = '0'; i <= '9'; i++)  
			codes[i] = (byte) (52 + i - '0');  
		codes['_'] = 62;  
		codes['-'] = 63;  
	}
	
	/** 
	 * 将原始数据编码转换为base64编码 
	 */  
	static public char[] encode(byte[] data)
	{  
		char[] out = new char[((data.length + 2) / 3) * 4];  

		for (int i = 0, index = 0; i < data.length; i += 3, index += 4)  
		{  
			boolean quad = false;  
			boolean trip = false;  

			int val = (0xFF & (int) data[i]);  
			val <<= 8;  
			if ((i + 1) < data.length)  
			{  
				val |= (0xFF & (int) data[i + 1]); 
				trip = true; 
			}  
			
			val <<= 8;  
			if ((i + 2) < data.length)  
			{  
				val = (0x0F & (int) data[i]) | ((0x0F & (int) data[i + 2])<<4);
				val<<= 8;
				val |= ((0x0F & (int) data[i + 1])<<4) | ((0xF0 & (int) data[i + 1])>>4);
				val<<= 8;
				val |= ((0xF0 & (int) data[i])>>4) | ((0xF0 & (int) data[i + 2]));
				quad = true;  
			}  
			out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];  
			val >>= 6;  
			out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];  
			val >>= 6;  
			out[index + 1] = alphabet[val & 0x3F];  
			val >>= 6;  
			out[index + 0] = alphabet[val & 0x3F];  
		}  
		return out;  
	}
	
	static public String encode(String data)
	{
		String result = null;
		try {
			result = new String(encode(data.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	/** 
	 * 将base64编码的数据解码成原始数据 
	 */  
	static public byte[] decode(char[] data)  
	{  
		int len = ((data.length + 3) / 4) * 3;  
		if(data.length > 0 && data[data.length - 1] == alphabet[64])  
			--len;  
		if(data.length > 1 && data[data.length - 2] == alphabet[64])  
			--len;  
		byte[] out = new byte[len];  
		int shift = 0;  
		int accum = 0;  
		int index = 0;  
		for(int ix = 0; ix < data.length; ix++)  
		{  
			int value = codes[data[ix] & 0xFF];  
			if(value >= 0)  
			{  
				accum <<= 6;  
				shift += 6;  
				accum |= value;  
				if(shift >= 24)
				{
					accum = (accum & 0xF0) | ((accum & 0x0F) << 20) | ((accum & 0xF000) >> 4) | ((accum & 0x0F00) << 4)  |  ((accum & 0xF00000) >> 20)| (accum & 0x0F0000);
					while(shift > 0)  
					{  
						shift -= 8;  
						out[index++] = (byte)((accum >> shift) & 0xff);  
					}  
				}
			}  
		}
		
		while(shift>=8)
		{
			shift -= 8;  
			out[index++] = (byte)((accum >> shift) & 0xff); 
		}
		
		//数据长度不正确
		if(index != out.length)  
		{
			return null;
		}
		
		return out;  
	} 
	
	static public String decode(String data) 
	{
		String result = null;
		
		try {
			result = new String(decode(data.toCharArray()),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
