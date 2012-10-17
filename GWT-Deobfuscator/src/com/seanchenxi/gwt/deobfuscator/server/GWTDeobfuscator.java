package com.seanchenxi.gwt.deobfuscator.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.LogRecord;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gwt.logging.server.StackTraceDeobfuscator;

public class GWTDeobfuscator {

	private static final char POUND = '#';
	protected static final String OPEN_PARENTHESIS = "(";
	protected static final String CLOSE_PARENTHESIS = ")";
	protected static final String AT = "at";
	protected static final String COMMA = ",";
	protected static final String SEMICOLON = ";";
	protected static final String UNKNOWN = "Unknown";
	protected static final String SPACE = " ";
	protected static final String PERIOD = ".";
	protected static final String FORWARD_SLASH = "/";
	protected static final String COLON = ":";
	
	public static final String AT_UNKNOWN = AT + SPACE + UNKNOWN + PERIOD;
	protected static final String USER_AGENT = "user.agent";

	// From JsniRef class, which is in gwt-dev and so can't be accessed here
	// TODO(unnurg) once there is a place for shared code, move this to there.
	protected static Pattern JsniRefPattern = Pattern.compile("@?([^:]+)::([^(]+)(\\((.*)\\))?");

	// The javadoc for StackTraceElement.getLineNumber() says it returns -1 when
	// the line number is unavailable
	protected static final int LINE_NUMBER_UNKNOWN = -1;

	protected File symbolMapsDirectory;

	// Key = Module Name, Value = Symbol Maps
	protected Map<String, SymbolMap> symbolMaps;

	public GWTDeobfuscator(String symbolMapsDirectory) {
		setSymbolMapsDirectory(symbolMapsDirectory);
	}
	
	public HashMap<String, String> getAllPermutationStrongNames(){
		HashMap<String, String> map = new HashMap<String, String>();
		for(String strongName : symbolMaps.keySet()){
			map.put(strongName, symbolMaps.get(strongName).getUserAgent());
		}
		return map;
	}
	
	public String deobfuscatePrintedStackTraceLine(String trace, String strongName) {
		if(strongName == null || strongName.trim().isEmpty() || trace == null || !trace.startsWith(AT)) 
			return trace;
		SymbolMap map = symbolMaps.get(strongName);
		if(map == null) return trace;	
		int methodStartIndex = AT_UNKNOWN.length();
		int methodEndIndex = trace.indexOf(OPEN_PARENTHESIS, methodStartIndex);
		String _methodName = trace.substring(methodStartIndex, methodEndIndex);
		String symbolData = map.get(_methodName.trim());
		if (symbolData == null) return trace;
		
		// jsniIdent, className, memberName, sourceUri, sourceLine
		String[] parts = symbolData.split(COMMA);
		if (parts.length != 5) return trace;
		
		String[] ref = parse(parts[0].substring(0,parts[0].lastIndexOf(CLOSE_PARENTHESIS) + 1));
		String declaringClass;
		String methodName;
		if (ref != null) {
			declaringClass = ref[0];
			methodName = ref[1];
		} else {
			declaringClass = UNKNOWN;
			methodName = _methodName;
		}

		// parts[3] contains the source file URI or "Unknown"
		String filename = UNKNOWN.equals(parts[3]) ? null : parts[3].substring(parts[3].lastIndexOf(FORWARD_SLASH) + 1);
		
		int lineNumber = LINE_NUMBER_UNKNOWN;
		try{
			int lineNumberStartIndex = trace.indexOf(COLON, methodEndIndex);
			String _lineNumber = trace.substring(lineNumberStartIndex, trace.indexOf(CLOSE_PARENTHESIS, lineNumberStartIndex));
			lineNumber = Integer.parseInt(_lineNumber);
		}catch(Exception e){
			lineNumber = LINE_NUMBER_UNKNOWN;
		}
		/*
		 * When lineNumber is LINE_NUMBER_UNKNOWN, either because
		 * compiler.stackMode is not emulated or
		 * compiler.emulatedStack.recordLineNumbers is false, use the
		 * method declaration line number from the symbol map.
		 */
		if (lineNumber == LINE_NUMBER_UNKNOWN) {
			lineNumber = Integer.parseInt(parts[4]);
		}

		return new StringBuilder(AT).append(SPACE).append(new StackTraceElement(declaringClass, methodName, filename, lineNumber)).toString();
	}

	/**
	 * Copied form {@link StackTraceDeobfuscator#deobfuscateLogRecord(lr, strongName)}
	 */
	public LogRecord deobfuscateLogRecord(LogRecord lr, String strongName) {
		if (lr.getThrown() != null && strongName != null) {
			lr.setThrown(deobfuscateThrowable(lr.getThrown(), strongName));
		}
		return lr;
	}

	/**
	 * Copied form {@link StackTraceDeobfuscator#deobfuscateStackTrace(st, strongName)}
	 */
	public StackTraceElement[] deobfuscateStackTrace(StackTraceElement[] st, String strongName) {
		StackTraceElement[] newSt = new StackTraceElement[st.length];
		for (int i = 0; i < st.length; i++) {
			newSt[i] = resymbolize(st[i], strongName);
		}
		return newSt;
	}

	/**
	 * Copied form {@link StackTraceDeobfuscator#resymbolize(ste, strongName)}
	 */
	public StackTraceElement resymbolize(StackTraceElement ste, String strongName) {
		SymbolMap map = symbolMaps.get(strongName);
		String symbolData = map == null ? null : map.get(ste.getMethodName());

		if (symbolData != null) {
			// jsniIdent, className, memberName, sourceUri, sourceLine
			String[] parts = symbolData.split(",");
			if (parts.length == 5) {
				String[] ref = parse(parts[0].substring(0,
						parts[0].lastIndexOf(')') + 1));

				String declaringClass;
				String methodName;
				if (ref != null) {
					declaringClass = ref[0];
					methodName = ref[1];
				} else {
					declaringClass = ste.getClassName();
					methodName = ste.getMethodName();
				}

				// parts[3] contains the source file URI or "Unknown"
				String filename = "Unknown".equals(parts[3]) ? null : parts[3]
						.substring(parts[3].lastIndexOf('/') + 1);

				int lineNumber = ste.getLineNumber();
				/*
				 * When lineNumber is LINE_NUMBER_UNKNOWN, either because
				 * compiler.stackMode is not emulated or
				 * compiler.emulatedStack.recordLineNumbers is false, use the
				 * method declaration line number from the symbol map.
				 */
				if (lineNumber == LINE_NUMBER_UNKNOWN) {
					lineNumber = Integer.parseInt(parts[4]);
				}

				return new StackTraceElement(declaringClass, methodName,
						filename, lineNumber);
			}
		}
		// If anything goes wrong, just return the unobfuscated element
		return ste;
	}

	/**
	 * Copied form {@link StackTraceDeobfuscator#deobfuscateThrowable(old, strongName)}
	 */
	private Throwable deobfuscateThrowable(Throwable old, String strongName) {
		Throwable t = new Throwable(old.getMessage());
		if (old.getStackTrace() != null) {
			t.setStackTrace(deobfuscateStackTrace(old.getStackTrace(),
					strongName));
		} else {
			t.setStackTrace(new StackTraceElement[0]);
		}
		if (old.getCause() != null) {
			t.initCause(deobfuscateThrowable(old.getCause(), strongName));
		}
		return t;
	}
	
	public void setSymbolMapsDirectory(String symbolMapsDirectory) {
		// permutations are unique, no need to clear the symbolMaps hash map
		this.symbolMapsDirectory = new File(symbolMapsDirectory);
		loadAllSymbolMaps();
	}

	/**
	 * Transformed form {@link StackTraceDeobfuscator#loadSymbolMap(String strongName)}
	 */
	private void loadAllSymbolMaps(){
		symbolMaps = new HashMap<String, SymbolMap>();
		for(File file : symbolMapsDirectory.listFiles()){
			if(!file.isFile() && !file.getName().endsWith(".symbolMap")){
				continue;
			}
			final String strongName = file.getName().split("\\.")[0];
			SymbolMap map = new SymbolMap(strongName);
			String line;
			try {
				BufferedReader bin = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				try {
					while ((line = bin.readLine()) != null) {
						if (line.charAt(0) == POUND) {
							if(line.indexOf(USER_AGENT) != -1 && null == map.getUserAgent()){
								try {
									JSONObject json = new JSONObject(line.substring(1));
									map.setUserAgent(json.getString(USER_AGENT));
								} catch (JSONException e) {
									e.printStackTrace();
									continue;
								}
							}
							continue;
						}
						int idx = line.indexOf(',');
						map.put(new String(line.substring(0, idx)), line.substring(idx + 1));
					}
				} finally {
					bin.close();
				}
			} catch (IOException e) {
				// use empty symbol map to avoid repeated lookups
				map = new SymbolMap(strongName);
			}
			symbolMaps.put(strongName, map);
		}
	}

	/**
	 * Copied form {@link StackTraceDeobfuscator#parse(String refString)}
	 */
	private String[] parse(String refString) {
		Matcher matcher = JsniRefPattern.matcher(refString);
		if (!matcher.matches()) {
			return null;
		}
		String className = matcher.group(1);
		String memberName = matcher.group(2);
		String[] toReturn = new String[] { className, memberName };
		return toReturn;
	}
}
