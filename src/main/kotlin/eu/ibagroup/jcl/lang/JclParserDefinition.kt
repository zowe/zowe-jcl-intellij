package eu.ibagroup.jcl.lang

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
import eu.ibagroup.jcl.lang.parser.JclParser
import eu.ibagroup.jcl.lang.psi.JclFile
import eu.ibagroup.jcl.lang.psi.JclTypes

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
