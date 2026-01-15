import { AstNode, AstNodeDescription, isAstNodeDescription } from "langium";
import { DefaultNodeKindProvider } from "langium/lsp";
import { CompletionItemKind, SymbolKind } from "vscode-languageserver-types";
import { isSetParameter} from "../language/generated/ast.js";

export class JclNodeKindProvider extends DefaultNodeKindProvider {
    override getSymbolKind(node: AstNode | AstNodeDescription): SymbolKind {
        const symbol = this.getNode(node);
        if (!symbol) {
            return SymbolKind.Null;
        }
        if (isSetParameter(symbol)) {
            return SymbolKind.Class;
        // } else if (isNamedElement(symbol)) {
        //     return SymbolKind.Variable;
        // } else if (isPackage(symbol)) {
        //     return SymbolKind.Namespace;
        // } else if (isLabelPrefix(symbol)) {
        //     return SymbolKind.Constant;
        } else {
            return SymbolKind.Variable;
        }
    }

    override getCompletionItemKind(node: AstNode | AstNodeDescription): CompletionItemKind {
        const symbol = this.getNode(node);
        if (!symbol) {
            return CompletionItemKind.Text;
        }
        if (isSetParameter(symbol)) {
            return CompletionItemKind.Interface;
        // } else if (isNamedElement(symbol)) {
        //     return CompletionItemKind.Variable;
        // } else if (isPackage(symbol)) {
        //     return CompletionItemKind.Module;
        // } else if (isLabelPrefix(symbol)) {
        //     return CompletionItemKind.Constant;
        }
        return CompletionItemKind.Variable;
    }

    private getNode(node: AstNode | AstNodeDescription): AstNode | undefined {
        if (isAstNodeDescription(node)) {
            return node.node;
        }
        return node;
    }
}