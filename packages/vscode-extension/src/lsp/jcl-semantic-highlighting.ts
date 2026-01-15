import { AstNode } from 'langium';
import { AbstractSemanticTokenProvider, SemanticTokenAcceptor } from 'langium/lsp';
import { isSetStatement, isInlineDDParameters, isSetParameter, isDDParameters, isDDStatement, isJobStatement, isExecStatement, isContinueDDStatement, isIfStatement, isElseStatement, isEndifStatement } from '../language/generated/ast.js';
import { SemanticTokenTypes } from 'vscode-languageserver';

export class JclSemanticTokenProvider extends AbstractSemanticTokenProvider {
    protected override highlightElement(node: AstNode, acceptor: SemanticTokenAcceptor): void | undefined | 'prune' {
        if (isSetStatement(node)|| isJobStatement(node) || isExecStatement(node) ||isDDStatement(node) || isIfStatement(node) || isElseStatement(node) || isEndifStatement(node)) {
            acceptor({
                node,
                property: 'name',
                type: SemanticTokenTypes.class
            });
            acceptor({
                node,
                property: 'oper',
                type: SemanticTokenTypes.macro
            });    
            acceptor({
                node,
                property: 'comment',
                type: SemanticTokenTypes.comment
            });      
        } else if (isInlineDDParameters(node)) {
            acceptor({
                node,
                property: 'value',
                type: SemanticTokenTypes.variable
            });
        } else if (isDDParameters(node) || isSetParameter(node)) {
            acceptor({
                node,
                property: 'name',
                type: SemanticTokenTypes.class
            });
        } else if (isContinueDDStatement(node)) {
            acceptor({
                node,
                property: 'oper',
                type: SemanticTokenTypes.macro
            });
            acceptor({
                node,
                property: 'comment',
                type: SemanticTokenTypes.comment
            }); 
        // } else if (isProcedureCall(node)) {
        //     acceptor({
        //         node,
        //         property: 'procedure',
        //         type: SemanticTokenTypes.function
        //     });
        // } else if (isLabelPrefix(node)) {
        //     const container = node.$container;
        //     acceptor({
        //         node: node,
        //         property: 'name',
        //         type: isProcedureStatement(container) ? SemanticTokenTypes.function : SemanticTokenTypes.variable
        //     });
        // } else if (isNumberLiteral(node)) {
        //     acceptor({
        //         node,
        //         property: 'value',
        //         type: SemanticTokenTypes.number
        //     });
        // } else if (isStringLiteral(node)) {
        //     acceptor({
        //         node,
        //         property: 'value',
        //         type: SemanticTokenTypes.string
        //     });
        // } else if (isLiteral(node)) {
        //     acceptor({
        //         node,
        //         property: 'multiplier',
        //         type: SemanticTokenTypes.number
        //     });
        }
    }

}