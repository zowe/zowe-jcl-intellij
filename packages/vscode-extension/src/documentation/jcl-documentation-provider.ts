import { AstNode, JSDocDocumentationProvider } from 'langium';
import { isSetParameter,isKeywordDDParameters, KeywordDDParameters, isExecStatement, isDDStatement, isJobStatement } from '../language/generated/ast.js';

export class JclDocumentationProvider extends JSDocDocumentationProvider {

    override getDocumentation(node: AstNode): string | undefined {
        if (isExecStatement(node)|| isDDStatement(node) || isJobStatement(node)) {
            //const declaredItem = node.$container;
            let text = '```\n' + `${node.name} ${node.oper}`
            text += '\n```';
            return text;
        }
        if (isSetParameter(node)) {
            const declaredItem = node.$container;
            let text = '```\n' + `SET ${declaredItem.$container.name} `;
            for (const attribute of declaredItem.param) {
                text += `${attribute.$cstNode?.text} `;
            }
            text += '\n```';
            return text;
        } else if (isKeywordDDParameters(node)) {
            return this.getProcedureHoverContent(node);
        }
        return '';
    }

    private getProcedureHoverContent(node: KeywordDDParameters): string | undefined {
        let text = '```\n';
        text += `DD ${node.$container.$container}`;
        if (node.name) {
            text += '(' + node.name + ') ';
        }
        text += '\n```';
        return text;
    }
}