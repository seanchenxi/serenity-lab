package com.seanchenxi.gwt.storage.client;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.core.client.JavaScriptException;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.SerializationException;
import com.seanchenxi.gwt.storage.client.cache.StorageCache;
import com.seanchenxi.gwt.storage.client.serializer.StorageSerializer;

public final class StorageExt {

  private static StorageExt localStorage;
  private static StorageExt sessionStorage;
  private static final StorageSerializer TYPE_SERIALIZER = GWT.create(StorageSerializer.class);

  public static StorageExt getLocalStorage() {
    if (localStorage == null && Storage.isLocalStorageSupported()) {
      localStorage = new StorageExt(Storage.getLocalStorageIfSupported());
    }
    return localStorage;
  }

  public static StorageExt getSessionStorage() {
    if (sessionStorage == null && Storage.isSessionStorageSupported()) {
      sessionStorage = new StorageExt(Storage.getSessionStorageIfSupported());
    }
    return localStorage;
  }

  private final StorageCache cache;
  private Set<StorageChangeEvent.Handler> handlers;

  private final Storage storage;
  private boolean traceObject;

  private StorageExt(Storage storage) {
    assert storage != null : "Storage can not be null, check your browser's HTML 5 support state.";
    this.storage = storage;
    this.cache = GWT.create(StorageCache.class);
  }

  public HandlerRegistration addStorageChangeHandler(final StorageChangeEvent.Handler handler) {
    ensureHandlerSet().add(handler);
    return new HandlerRegistration() {
      @Override
      public void removeHandler() {
        if (handlers != null && handler != null) {
          handlers.remove(handler);
          if (handlers.isEmpty()) {
            handlers = null;
          }
        }
      }
    };
  }

  public int checkUsedSpace(Storage storage) {
    int total = 0;
    String key = null;
    for (int i = 0; i < storage.getLength(); i++) {
      if (null != (key = storage.key(i))) {
        total += getDataLength(storage.getItem(key));
      }
    }
    return total / 1024;
  }

  public void clear() {
    storage.clear();
    cache.clear();
    fireEvent(StorageChangeEvent.ChangeType.CLEAR, null, null, null, null, null);
  }

  public <T> boolean containsKey(StorageKey<T> key) {
    return key != null && storage.getItem(key.name()) != null;
  }

  public <T> T get(StorageKey<T> key) throws SerializationException {
    T item = cache.get(key);
    if (item == null) {
      item = TYPE_SERIALIZER.deserialize(key.getClazz(), storage.getItem(key.name()));
      cache.put(key, item);
    }
    return item;
  }

  public void getKeyValueSize(final AsyncCallback<HashMap<String, Integer>> callback) {
    Scheduler.get().scheduleIncremental(new RepeatingCommand() {
      private int count = storage.getLength();
      private String key = null;
      private final HashMap<String, Integer> result = new HashMap<String, Integer>();

      @Override
      public boolean execute() {
        if (--count < 0) {
          callback.onSuccess(result);
          return false;
        }
        if (null != (key = storage.key(count))) {
          int length = getDataLength(storage.getItem(key));
          result.put(key, length);
        }
        return true;
      }
    });
  }

  public String getString(String key) {
    return storage.getItem(key);
  }

  public String key(int index) {
    return storage.key(index);
  }

  public <T> void put(StorageKey<T> key, T value) throws SerializationException,
      StorageQuotaExceededException {
    try {
      String data = TYPE_SERIALIZER.serialize(key.getClazz(), value);
      // Update store and cache
      String oldData = storage.getItem(key.name());
      storage.setItem(key.name(), data);
      T oldValue = cache.put(key, value);
      fireEvent(StorageChangeEvent.ChangeType.PUT, key, value, oldValue, data, oldData);
    } catch (JavaScriptException e) {
      String msg = e.getMessage();
      if (msg != null && msg.indexOf("QUOTA") != -1 && msg.indexOf("DOM") != -1) {
        throw new StorageQuotaExceededException(key, e);
      }
      throw e;
    }
  }

  public <T> void remove(StorageKey<T> key) {
    String data = storage.getItem(key.name());
    storage.removeItem(key.name());
    T value = cache.remove(key);
    fireEvent(StorageChangeEvent.ChangeType.REMOVE, key, null, value, null, data);
  }

  public void setEventLevel(boolean traceObject) {
    this.traceObject = traceObject;
  }

  public int size() {
    return storage.getLength();
  }

  private Set<StorageChangeEvent.Handler> ensureHandlerSet() {
    if (handlers == null) {
      handlers = new HashSet<StorageChangeEvent.Handler>();
    }
    return handlers;
  }

  private void fireEvent(StorageChangeEvent.ChangeType changeType, StorageKey<?> key, Object value,
      Object oldVal, String data, String oldData) {
    if (handlers != null) {
      Object oldValue = oldVal;
      if (traceObject && oldValue == null && oldData != null) {
        UncaughtExceptionHandler ueh = com.google.gwt.core.client.GWT.getUncaughtExceptionHandler();
        try {
          oldValue = TYPE_SERIALIZER.deserialize(key.getClazz(), data);
        } catch (SerializationException e) {
          if (ueh != null)
            ueh.onUncaughtException(e);
          oldValue = null;
        }
      }

      final StorageChangeEvent event =
          new StorageChangeEvent(changeType, key, value, oldValue, data, oldData);
      for (Iterator<StorageChangeEvent.Handler> it = handlers.iterator(); it.hasNext();) {
        it.next().onStorageChange(event);
      }
    }
  }

  private int getDataLength(String str) {
    return str == null ? 0 : str.length();
  }

}
