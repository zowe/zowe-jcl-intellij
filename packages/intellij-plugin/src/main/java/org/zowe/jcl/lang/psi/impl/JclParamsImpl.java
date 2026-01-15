// This is a generated file. Not intended for manual editing.
package org.zowe.jcl.lang.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static org.zowe.jcl.lang.psi.JclTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import org.zowe.jcl.lang.psi.*;

public class JclParamsImpl extends ASTWrapperPsiElement implements JclParams {

  public JclParamsImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull JclVisitor visitor) {
    visitor.visitParams(this);
  }

  @Override
  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof JclVisitor) accept((JclVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public JclLineContinuation getLineContinuation() {
    return findChildByClass(JclLineContinuation.class);
  }

  @Override
  @NotNull
  public List<JclParam> getParamList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, JclParam.class);
  }

}
