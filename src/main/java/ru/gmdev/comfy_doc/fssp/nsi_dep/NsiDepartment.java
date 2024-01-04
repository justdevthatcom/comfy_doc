package ru.gmdev.comfy_doc.fssp.nsi_dep;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.gmdev.comfy_doc.core.DocumentObject;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class NsiDepartment extends DocumentObject {
}
