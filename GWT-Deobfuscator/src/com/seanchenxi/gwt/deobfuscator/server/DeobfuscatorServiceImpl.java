package com.seanchenxi.gwt.deobfuscator.server;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.seanchenxi.gwt.deobfuscator.client.DeobfuscatorService;

@SuppressWarnings("serial")
public class DeobfuscatorServiceImpl extends RemoteServiceServlet implements DeobfuscatorService {

	private static final Logger Log = Logger.getLogger(DeobfuscatorServiceImpl.class.getName());
	private static final String PARAMETER_SYMBOL_MAPS = "symbolMaps";
	private static final HashMap<String, GWTDeobfuscator> DEOBFUSCATORS = new HashMap<String, GWTDeobfuscator>();
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String path = getServletContext().getInitParameter(PARAMETER_SYMBOL_MAPS);
		if(path != null && !path.trim().isEmpty()){
			Log.log(Level.INFO, "set symbolMaps to path " + path);
			loadDeobfuscator(path);			
		}	
	    if (DEOBFUSCATORS == null || DEOBFUSCATORS.isEmpty()) {
	      Log.log(Level.WARNING, "In order to enable stack trace deobfuscation, please specify the '"
	          + PARAMETER_SYMBOL_MAPS + " in your web.xml");
	    }
	}

	@Override
	public HashMap<String, HashMap<String, String>> setModuleSymbolMapPath(String path){
		loadDeobfuscator(path);
		return getAvailableModules();
	}
	
	@Override
	public HashMap<String, HashMap<String, String>> getAvailableModules(){
		 HashMap<String, HashMap<String, String>> map = new HashMap<String, HashMap<String, String>>();
		 for(String module : DEOBFUSCATORS.keySet()){
			 GWTDeobfuscator d = DEOBFUSCATORS.get(module);
			 if(d != null)
				 map.put(module, d.getAllPermutationStrongNames());
		 }
		return map;
	}
	
	@Override
	public String deobfuscate(String moduleName, String strongName, String input) { 
		GWTDeobfuscator deobfuscator = DEOBFUSCATORS.get(moduleName);
		if(deobfuscator == null){
			Log.warning("deobfuscate - module " + moduleName + " not found.");
			return input;
		}
		StringBuilder sb = new StringBuilder();
		for(String line : input.split("\\\n")){
			String trace = line.replaceAll("[\\r\\n\\t]", "").trim();
			if(trace.startsWith("at")){
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;");
			}
			sb.append(deobfuscator.deobfuscatePrintedStackTraceLine(trace, strongName)).append("<br/>");
		}
		return sb.toString() ;
	}

	private void loadDeobfuscator(String path) {
		DEOBFUSCATORS.clear();
		File symbolMapsDirectory = new File(path);
		if (symbolMapsDirectory.isDirectory()) {
			for (File module : symbolMapsDirectory.listFiles()) {
				if (!module.isDirectory()) continue;
				checkModuleFolder: for (File symbolMaps : module.listFiles()) {
					if (symbolMaps.isDirectory() 
							&& PARAMETER_SYMBOL_MAPS.equalsIgnoreCase(symbolMaps.getName()) 
							&& symbolMaps.list().length > 0 ) {
						DEOBFUSCATORS.put(module.getName(), new GWTDeobfuscator(symbolMaps.getAbsolutePath()));
						Log.info("Added Module ("+module.getName() + ") Symbol Maps at " + symbolMaps.getAbsolutePath());
						break checkModuleFolder;
					}
				}
			}
		}
	}
}
