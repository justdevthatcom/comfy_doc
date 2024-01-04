package ru.gmdev.comfy_doc.core;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public interface DocumentIntf {


  /**
   * Статус. Type: D_DOCSTATUS. Subsystem: NCORE. Not null.
   */
  public static final String ATTR_DOCSTATUSID = "DOCSTATUSID";
  /**
   * Класс документа. Type: D_DOCUMENTCLASS. Subsystem: NCORE. Not null.
   */
  public static final String ATTR_DOCUMENTCLASSID = "DOCUMENTCLASSID";
  /**
   * Идентификатор документа. Type: D_ID. Subsystem: NCORE. Not null.
   */
  public static final String ATTR_ID = "ID";
  /**
   * Имя метаобъекта. Type: D_METANAME. Subsystem: NCORE. Not null.
   */
  public static final String ATTR_METAOBJECTNAME = "METAOBJECTNAME";

  // Field getters  -----------------------------------------------------------
  /**
   * Имя метаобъекта
   */
  @NotNull
  public String getMetaobjectname();
  /**
   * Класс документа
   */
  @NotNull
  public Integer getDocumentclassid();
  /**
   * Статус
   */
  @NotNull
  public Integer getDocstatusid();
  /**
   * Идентификатор документа
   */
  @NotNull
  public Long getId();


  // Field setters  -----------------------------------------------------------
  /**
   * Имя метаобъекта
   */
  public void setMetaobjectname(@NotNull String value);
  /**
   * Класс документа
   */
  public void setDocumentclassid(@NotNull Integer value);
  /**
   * Тип родительского документа
   */
  public void setParentMetaobjectname(@Nullable String value);
  /**
   * Статус
   */
  public void setDocstatusid(@NotNull Integer value);

}
