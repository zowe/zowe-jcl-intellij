// This is a generated file. Not intended for manual editing.
package org.zowe.jcl.lang.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface JclIfConstr extends PsiElement {

  @NotNull
  JclEndIfConstr getEndIfConstr();

  @NotNull
  JclIfBody getIfBody();

  @NotNull
  List<JclJclLine> getJclLineList();

  @Nullable
  JclOperatorFullName getOperatorFullName();

}
