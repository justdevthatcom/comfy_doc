package ru.gmdev.comfy_doc.fssp.nsi_dep.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gmdev.comfy_doc.fssp.nsi_dep.NsiDepartment;
import ru.gmdev.comfy_doc.fssp.nsi_dep.obj.NsiDeptCatalogRequest;

import java.math.BigInteger;

@Repository
public interface NsiDepartmentRequestRepository  extends JpaRepository<NsiDeptCatalogRequest, BigInteger> {
}
