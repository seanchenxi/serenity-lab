/*******************************************************************************
 * Copyright 2012 Xi CHEN
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.seanchenxi.gwt.util.codec;

/**
 *
 *  Base64 encode / decode
 *  http://www.webtoolkit.info/
 *
 **/
public class Base64 {
	
	// private property
	private final static String _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
	
	public static String encode(String string){
		String output = "";
		float chr1, chr2, chr3, enc1, enc2, enc3, enc4;
		int i = 0;
		
		string = _utf8_encode(string);

		while (i < string.length()) {
			
			chr1 = string.codePointAt(i++);
			try{
				chr2 = string.codePointAt(i++);
			}catch (IndexOutOfBoundsException e) {
				chr2 = Float.NaN;
			}
			try{
				chr3 = string.codePointAt(i++);
			}catch (IndexOutOfBoundsException e) {
				chr3 = Float.NaN;
			}
 
			enc1 = (int)chr1 >> 2;
			enc2 = (((int)chr1 & 3) << 4) | ((int)chr2 >> 4);
			enc3 = (((int)chr2 & 15) << 2) | ((int)chr3 >> 6);
			enc4 = (int)chr3 & 63;
 
			if (Float.isNaN(chr2)) {
				enc3 = enc4 = 64;
			} else if (Float.isNaN(chr3)) {
				enc4 = 64;
			}
			
			output = output +
			_keyStr.charAt((int)enc1) + _keyStr.charAt((int)enc2) +
			_keyStr.charAt((int)enc3) + _keyStr.charAt((int)enc4);
 
		}
		return output;
	}
	
	public static String decode(String string){
		String output = "";
		int chr1, chr2, chr3;
		int enc1, enc2, enc3, enc4;
		int i = 0;
 
		string = string.replace("/[^A-Za-z0-9+/=]/g", "");
 
		while (i < string.length()) {
 
			enc1 = _keyStr.indexOf(string.charAt(i++));
			enc2 = _keyStr.indexOf(string.charAt(i++));
			enc3 = _keyStr.indexOf(string.charAt(i++));
			enc4 = _keyStr.indexOf(string.charAt(i++));
 
			chr1 = (enc1 << 2) | (enc2 >> 4);
			chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
			chr3 = ((enc3 & 3) << 6) | enc4;
 
			output = output + String.valueOf(Character.toChars(chr1));
 
			if (enc3 != 64) {
				output = output + String.valueOf(Character.toChars(chr2));
			}
			if (enc4 != 64) {
				output = output + String.valueOf(Character.toChars(chr3));
			}
 
		}
 
		output = Base64._utf8_decode(output);
 
		return output;		
	}

	private static String _utf8_encode(String string) {
		string = string.replace("/\r\n/g","\n");
		String utftext = "";
		for (int n = 0; n < string.length(); n++) {		
			int c = string.codePointAt(n);
			if (c < 128) {
				utftext += String.valueOf(Character.toChars(c));
			}
			else if((c > 127) && (c < 2048)) {
				utftext += String.valueOf(Character.toChars((c >> 6) | 192));
				utftext += String.valueOf(Character.toChars((c & 63) | 128));
			}
			else {
				utftext += String.valueOf(Character.toChars((c >> 12) | 224));
				utftext += String.valueOf(Character.toChars(((c >> 6) & 63) | 128));
				utftext += String.valueOf(Character.toChars((c & 63) | 128));
			}
		}
		return utftext;
	}
	
	private static String _utf8_decode(String utftext) {
		String string = "";
		int i = 0;
		int c = 0, c2 = 0, c3 = 0;
 
		while (i < utftext.length()) {
 
			c = utftext.codePointAt(i);
 
			if (c < 128) {
				string += String.valueOf(Character.toChars(c));
				i++;
			}
			else if((c > 191) && (c < 224)) {
				c2 = utftext.codePointAt(i+1);
				string += String.valueOf(Character.toChars(((c & 31) << 6) | (c2 & 63)));
				i += 2;
			}
			else {
				c2 = utftext.codePointAt(i+1);
				c3 = utftext.codePointAt(i+2);
				string += String.valueOf(Character.toChars(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63)));
				i += 3;
			}
 
		}
 
		return string;
	}
}
