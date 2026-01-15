// This is a generated file. Not intended for manual editing.
package org.zowe.jcl.lang.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface JclParam extends PsiElement {

  @Nullable
  JclKeyValueParam getKeyValueParam();

  @Nullable
  JclString getString();

  @Nullable
  JclTuple getTuple();

}
