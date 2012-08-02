package com.seanchenxi.resteasy.autobean.generator;

import java.io.PrintWriter;
import java.util.HashSet;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

public class ThrowableFactoryGenerator extends Generator {

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
		
		SourceWriter writer = factory.createSourceWriter(context, printWriter);
		writer.indent();
		writer.println("@Override");
		writer.println("public Throwable create(String className, String message){");
		for (String qname : getAllThrowableTypes(oracle, logger)) {
			writer.println("	if(\"" + qname + "\".equals(className)) {");
			writer.println("		return new " + qname + " (message);");
			writer.println("	}");
		}
		writer.println("	return null;");
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
			JClassType[] types = oracle.getType(Throwable.class.getName()).getSubtypes();
			for (JClassType type : types) {
				try {
					String name = type.getQualifiedSourceName();
					if (name.startsWith("com.pcis.phenix")
							&& null != type.getConstructor(params)) {
						set.add(name);
					}
				} catch (Exception e) {
				}
			}
		} catch (Exception e) {
			logger.log(Type.ERROR, e.getMessage(), e);
		}
		set.add("java.lang.ArithmeticException");
		set.add("java.lang.ArrayIndexOutOfBoundsException");
		set.add("java.lang.ArrayStoreException");
		set.add("java.lang.ClassCastException");
		set.add("java.lang.Exception");
		set.add("java.lang.IllegalArgumentException");
		set.add("java.lang.IllegalStateException");
		set.add("java.lang.IndexOutOfBoundsException");
		set.add("java.lang.NegativeArraySizeException");
		set.add("java.lang.NullPointerException");
		set.add("java.lang.NumberFormatException");
		set.add("java.lang.RuntimeException");
		set.add("java.lang.StringIndexOutOfBoundsException");
		set.add("java.lang.UnsupportedOperationException");
		set.add("java.lang.Throwable");
		return set;
	}

}
