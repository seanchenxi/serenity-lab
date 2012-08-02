package com.seanchenxi.gwt.uri.schema.data;

public enum MimeType {
  APPLICATION_JSON {
    @Override
    public String getValue() {
      return "application/json";
    }
  },
  APPLICATION_JAVASCRIPT {
    @Override
    public String getValue() {
      return "application/javascript";
    }
  },
  IMAGE_GIF {
    @Override
    public String getValue() {
      return "image/gif";
    }
  },
  IMAGE_JPEG {
    @Override
    public String getValue() {
      return "image/jpeg";
    }
  },
  IMAGE_PJPEG {
    @Override
    public String getValue() {
      return "image/pjpeg";
    }
  },
  IMAGE_PNG {
    @Override
    public String getValue() {
      return "image/png";
    }
  },
  IMAGE_SVG_XML {
    @Override
    public String getValue() {
      return "image/svg+xml";
    }
  },
  IMAGE_TIFF {
    @Override
    public String getValue() {
      return "image/tiff";
    }
  },
  TEXT_CSS {
    @Override
    public String getValue() {
      return "text/css";
    }
  },
  TEXT_CSV {
    @Override
    public String getValue() {
      return "text/csv";
    }
  },
  TEXT_HTML {
    @Override
    public String getValue() {
      return "text/html";
    }
  },
  TEXT_JAVASCRIPT {
    @Override
    public String getValue() {
      return "text/javascript";
    }
  },
  TEXT_PLAIN {
    @Override
    public String getValue() {
      return "text/plain";
    }
  },
  TEXT_VCARD {
    @Override
    public String getValue() {
      return "text/vcard";
    }
  },
  TEXT_XML {
    @Override
    public String getValue() {
      return "text/xml";
    }
  };

  public abstract String getValue();
}