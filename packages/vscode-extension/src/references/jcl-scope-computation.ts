import { AstNode, AstNodeDescription, AstUtils, DefaultScopeComputation, LangiumDocument, PrecomputedScopes } from "langium";
import { CancellationToken } from "vscode-languageserver";

export class JclScopeComputation extends DefaultScopeComputation {

    override async computeExports(document: LangiumDocument, cancelToken = CancellationToken.None): Promise<AstNodeDescription[]> {
        return this.computeExportsForNode(document.parseResult.value, document, AstUtils.streamAllContents, cancelToken);
    }

    protected override processNode(node: AstNode, document: LangiumDocument, scopes: PrecomputedScopes): void {
        const container = AstUtils.findRootNode(node);
        if (container) {
            const name = this.nameProvider.getName(node);
            if (name) {
                scopes.add(container, this.descriptions.createDescription(node, name, document));
            }
        }
    }
}