import type { ValidationAcceptor, ValidationChecks } from 'langium';
import type { JobControlLanguageAstType, JobStatement, ExecStatement, DDStatement, DDParameters, ExecParameters, JobParameters } from './generated/ast.js';
import type { JobControlLanguageServices } from './job-control-language-module.js';
//import { JclLexer } from '../parser/jcl-parser.js';



/**
 * Register custom validation checks.
 */
export function registerValidationChecks(services: JobControlLanguageServices) {
    const registry = services.validation.ValidationRegistry;
    const validator = services.validation.JobControlLanguageValidator;
    const checks: ValidationChecks<JobControlLanguageAstType> = {
        JobStatement: validator.checkCardNameLength,
        ExecStatement: validator.checkCardNameLength,
        DDStatement: validator.checkCardNameLength, 
        JobParameters: validator.checkDuplicateParameterNames,
        DDParameters : validator.checkDuplicateParameterNames,
        ExecParameters: validator.checkDuplicateParameterNames
    };
    registry.register(checks, validator);
}

/**
 * Implementation of custom validations.
 */
export class JobControlLanguageValidator {

    // checkStartLine(card:JobStatement|ExecStatement|DDStatement, acceptor: ValidationAcceptor) :void{
    //     if (card){
    //         if (card.$document?.textDocument.positionAt(0).character !== 1){
    //             acceptor('warning','Jcl line should start with // from the 1st position',{node: card, property: 'start'})

    //         }
    //     }
    // }
    // checkCardNameLength(card: JobStatement|ExecStatement|DDStatement, accept: ValidationAcceptor): void {
    checkCardNameLength(card: JobStatement|DDStatement|ExecStatement, accept: ValidationAcceptor): void {    
        if (card.name) {
            if (card.name.length > 10) {
                accept('warning', 'Jcl card name should be max 8 char', {node: card, property: 'name' });
            }
        }
    }

    checkDuplicateParameterNames(card: JobParameters|DDParameters|ExecParameters, accept: ValidationAcceptor): void{
        if (card){
            const reported = new Set();
            card.keyword.forEach(d =>{
                if (reported.has(d.name)){
                    accept('warning','Duplicated parameters',{ node : d, property : 'name'})
                }
                reported.add(d.name)
            })
        }
    }

}
