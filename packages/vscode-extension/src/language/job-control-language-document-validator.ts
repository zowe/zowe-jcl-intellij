import { DefaultDocumentValidator, LexingDiagnostic} from "langium";
import { type ParseResult } from "langium";
import { type Diagnostic } from "vscode-languageserver-types";
import { ValidationOptions, toDiagnosticSeverity, toDiagnosticData } from "langium";

export class JclDocumentValidator extends DefaultDocumentValidator{
    protected override processLexingErrors(parseResult: ParseResult, diagnostics: Diagnostic[], _options: ValidationOptions): void {
        const lexerDiagnostics = [...parseResult.lexerErrors, ...parseResult.lexerReport?.diagnostics ?? []] as LexingDiagnostic[];
        for (const lexerDiagnostic of lexerDiagnostics){
            if (lexerDiagnostic.column){
                console.log(lexerDiagnostic.column);}
            const severity = lexerDiagnostic.severity ?? 'error';
            const diagnostic: Diagnostic = {
                severity: toDiagnosticSeverity(severity),
                range: {
                    start: {
                        line: lexerDiagnostic.line! - 1,
                        character: lexerDiagnostic.column! - 1
                    },
                    end: {
                        line: lexerDiagnostic.line! - 1,
                        character: lexerDiagnostic.column! + lexerDiagnostic.length - 1
                    }
                },
                message: lexerDiagnostic.message,
                data: toDiagnosticData(severity),
                source: this.getSource()
            };
            diagnostics.push(diagnostic);
        }
    }

}
 