package com.seanchenxi.gwt.storage.client.cache;

import com.seanchenxi.gwt.storage.client.StorageKey;

public class NoStorageCache implements StorageCache {

  @Override
  public void clear() {
  }

  @Override
  public <T> T get(StorageKey<T> key) {
    return null;
  }

  @Override
  public <T> T put(StorageKey<T> key, T value) {
    return null;
  }

  @Override
  public <T> T remove(StorageKey<T> key) {
    return null;
  }

}
