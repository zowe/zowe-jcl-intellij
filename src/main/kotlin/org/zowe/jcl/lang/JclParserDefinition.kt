/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright IBA Group 2023
 */

package org.zowe.jcl.lang

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import org.zowe.jcl.lang.parser.JclParser
import org.zowe.jcl.lang.psi.JclFile
import org.zowe.jcl.lang.psi.JclTypes

class JclParserDefinition: ParserDefinition {

  companion object {
    val WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE)
    val COMMENTS = TokenSet.create(JclTypes.COMMENT)

    val FILE = IFileElementType(JclLanguage.INSTANCE)
  }


  override fun createLexer(project: Project?): Lexer = JclLexerAdapter()

  override fun getWhitespaceTokens(): TokenSet = WHITE_SPACES

  override fun createParser(project: Project?): PsiParser = JclParser()

  override fun getFileNodeType(): IFileElementType = FILE

  override fun getCommentTokens(): TokenSet = COMMENTS

  override fun getStringLiteralElements(): TokenSet = TokenSet.EMPTY

  override fun createElement(node: ASTNode?): PsiElement = JclTypes.Factory.createElement(node)

  override fun createFile(viewProvider: FileViewProvider): PsiFile = JclFile(viewProvider)

  override fun spaceExistenceTypeBetweenTokens(left: ASTNode?, right: ASTNode?): ParserDefinition.SpaceRequirements {
    return ParserDefinition.SpaceRequirements.MAY
  }
}
