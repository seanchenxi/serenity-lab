package com.seanchenxi.gwt.storage.client.cache;

import com.seanchenxi.gwt.storage.client.StorageKey;

public interface StorageCache {

  void clear();

  <T> T get(StorageKey<T> key);

  <T> T put(StorageKey<T> key, T value);

  <T> T remove(StorageKey<T> key);

}