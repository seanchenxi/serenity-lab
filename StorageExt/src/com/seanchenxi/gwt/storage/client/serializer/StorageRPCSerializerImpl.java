package com.seanchenxi.gwt.storage.client.serializer;

import java.util.HashMap;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.impl.ClientSerializationStreamReader;

final class StorageRPCSerializerImpl implements StorageSerializer {

  private final static HashMap<Class<?>, StorageValueType> TYPE_MAP = new HashMap<Class<?>, StorageValueType>();
  private final static StorageTypeSerializer TYPE_SERIALIZER = GWT.create(StorageTypeSerializer.class);
  static {
    TYPE_MAP.put(boolean[].class, StorageValueType.BOOLEAN_VECTOR);
    TYPE_MAP.put(byte[].class, StorageValueType.BYTE_VECTOR);
    TYPE_MAP.put(char[].class, StorageValueType.CHAR_VECTOR);
    TYPE_MAP.put(double[].class, StorageValueType.DOUBLE_VECTOR);
    TYPE_MAP.put(float[].class, StorageValueType.FLOAT_VECTOR);
    TYPE_MAP.put(int[].class, StorageValueType.INT_VECTOR);
    TYPE_MAP.put(long[].class, StorageValueType.LONG_VECTOR);
    TYPE_MAP.put(short[].class, StorageValueType.SHORT_VECTOR);
    TYPE_MAP.put(String[].class, StorageValueType.STRING_VECTOR);

    TYPE_MAP.put(Boolean[].class, StorageValueType.BOOLEAN_VECTOR);
    TYPE_MAP.put(Byte[].class, StorageValueType.BYTE_VECTOR);
    TYPE_MAP.put(Character[].class, StorageValueType.CHAR_VECTOR);
    TYPE_MAP.put(Double[].class, StorageValueType.DOUBLE_VECTOR);
    TYPE_MAP.put(Float[].class, StorageValueType.FLOAT_VECTOR);
    TYPE_MAP.put(Integer[].class, StorageValueType.INT_VECTOR);
    TYPE_MAP.put(Long[].class, StorageValueType.LONG_VECTOR);
    TYPE_MAP.put(Short[].class, StorageValueType.SHORT_VECTOR);

    TYPE_MAP.put(boolean.class, StorageValueType.BOOLEAN);
    TYPE_MAP.put(byte.class, StorageValueType.BYTE);
    TYPE_MAP.put(char.class, StorageValueType.CHAR);
    TYPE_MAP.put(double.class, StorageValueType.DOUBLE);
    TYPE_MAP.put(float.class, StorageValueType.FLOAT);
    TYPE_MAP.put(int.class, StorageValueType.INT);
    TYPE_MAP.put(long.class, StorageValueType.LONG);
    TYPE_MAP.put(short.class, StorageValueType.SHORT);
    TYPE_MAP.put(String.class, StorageValueType.STRING);
  }

  @SuppressWarnings("unchecked")
  public <T> T deserialize(Class<T> clazz, String encodedString) throws SerializationException {
    if (encodedString == null) {
      return null;
    }
    ClientSerializationStreamReader reader = new ClientSerializationStreamReader(TYPE_SERIALIZER);
    try {
      reader.prepareToRead(encodedString);
    } catch (IncompatibleRemoteServiceException irse) {
      String message = irse.getMessage();
      if (message == null || !message.contains("Expecting version")
          && message.contains("unknown flag")) {
        throw irse;
      }
    }
    Object obj = findType(clazz).read(reader);
    return obj != null ? (T) obj : null;
  }

  public <T> String serialize(Class<T> clazz, T instance) throws SerializationException {
    if (instance == null) {
      return null;
    }
    StorageSerializationStreamWriter writer = new StorageSerializationStreamWriter(TYPE_SERIALIZER);
    writer.prepareToWrite();
    findType(clazz).write(writer, instance);
    return writer.toString();
  }

  private StorageValueType findType(Class<?> clazz) {
    StorageValueType type = TYPE_MAP.get(clazz);
    if (type == null) {
      type = clazz.isArray() ? StorageValueType.OBJECT_VECTOR : StorageValueType.OBJECT;
    }
    return type;
  }

}
