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
import java.util.HashSet;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.seanchenxi.resteasy.autobean.share.RESTService;

public class ThrowableFactoryGenerator extends Generator {
  
  private static HashSet<String> THROWABLES = new HashSet<String>();
  static{
    THROWABLES.add("java.lang.ArithmeticException");
    THROWABLES.add("java.lang.ArrayIndexOutOfBoundsException");
    THROWABLES.add("java.lang.ArrayStoreException");
    THROWABLES.add("java.lang.ClassCastException");
    THROWABLES.add("java.lang.Exception");
    THROWABLES.add("java.lang.IllegalArgumentException");
    THROWABLES.add("java.lang.IllegalStateException");
    THROWABLES.add("java.lang.IndexOutOfBoundsException");
    THROWABLES.add("java.lang.NegativeArraySizeException");
    THROWABLES.add("java.lang.NullPointerException");
    THROWABLES.add("java.lang.NumberFormatException");
    THROWABLES.add("java.lang.RuntimeException");
    THROWABLES.add("java.lang.StringIndexOutOfBoundsException");
    THROWABLES.add("java.lang.UnsupportedOperationException");
    THROWABLES.add("java.lang.Throwable");
  }

	@Override
	public String generate(TreeLogger logger, GeneratorContext context,
			String typeName) throws UnableToCompleteException {
		TypeOracle oracle = context.getTypeOracle();
		JClassType type = oracle.findType(typeName);
		type = type.isInterface();
		if (type == null) {
			logger.log(TreeLogger.ERROR, typeName + " is not found");
		    throw new UnableToCompleteException();
		}
		
		String packageName = type.getPackage().getName();
		String simpleName = type.getSimpleSourceName() + "Impl";
		PrintWriter printWriter = context.tryCreate(logger, packageName, simpleName);
		if (printWriter == null) {
			return packageName + "." + simpleName;
		}

		ClassSourceFileComposerFactory factory = new ClassSourceFileComposerFactory(packageName, simpleName);
		factory.addImplementedInterface(typeName);
		factory.addImport(Throwable.class.getName());
		
		SourceWriter writer = factory.createSourceWriter(context, printWriter);
		writer.indent();
		writer.println("@Override");
		writer.println("public Throwable create(String className, String message){");
		for (String qname : getAllThrowableTypes(oracle, logger)) {
			writer.println("	if(\"" + qname + "\".equals(className)) {");
			writer.println("		return new " + qname + " (message);");
			writer.println("	}");
		}
		writer.println("	return new Throwable(message);");
		writer.println("}");
		writer.outdent();
		writer.commit(logger);
		return factory.getCreatedClassName();
	}

	private HashSet<String> getAllThrowableTypes(TypeOracle oracle,
			TreeLogger logger) {
		HashSet<String> set = new HashSet<String>();
		try {
			JType[] params = new JType[1];
			params[0] = oracle.parse("java.lang.String");
			JClassType[] svcTypes = oracle.getType(RESTService.class.getName()).getSubtypes();
			for(JClassType type : svcTypes){
			  for(JMethod method : type.getMethods()){
			    for(JClassType throwable : method.getThrows()){
			      if(null != throwable.getConstructor(params))
			        set.add(throwable.getQualifiedSourceName());
			      else
			        logger.log(Type.WARN, throwable.getQualifiedSourceName() + " will be ignored for serialization, it must at least have a constructor with string type message as parameter.");
			    }
			  }
			}
		} catch (Exception e) {
			logger.log(Type.ERROR, e.getMessage(), e);
		}
		set.addAll(THROWABLES);
		return set;
	}

}
