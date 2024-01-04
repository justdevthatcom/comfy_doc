package ru.gmdev.comfy_doc.fssp.nsi_dep.obj;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.gmdev.comfy_doc.core.Document;
import ru.gmdev.comfy_doc.core.DocumentObject;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
public class NsiDeptCatalogRequest extends DocumentObject {
  public static String CATALOG_KIND_TYPE = "Departments";

  private String catalogKindCode;
}
