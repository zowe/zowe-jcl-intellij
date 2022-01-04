package eu.ibagroup.jcl.lang

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.patterns.PlatformPatterns
import com.intellij.util.ProcessingContext
import eu.ibagroup.jcl.lang.psi.JclTypes
import eu.ibagroup.jcl.lang.psi.firstParentByElementType

class JclCompletionContributor() : CompletionContributor() {
  init {
    extend(
      CompletionType.BASIC,
      PlatformPatterns.psiElement(JclTypes.OPERATOR),
      object: CompletionProvider<CompletionParameters>() {
        override fun addCompletions(
          parameters: CompletionParameters,
          context: ProcessingContext,
          result: CompletionResultSet
        ) {
          val possibleOperators = JclConstants.OPERATORS
            .map { LookupElementBuilder.create(it) }
          result.addAllElements(possibleOperators)
        }
      }
    )

    extend(
      CompletionType.BASIC,
      PlatformPatterns.psiElement(JclTypes.PARAM_KEY),
      object: CompletionProvider<CompletionParameters>() {
        override fun addCompletions(
          parameters: CompletionParameters,
          context: ProcessingContext,
          result: CompletionResultSet
        ) {
          val jclLineElementPair = parameters.position.firstParentByElementType(JclTypes.JCL_LINE)
          val instreamLineElementPair = parameters.position.firstParentByElementType(JclTypes.INSTREAM_LINE)

          val jclLineElement = jclLineElementPair.first ?: return
          val jclLineDistance = jclLineElementPair.second

          val instreamLineElement = instreamLineElementPair.first
          val instreamLineDistance = instreamLineElementPair.second

          var lineWithOperator = jclLineElement
          if (jclLineDistance > instreamLineDistance) {
            lineWithOperator = instreamLineElement ?: return
          }

          val operator = lineWithOperator.node.findChildByType(JclTypes.OPERATOR) ?: return
          if (operator.text.uppercase() == "DD") {
            val ddOperatorPossibleParams = JclConstants.DD_OPERATOR_PARAMS
              .map { LookupElementBuilder.create(it) }
            result.addAllElements(ddOperatorPossibleParams)
          }
          if (operator.text.uppercase() == "EXEC") {
            val execOperatorPossibleParams = JclConstants.EXEC_OPERATOR_PARAMS
              .map { LookupElementBuilder.create(it) }
            result.addAllElements(execOperatorPossibleParams)
          }
          if (operator.text.uppercase() == "JOB") {
            val jobOperatorPossibleParams = JclConstants.JOB_OPERATOR_PARAMS
              .map { LookupElementBuilder.create(it) }
            result.addAllElements(jobOperatorPossibleParams)
          }
        }
      }
    )
  }
}
