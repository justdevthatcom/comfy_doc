package ru.gmdev.comfy_doc.fssp.nsi_dep.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gmdev.comfy_doc.core.wsExchange.DxAddress;
import ru.gmdev.comfy_doc.fssp.nsi_dep.obj.NsiDeptCatalogRequest;
import ru.gmdev.comfy_doc.fssp.nsi_dep.repo.NsiDepartmentRequestRepository;

@Slf4j
public class JobNsiDeptCatalogRequest {
  NsiDepartmentRequestRepository nsiRequestRepo;

  @Autowired
  public JobNsiDeptCatalogRequest(NsiDepartmentRequestRepository nsiRequestRepo) {
    this.nsiRequestRepo = nsiRequestRepo;
  }

  protected void run() {
    log.info("JobNsiDeptCatalogRequest starts. Sending request...");
    NsiDeptCatalogRequest request = new NsiDeptCatalogRequest();
    request.setCatalogKindCode(NsiDeptCatalogRequest.CATALOG_KIND_TYPE);
    nsiRequestRepo.save(request);

    request.send(new DxAddress());

  }

}
