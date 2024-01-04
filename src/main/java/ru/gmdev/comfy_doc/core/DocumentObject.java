package ru.gmdev.comfy_doc.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import ru.gmdev.comfy_doc.core.wsExchange.DxAddress;
import ru.gmdev.comfy_doc.core.wsExchange.ExchangeManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class DocumentObject extends Document {

  private ExchangeManager exManager;

  @Autowired
  public DocumentObject(ExchangeManager exManager) {
    this.exManager = exManager;
  }

  @Override
  public String getMetaobjectname() {
    return null;
  }

  @Override
  public Integer getDocumentclassid() {
    return null;
  }

  @Override
  public Integer getDocstatusid() {
    return null;
  }

  @Override
  public Long getId() {
    return null;
  }

  @Override
  public void setMetaobjectname(String value) {

  }

  @Override
  public void setDocumentclassid(Integer value) {

  }

  @Override
  public void setParentMetaobjectname(String value) {

  }

  @Override
  public void setDocstatusid(Integer value) {

  }

  public final boolean isDocument() {
    return true;
  }

    public final void send(DxAddress recipient) {

    }
//  public final void send(DxAddress sender, DxAddress recipient) {
//    this.send((Integer)null, sender, recipient, (Short)null, false);
//  }

//  public int invoke_ex(Map<String, Object> params) {
//    try {
//      DxEnvObject env = this.invoke(dc, params);
//      switch(env.getEnvelopeState()) {
//        case SXO_ANSWER:
//          return 0;
//        case SXO_FIN:
//          return 1;
//        case SXO_SECONDARY_QUEUE:
//          return 2;
//        default:
//          return 3;
//      }
//    } catch (SendException var4) {
//      return 3;
//    }
//  }
//
//  private void checkDocSecurity(Context con, DocumentObject doc, String docEvent) {
//    if (!SecurityHelper.isAdminContext(con)) {
//      DocumentClassHelper helper = con.getApplication().getDocumentClassHelper();
//      Docevent event = helper.getDocEvent(doc, docEvent);
//      boolean chkUserAccess;
//      if (event != null) {
//        DocAccessLevel level = helper.getAccessLevel(con, doc, event);
//        if (level.accessLevel < 2) {
//          throw DocFlow.createAccessDeniedException(doc, docEvent);
//        }
//
//        chkUserAccess = level.checkUserAccess;
//      } else {
//        Integer statusId = doc.getDocstatusid();
//        if (statusId == 0) {
//          statusId = 1;
//        }
//
//        DocAccessLevel level = helper.getAccessLevelOnStatus(doc, statusId);
//        chkUserAccess = level.checkUserAccess;
//      }
//
//      if (chkUserAccess) {
//        doc.checkUserAccess(con, docEvent);
//      }
//
//    }
//  }
//
//  public final DataIterator<DigitalSignatureObject> getAttachments(Context con, Map<String, Object> userParams, Boolean loadAllFields) {
//    boolean caching = this.isUseTemplateCaching() && !loadAllFields;
//    if (caching && this.cachedAttachments != null) {
//      List<DigitalSignatureObject> dsList = (List)this.cachedAttachments.get(userParams);
//      if (dsList != null) {
//        return new IteratorList(dsList);
//      }
//    }
//
//    ObjectContainer<DigitalSignatureObject> dsContainer = this.getAttachments(con, DigitalSignatureObject.class, userParams, loadAllFields);
//    if (caching) {
//      if (this.cachedAttachments == null) {
//        this.cachedAttachments = new HashMap();
//      }
//
//      ArrayList list = new ArrayList();
//
//      try {
//        dsContainer.forEach(list::add);
//      } finally {
//        dsContainer.close();
//      }
//
//      this.cachedAttachments.put(userParams, list);
//      return new IteratorList(list);
//    } else {
//      return dsContainer;
//    }
//  }
}
