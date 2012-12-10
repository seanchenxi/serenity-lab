package com.seanchenxi.gwt.storage.client;

public class StorageKey<T> {

  private final Class<T> clazz;
  private final String name;

  public StorageKey(String name, Class<T> clazz) {
    if(name == null || name.trim().length() < 1){
      throw new IllegalArgumentException("StorageKey's name can not be null or empty");
    }
    if(clazz == null){
      throw new IllegalArgumentException("StorageKey's class type can not be null");
    }
    this.name = name;
    this.clazz = clazz;
  }

  public Class<T> getClazz() {
    return clazz;
  }

  public String name() {
    return this.name;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }
  
  @SuppressWarnings("rawtypes")
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    StorageKey other = (StorageKey) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }
  
  @Override
  public String toString() {
    return name;
  }
  
}