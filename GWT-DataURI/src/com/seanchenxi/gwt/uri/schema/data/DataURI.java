package com.seanchenxi.gwt.uri.schema.data;

import com.google.gwt.http.client.URL;

public class DataURI {

  public static String create(MimeType mimeType, String data) {
    return create(mimeType, data, false);
  }

  public static String create(MimeType mimeType, String data, boolean isBase64) {
    return create(mimeType, Charset.UTF_8, data, isBase64);
  }
  
  public static String create(MimeType mimeType, Charset charset, String data) {
    return create(mimeType, charset, data, false);
  }

  public static String create(MimeType mimeType, Charset charset, String data, boolean isBase64) {
    return create(mimeType == null ? null : mimeType.getValue(), charset == null ? null : charset
        .getValue(), data, isBase64);
  }

  /**
   * return "data:[<MIME-type>][;charset=<encoding>][;base64],<data>" formatted, URL encoded string
   * 
   * @param mimeType
   * @param charset
   * @param data
   * @param isBase64
   * @return URL encoded string
   */
  public static String create(String mimeType, String charset, String data, boolean isBase64) {
    if (data == null || data.trim().isEmpty()) return null;
    StringBuilder sb = new StringBuilder("data:");
    if (mimeType != null && !mimeType.trim().isEmpty())
      sb.append(mimeType);
    if (charset != null && !charset.trim().isEmpty())
      sb.append(";charset=").append(charset);
    if (isBase64) sb.append(";base64");
    sb.append(",").append(data);
    return URL.encode(sb.toString());
  }

}
