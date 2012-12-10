package com.seanchenxi.gwt.storage.client.cache;

import java.util.HashMap;

import com.seanchenxi.gwt.storage.client.StorageKey;

public class StorageMemoryCache implements StorageCache {

  private final HashMap<StorageKey<?>, Object> map;

  public StorageMemoryCache() {
    map = new HashMap<StorageKey<?>, Object>();
  }

  @Override
  public void clear() {
    map.clear();
  }

  @Override
  public <T> boolean containsValue(T value) {
    return map.containsValue(value);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T get(StorageKey<T> key) {
    Object val = map.get(key);
    return val != null ? (T) val : null;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T put(StorageKey<T> key, T value) {
    Object old = map.put(key, value);
    return old != null ? (T) old : null;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T remove(StorageKey<T> key) {
    Object val = map.remove(key);
    return val != null ? (T) val : null;
  }
}