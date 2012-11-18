package com.seanchenxi.gwt.storage.rebind;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXParseException;

import com.google.gwt.core.ext.CachedGeneratorResult;
import com.google.gwt.core.ext.ConfigurationProperty;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.IncrementalGenerator;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.RebindMode;
import com.google.gwt.core.ext.RebindResult;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.dev.resource.Resource;
import com.google.gwt.dev.resource.ResourceOracle;
import com.google.gwt.dev.util.Util;
import com.google.gwt.uibinder.rebind.W3cDomHelper;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.rebind.rpc.CachedRpcTypeInformation;
import com.google.gwt.user.rebind.rpc.ProxyCreator;
import com.google.gwt.user.rebind.rpc.SerializableTypeOracle;
import com.google.gwt.user.rebind.rpc.SerializableTypeOracleBuilder;
import com.google.gwt.user.rebind.rpc.TypeSerializerCreator;

public class StorageTypeSerializerGenerator extends IncrementalGenerator {

  private static final String CACHED_EXT_TYPE_INFO_KEY = "cached-ext-type-info";
  private static final String CLASS_NODE = "class";
  private static final long GENERATOR_VERSION_ID = 1L;

  private static final String SERIALIZATION_CONFIG = "storage-serialization.xml";
  private static final String TPYE_FINDER = "storage.type.finder";

  private static final List<String> TPYE_FINDER_VALUES = Arrays.asList("rpc", "xml", "rpc_xml");

  private static final String TYPE_SERIALIZER_SUFFIX = "Impl";

  private static void addIfIsValidType(HashSet<JType> serializables, JType jType) {
    if (jType != null && jType.isInterface() == null)
      serializables.add(jType);
  }

  @Override
  public RebindResult generateIncrementally(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
    TypeOracle typeOracle = context.getTypeOracle();

    JClassType serializerType = typeOracle.findType(typeName);
    if (serializerType == null || serializerType.isInterface() == null)
      throw new UnableToCompleteException();

    HashSet<JType> serializables = new HashSet<JType>();

    int typeFinder = getTypeFinder(logger, context.getPropertyOracle());
    if (typeFinder != 1)
      findRPCSerializableTypes(logger, typeOracle, serializables);
    if (typeFinder != 0)
      findXMLSerializableTypes(logger, typeOracle, context.getResourcesOracle(), serializables);

    String typeSerializerClassName = serializerType.getQualifiedSourceName() + TYPE_SERIALIZER_SUFFIX;
    String typeSerializerSimpleName = serializerType.getSimpleSourceName() + TYPE_SERIALIZER_SUFFIX;
    JClassType typeSerializer = typeOracle.findType(typeSerializerClassName);

    SerializableTypeOracle serializationOracle = buildSerializableTypeOracle(logger, context, serializables);

    if (typeSerializer != null && typeSerializer.isClass() != null
        && isNothingChanged(logger, context, serializables, serializationOracle)) {
      return new RebindResult(RebindMode.USE_EXISTING, typeSerializerClassName);
    }

    TypeSerializerCreator tsc =
        new TypeSerializerCreator(logger, serializationOracle, serializationOracle, context,
            typeSerializerClassName, typeSerializerSimpleName);
    tsc.realize(logger);

    if (context.isGeneratorResultCachingEnabled()) {
      RebindResult result = new RebindResult(RebindMode.USE_PARTIAL_CACHED, typeSerializerClassName);
      CachedRpcTypeInformation cti = new CachedRpcTypeInformation(serializationOracle, serializationOracle, serializables, new HashSet<JType>());
      result.putClientData(ProxyCreator.CACHED_TYPE_INFO_KEY, cti);
      return result;
    } else {
      return new RebindResult(RebindMode.USE_ALL_NEW_WITH_NO_CACHING, typeSerializerClassName);
    }
  }

  @Override
  public long getVersionId() {
    return GENERATOR_VERSION_ID;
  }

  private SerializableTypeOracle buildSerializableTypeOracle(TreeLogger logger, GeneratorContext context, HashSet<JType> serializables) throws UnableToCompleteException {
    SerializableTypeOracleBuilder builder = new SerializableTypeOracleBuilder(logger, context.getPropertyOracle(), context);
    for (JType type : serializables) {
      builder.addRootType(logger, type);
    }
    return builder.build(logger);
  }

  private void findRPCSerializableTypes(TreeLogger logger, TypeOracle typeOracle, HashSet<JType> serializables) {
    JClassType remoteSvcIntf = typeOracle.findType(RemoteService.class.getName());
    JClassType[] remoteSvcTypes = remoteSvcIntf.getSubtypes();
    for (JClassType rmoteSvcType : remoteSvcTypes) {
      for (JMethod method : rmoteSvcType.getMethods()) {
        JType type = method.getReturnType();
        if (JPrimitiveType.VOID != type)
          addIfIsValidType(serializables, type);
        for (JType param : method.getParameterTypes()) {
          addIfIsValidType(serializables, param);
        }
      }
    }
  }

  private void findXMLSerializableTypes(TreeLogger logger, TypeOracle typeOracle, ResourceOracle resourceOracle, HashSet<JType> serializables) {
    Resource resource = null;
    for (String key : resourceOracle.getResourceMap().keySet()) {
      if (key.endsWith(SERIALIZATION_CONFIG)) {
        resource = resourceOracle.getResourceMap().get(key);
        break;
      }
    }
    if (null == resource)
      return;
    try {
      String content = Util.readStreamAsString(resource.openContents());
      Document doc =
          new W3cDomHelper(logger, resourceOracle).documentFor(content, resource.getPath());
      Element el = doc.getDocumentElement();
      NodeList nodeList = el.getChildNodes();
      for (int i = 0; i < nodeList.getLength(); i++) {
        Node node = nodeList.item(i);
        if (CLASS_NODE.equalsIgnoreCase(node.getNodeName())) {
          String typeName = node.getTextContent();
          if (typeName == null || typeName.trim().isEmpty())
            continue;
          addIfIsValidType(serializables, typeOracle.findType(typeName));
        }
      }
    } catch (SAXParseException e) {
      logger.log(Type.ERROR, "Error parsing XML (line " + e.getLineNumber() + "): "
          + e.getMessage(), e);
    } catch (IOException e) {
      logger.log(Type.ERROR, "Error reading XML Source: " + e.getMessage(), e);
    }
  }

  private int getTypeFinder(TreeLogger logger, PropertyOracle propertyOracle) {
    try {
      ConfigurationProperty property = propertyOracle.getConfigurationProperty(TPYE_FINDER);
      List<String> values = property.getValues();
      return Math.max(0, TPYE_FINDER_VALUES.indexOf(values.get(0)));
    } catch (Exception e) {
      return 0;
    }
  }

  @SuppressWarnings("unchecked")
  private boolean isNothingChanged(TreeLogger logger, GeneratorContext context, HashSet<JType> serializables, SerializableTypeOracle serializationOracle) {// caching use
    CachedGeneratorResult cachedResult = context.getCachedGeneratorResult();
    if (cachedResult == null || !context.isGeneratorResultCachingEnabled())
      return false;
    Object obj = context.getCachedGeneratorResult().getClientData(ProxyCreator.CACHED_TYPE_INFO_KEY);
    if (obj == null) {
      return false;
    }
    if (!((CachedRpcTypeInformation) obj).checkTypeInformation(logger, context.getTypeOracle(),
        serializationOracle, serializationOracle)) {
      return false;
    }
    HashSet<JType> oldSerializables = (HashSet<JType>) cachedResult.getClientData(CACHED_EXT_TYPE_INFO_KEY);
    return oldSerializables != null && !oldSerializables.isEmpty()
        && serializables.size() == oldSerializables.size()
        && serializables.equals(oldSerializables);
  }
}
