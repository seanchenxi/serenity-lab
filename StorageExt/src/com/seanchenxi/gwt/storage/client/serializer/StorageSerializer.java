package com.seanchenxi.gwt.storage.client.serializer;

import com.google.gwt.user.client.rpc.SerializationException;

public interface StorageSerializer {

  <T> T deserialize(Class<T> clazz, String encodedString) throws SerializationException;

  <T> String serialize(Class<T> clazz, T instance) throws SerializationException;
}