
import { AstNode, DefaultReferences, FindReferencesOptions, ReferenceDescription, Stream } from "langium";
import { isSetStatement } from "../language/generated/ast.js";

export class JclReferences extends DefaultReferences {
    override findReferences(targetNode: AstNode, options: FindReferencesOptions): Stream<ReferenceDescription> {
        if (isSetStatement(targetNode.$container!.$container!)) {
            return this.findReferences(targetNode.$container!.$container!, options);
        } else {
            return super.findReferences(targetNode, options);
        }
    }
}