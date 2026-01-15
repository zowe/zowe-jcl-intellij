import { DefaultLexer, LexerResult, LexingDiagnostic} from 'langium'
import type {IToken, ILexingError} from 'chevrotain'


const NEWLINE = '\n'.charCodeAt(0);

export class JclLexer extends DefaultLexer {

    override tokenize(text: string): LexerResult {
        const lines = this.splitLines(text);
        let inlineDDLine: number = 0;

        const adjustedLines = (lines.map(line=> {
            console.log(line);
            const inlineDD = line.match(/\/\/.*DD\s+(\*|DATA)\s*$/);
            if (inlineDDLine === 0) {          
                if (inlineDD) {
                    inlineDDLine = 1;
                }
                return line;
            }
            else if (inlineDDLine === 1 && line.match(/^\/\//)) {
                inlineDDLine = 0;
                return line;
            }
            else{
                let eol = '';
                if (line.endsWith('\r\n')) {
                    eol = '\r\n';
                } else if (line.endsWith('\n')) {
                    eol = '\n';
                }

                // let newLine = '%'.repeat(72-eol.length)+eol;
                let newLine = '%'.repeat(line.length-3)+eol;
                return newLine;
                
            };
        }));
        const adjustedText = adjustedLines.join('');
        const chevrotainResult = this.chevrotainLexer.tokenize(adjustedText);
        const tokens: IToken[] = chevrotainResult.tokens;
        const re = new RegExp("\/\/");
        for (const token of tokens){
            if (re.test(token.image) && token.startColumn!==1){
                const error: ILexingError = {
                    offset: token.startOffset,
                    line: token.startLine,
                    column : token.startColumn,
                    length: 2,
                    message: `wrong start posittion ${token.tokenType.categoryMatches}`
                }
                chevrotainResult.errors.push(error)
            }
            if (token.startColumn && token.endColumn){
                if (token.endColumn>72){
                    const error: ILexingError = {
                        offset: token.startOffset,
                        line: token.startLine,
                        column: 72,
                        length:token.endColumn-71,
                        message: 'jcl should be betweeen columns 1 and 72'
                    }
                    chevrotainResult.errors.push(error);

                }
            }
        }
        if (this.tokenBuilder.flushLexingReport){
            const report : LexingDiagnostic[]= this.tokenBuilder.flushLexingReport(adjustedText).diagnostics;
            for (const re of report){
                re.column
            }
        }
    
        return {
            tokens: chevrotainResult.tokens,
            errors: chevrotainResult.errors,
            hidden: chevrotainResult.groups.hidden ?? [],
            report: this.tokenBuilder.flushLexingReport?.(adjustedText)
        };
    }
   
    private splitLines(text: string): string[] {
        const lines: string[] = [];
        for (let i = 0; i < text.length; i++) {
            const start = i;
            while (i < text.length && text.charCodeAt(i) !== NEWLINE) {
                i++;
            }
            lines.push(text.substring(start, i + 1));
        }
        return lines;
    }

    // private adjustLine(line: string): string {
    //     console.log(line)
    //     let eol = '';
    //     if (line.endsWith('\r\n')) {
    //         eol = '\r\n';
    //     } else if (line.endsWith('\n')) {
    //         eol = '\n';
    //     }
    //     const prefixLength = 0;
    //     const lineLength = line.length - eol.length;
    //     if (lineLength < prefixLength) {
    //         return ' '.repeat(lineLength) + eol;
     
    //     }
    //     const lineEnd = 71;
    //     const prefix = ' '.repeat(prefixLength);
    //     let postfix = '';
    //     if (lineLength > lineEnd) {
    //         postfix = ' '.repeat(lineLength - lineEnd);
    //     }
    //     return prefix + line.substring(prefixLength, Math.min(lineEnd, lineLength)) + postfix + eol;
    // }

}

