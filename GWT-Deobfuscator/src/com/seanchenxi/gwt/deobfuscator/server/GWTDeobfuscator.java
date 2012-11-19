package com.seanchenxi.gwt.deobfuscator.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gwt.logging.server.StackTraceDeobfuscator;

public class GWTDeobfuscator {
	
	private static final char POUND = '#';
	private static final String USER_AGENT = "user.agent";

	protected static final String OPEN_PARENTHESIS = "(";
	protected static final String CLOSE_PARENTHESIS = ")";
	protected static final String AT = "at";
	protected static final String UNKNOWN = "Unknown";
	protected static final String SPACE = " ";
	protected static final String PERIOD = ".";
	protected static final String COLON = ":";
	
	protected static final String PROJET_URL_PREFIX = "http://pcis-source.dng-consulting.com/browse/PCIS_PHENIX/trunk";
	protected static final String GWT_2_5_SOURCE = "http://code.google.com/p/google-web-toolkit/source/browse/releases/2.5";
	
	public static final String AT_UNKNOWN = AT + SPACE + UNKNOWN + PERIOD;
	
	private String symbolMapsDirectory;
	private String name;
	private String versionNum;
	private HashMap<String, String> userAgents;
	private StackTraceDeobfuscator deobfuscator;
	
	public GWTDeobfuscator(String name, String symbolMapsDirectory){
		this.symbolMapsDirectory = symbolMapsDirectory;
		this.name = name;
		try{
			String[] strs = name.split("-")[1].split("\\.");
			this.versionNum = strs[strs.length - 1];
		}catch(Exception e){
			this.versionNum = null;
		}
		loadAllUserAgents();
	}
	
	public String deobfuscateStackTrace(String line, String strongName){
		if(deobfuscator == null)
			deobfuscator = new StackTraceDeobfuscator(symbolMapsDirectory);
		
		StackTraceElement ste = deobfuscator.resymbolize(convert(line), strongName);
		return convertToLinkableHtml(ste);
	}

	public String getName() {
		return name;
	}
	
	private String convertToLinkableHtml(StackTraceElement ste) {
		String fileName = ste.getFileName();
		String className = ste.getClassName();
		int lineNumber = ste.getLineNumber();
		String methodName = ste.getMethodName();
		
		StringBuilder sb = new StringBuilder();
		sb.append(className).append(POUND).append(methodName);
		if(lineNumber == -2){
			sb.append("(Native Method)");
		}else if(fileName != null){
			sb.append(OPEN_PARENTHESIS);
			String url = getURL(className, lineNumber);
			if(url != null){
				sb.append("<a target=\"_blank\" href=\"").append(url).append("\">")
				  .append(fileName);
				if(lineNumber >= 0){
					sb.append(COLON).append(lineNumber);
				}
				sb.append("</a>");
			}else{
				sb.append(fileName);
				if(lineNumber >= 0){
					sb.append(COLON).append(lineNumber);
				}
			}
			sb.append(CLOSE_PARENTHESIS);
		}else{
			sb.append("(Unknown Source)");
		}
		return sb.toString();
	}
	
	private String getURL(String className, int lineNumber){
		if(className.startsWith("com.pcis.phenix.client")){
			String url = PROJET_URL_PREFIX + "/PhenixWeb/src/";
			url += className.replaceAll("\\.", "\\/") + ".java";
			url += getParam() + "#to" + lineNumber;
			return url;
		}else if(className.startsWith("com.pcis.phenix.domain.autobean") || 
				(className.startsWith("com.pcis.phenix.domain.commande") && className.endsWith("ViewModel")) || 
				className.startsWith("com.pcis.phenix.domain.ValueListConst")){
			String url = PROJET_URL_PREFIX + "/PhenixWeb/src/";
			url += className.replaceAll("\\.", "\\/") + ".java";
			url += getParam() + "#to" + lineNumber;
			return url;
		}else if(className.startsWith("com.pcis.phenix.domain")){
			String url = PROJET_URL_PREFIX + "/PhenixCommon/src/";
			url += className.replaceAll("\\.", "\\/") + ".java";
			url += getParam() + "#to" + lineNumber;
			return url;
		}else if(className.startsWith("com.pcis.phenix.util")){
			String url = PROJET_URL_PREFIX + "/PhenixCommon/src/";
			url += className.replaceAll("\\.", "\\/") + ".java";
			url += getParam() + "#to" + lineNumber;
			return url;
		}else if(className.startsWith("com.google.gwt") || className.startsWith("com.google.web.bindery")){
			String url = GWT_2_5_SOURCE + "/user/src/";
			url += className.replaceAll("\\.", "\\/") + ".java";
			url += "#" + lineNumber;
			return url;	
		}
		return null;
	}
	
	private String getParam(){
		if(versionNum == null)
			return "?hb=true";
		else
			return "?r="+versionNum;
	}
	
	private StackTraceElement convert(String line) {
		int methodStartIndex = AT_UNKNOWN.length();
		int methodEndIndex = line.indexOf(OPEN_PARENTHESIS, methodStartIndex);
		String methodName = line.substring(methodStartIndex, methodEndIndex);
		
		int lineNumberStartIndex = line.indexOf(COLON) + 1;
		int lineNumberEndIndex = line.indexOf(CLOSE_PARENTHESIS, lineNumberStartIndex);
		int lineNumber = Integer.parseInt(line.substring(lineNumberStartIndex, lineNumberEndIndex));
		
		String fileName = line.substring(methodEndIndex + 1, lineNumberStartIndex - 1);
		
		return new StackTraceElement(UNKNOWN, methodName, fileName, lineNumber);
	}

	private void loadAllUserAgents(){
		userAgents = new HashMap<String, String>();
		File dir = new File(symbolMapsDirectory);
		for(File file : dir.listFiles()){
			if(!file.isFile() && !file.getName().endsWith(".symbolMap")){
				continue;
			}
			
			final String strongName = file.getName().split("\\.")[0];

			try {
				BufferedReader bin = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				String line;
				try {
					findUserAgent : while ((line = bin.readLine()) != null) {
						if (line.charAt(0) == POUND && line.indexOf(USER_AGENT) != -1) {
							try {
								JSONObject json = new JSONObject(line.substring(1));
								userAgents.put(strongName, json.getString(USER_AGENT));
								break findUserAgent;
							} catch (JSONException e) {
								e.printStackTrace();
								continue;
							}
						}
						continue;
					}
				} finally {
					bin.close();
				}
				if(userAgents.get(strongName) == null){
					userAgents.put(strongName, strongName);
				}
			} catch (IOException e) {
				userAgents.put(strongName, strongName);
			}
		}
	}

	public HashMap<String, String> getAllPermutationStrongNames() {
		return userAgents;
	}
	
	public String getSymbolMapsDirectory() {
		return symbolMapsDirectory;
	}
	
}
