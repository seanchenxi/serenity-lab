package com.seanchenxi.gwt.storage.client;

@SuppressWarnings("serial")
public class StorageQuotaExceededException extends Exception {

  private StorageKey<?> key;

  public StorageQuotaExceededException(StorageKey<?> key, Throwable cause) {
    super(cause);
    this.key = key;
  }

  public StorageKey<?> getKey() {
    return key;
  }

}
