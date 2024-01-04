package ru.gmdev.comfy_doc.core.wsExchange;

import lombok.Builder;
import lombok.Data;
import ru.gmdev.comfy_doc.utils.CompareUtil;

@Data
public class DxAddress implements Comparable<DxAddress> {
  private final String organization;
  private final String department;
  private final String protocol;

  public DxAddress() {
    this.organization = null;
    this.department = null;
    this.protocol = null;
  }

  public boolean isEmpty() {
    return (this.organization == null || this.organization.isEmpty()) && (this.department == null || this.department.isEmpty()) && (this.protocol == null || this.protocol.isEmpty());
  }

  public boolean isValid() {
    return this.organization != null && !this.organization.isEmpty() && (this.department == null || !this.department.isEmpty()) && this.protocol != null && !this.protocol.isEmpty();
  }

  public boolean equals(Object obj) {
    return obj instanceof DxAddress && equals(this, (DxAddress) obj);
  }

  public static boolean equals(DxAddress a, DxAddress b) {
    return CompareUtil.equals(a.organization, b.organization) && CompareUtil.equals(a.department, b.department) && CompareUtil.equals(a.protocol, b.protocol);
  }

  public int hashCode() {
    return (this.organization != null ? this.organization.hashCode() : 0) ^ (this.department != null ? this.department.hashCode() : 0) ^ (this.protocol != null ? this.protocol.hashCode() : 0);
  }

  public String toString() {
    return (this.protocol == null ? "(нет)" : this.protocol) + ":" + (this.organization == null ? "(нет)" : this.organization) + (this.department == null ? "" : "/" + this.department);
  }

  public int compareTo(DxAddress o) {
    int res;
    return (res = CompareUtil.compare(this.organization, o.organization)) != 0 ? res : ((res = CompareUtil.compare(this.department, o.department)) != 0 ? res : CompareUtil.compare(this.protocol, o.protocol));
  }
}
