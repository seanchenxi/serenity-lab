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
package com.seanchenxi.resteasy.autobean.generator;

import java.io.PrintWriter;
import java.util.HashMap;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.seanchenxi.resteasy.autobean.client.RESTRequest;
import com.seanchenxi.resteasy.autobean.client.RESTServiceProxy;

public class RESTServiceGenerator extends Generator {
	
	private static final String METHOD_PREFIX = RequestBuilder.class.getSimpleName();
	private static final String REST_REQUEST = RESTRequest.class.getSimpleName();

	private HashMap<String, String> httpMethods = new HashMap<String, String>();
	private HashMap<String, String> restPaths = new HashMap<String, String>();
	
	private String rootPath;
	
	@Override
	public String generate(TreeLogger logger, GeneratorContext context,
			String typeName) throws UnableToCompleteException {
		
		TypeOracle oracle = context.getTypeOracle();
		
		String asyncTypeName = typeName + "Async";
		JClassType asyncType = null;
		String packageName = null;
		String simpleName = null;
		try{
			asyncType = oracle.findType(asyncTypeName);
			asyncType = asyncType.isInterface();
			packageName = asyncType.getPackage().getName();
			simpleName = asyncType.getSimpleSourceName() + "Impl";
		}catch (Exception e) {
			logger.log(TreeLogger.ERROR, asyncTypeName + " is not found or is not an interface", e);
		    throw new UnableToCompleteException();
		}

		PrintWriter printWriter = context.tryCreate(logger, packageName, simpleName);
		if (printWriter == null) {
			return packageName + "." + simpleName;
		}
		
    // This method will complete httpMethods map and restPaths map;
		parseSyncType(typeName, oracle, logger);
    
		ClassSourceFileComposerFactory factory = new ClassSourceFileComposerFactory(packageName, simpleName);
		factory.addImplementedInterface(asyncTypeName);
		factory.setSuperclass(RESTServiceProxy.class.getName());
		factory.addImport(RequestBuilder.class.getName());
		factory.addImport(RESTRequest.class.getName());
		
		SourceWriter writer = factory.createSourceWriter(context, printWriter);
		writer.indent();
		writer.println("");
		for(JMethod asyncMethod : asyncType.getMethods()){
			writeAsyncMethod(asyncTypeName, asyncMethod, writer, logger);
		}
		writer.outdent();
		writer.commit(logger);
		return factory.getCreatedClassName();
	}
	
	private void writeAsyncMethod(String asyncTypeName, JMethod asyncMethod, SourceWriter writer, TreeLogger logger){
		String methodName = asyncMethod.getName();
		String httpMethod = httpMethods.get(methodName);
		String restPath = restPaths.get(methodName);
		
		if(restPath == null || restPath.isEmpty()){
			logger.log(Type.WARN, "Method " + methodName + " has no defined REST Path value, ingore");
			return; // method must have a rest path
		}
		
		StringBuilder methodParams = new StringBuilder();
//		StringBuilder requestDatas = new StringBuilder(); //TODO treat request data
		String clazz = null;
		
		boolean isMethodParamsBegin = true;
//		boolean isRequestDatasBegin = true;
		for(JParameter parameter : asyncMethod.getParameters()){
			if(!isMethodParamsBegin) methodParams.append(", ");
			String name = parameter.getName();
			JType type = parameter.getType();
			JParameterizedType pType = parameter.getType().isParameterized();
			if(pType != null){
				clazz = pType.getTypeArgs()[0].getQualifiedSourceName();
				methodParams.append(pType.getParameterizedQualifiedSourceName());
			}else{
//				if(!isRequestDatasBegin) requestDatas.append(", ");
//				requestDatas.append(name);
//				isRequestDatasBegin = false;
				methodParams.append(type.getQualifiedSourceName());
			}
			methodParams.append(" ");
			methodParams.append(name);
			isMethodParamsBegin = false;
		}

		if(clazz == null || clazz.isEmpty()){
			logger.log(Type.WARN, "Method " + methodName + " has no AsyncCallback<T> paramter, ingore");
			return; //should have AsyncCallback<T> parameter
		}
		String resourceName = asyncTypeName+"."+asyncMethod.getName();
		writer.println("@Override");
		writer.println("public void %s(%s){", methodName, methodParams);
		writer.println("	%1s request = new %1$s(%2$s, %3$s, \"%4$s\");", REST_REQUEST, httpMethod, restPath, resourceName);
//		writer.println("	request.setRequestData(%s);", requestDatas); //TODO treat request data
		writer.println("	invoke(request, %s.class, callback);", clazz);
		writer.println("}");
		writer.println("");
	}
	
	private JClassType parseSyncType(String typeName, TypeOracle oracle, TreeLogger logger) throws UnableToCompleteException {
		JClassType syncType = null;
		try{
			syncType = oracle.findType(typeName);
			syncType = syncType.isInterface();
			rootPath = getRestPath(syncType.getAnnotation(Path.class));
			for(JMethod syncMethod : syncType.getMethods()){
				if(!syncMethod.isAnnotationPresent(Path.class)) continue; // means is not registered as REST service
				String key = syncMethod.getName();
				restPaths.put(key,  getMethodRestPath(rootPath, syncMethod));
				httpMethods.put(key, getHttpMethod(syncMethod));
			}
			return syncType;
		}catch (Exception e) {
			logger.log(TreeLogger.ERROR, typeName + " is not found or is not an interface", e);
			throw new UnableToCompleteException();
		}
	}
	
	private String getHttpMethod(JMethod syncMethod){
		if(syncMethod.isAnnotationPresent(GET.class)){
			return METHOD_PREFIX + ".GET";
		}else if(syncMethod.isAnnotationPresent(DELETE.class)){
			return METHOD_PREFIX + ".DELETE";
		}else if(syncMethod.isAnnotationPresent(HEAD.class)){
			return METHOD_PREFIX + ".HEAD";
		}else if(syncMethod.isAnnotationPresent(POST.class)){
			return METHOD_PREFIX + ".POST";
		}else if(syncMethod.isAnnotationPresent(PUT.class)){
			return METHOD_PREFIX + ".PUT";
		}
		return METHOD_PREFIX + ".GET";
	}
	
	private String getRestPath(Path path){
		String relativePath = path.value().trim();
		if(!relativePath.startsWith("/")) relativePath = "/" + relativePath;
		if(relativePath.endsWith("/")) relativePath = relativePath.substring(0, relativePath.length() - 2);
		return relativePath;
	}

	 private String getMethodRestPath(String rootPath, JMethod method){
	    String methodPath = rootPath + getRestPath(method.getAnnotation(Path.class));
	    for(JParameter param : method.getParameters()){
	      if(param.isAnnotationPresent(PathParam.class)){
	        String paramPath = param.getAnnotation(PathParam.class).value();
	        methodPath = methodPath.replace("{" + paramPath + "}", "\"+"+param.getName() + "+\"");
	      }
	    }
	    methodPath = ("\"" + methodPath.trim() + "\"").replace("+\"\"+", "+").replaceAll("(\"|\\+)\\1", "$1");
	    if(methodPath.endsWith("+\"")){
	      methodPath = methodPath.substring(0, methodPath.length() - 2);
	    }
	    return methodPath;
	  }
	 
}
