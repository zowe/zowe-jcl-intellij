import { AstNodeDescriptionProvider, LangiumCoreServices, MapScope, DefaultScopeProvider, ReferenceInfo, Scope, AstUtils,AstNodeDescription, Stream, stream, EMPTY_SCOPE} from "langium";
import { isRelationalExpression, isDsname,  isJclProgram, JclProgram,SetStatement, isCondExec, CondTest, ExecStatement} from "../language/generated/ast.js";
export class JclScopeProvider extends DefaultScopeProvider{
    private astNodeDescriptionProvider: AstNodeDescriptionProvider;
    constructor(services: LangiumCoreServices) {
        super(services)
        this.astNodeDescriptionProvider = services.workspace.AstNodeDescriptionProvider;
    }
    override getScope(context: ReferenceInfo): Scope {
        const scopes: Array<Stream<AstNodeDescription>> = [];
        const referenceType = this.reflection.getReferenceType(context);
        const precomputed = AstUtils.getDocument(context.container).precomputedScopes;
        let model: JclProgram = AstUtils.getContainerOfType(context.container, isJclProgram)!
        if (precomputed) {
            if (context.property==='set' && isDsname(context.container)){
                let setStatement : SetStatement[]= model?.set;              
                for (let set of setStatement){
                    let allDescriptions = precomputed.get(set.param);
                    let offset = allDescriptions[0].selectionSegment?.offset;
                    let setOffset=context.container.$cstNode?.offset;
                    if (setOffset && offset){
                        if (setOffset > offset){
                            scopes.push(stream(allDescriptions))  
                        }
                    }            
                }
            let result: Scope = this.getGlobalScope(referenceType, context);
            for (let i = scopes.length - 1; i >= 0; i--) {
                result = this.createScope(scopes[i], result);
            }
            return result;
            } else if (context.property==='ref' && (isRelationalExpression(context.container)||isCondExec(context.container))) {
                // let execStatement : ExecStatement[] = model?.exec;      
                // let conds : CondTest[] = model?.cond   
                // for (let exec of execStatement){
                //     let allDescriptions = precomputed.get(exec);
                //     scopes.push(stream(allDescriptions))
                // }
                // for (let cond of conds){
                //     for (let exec of cond.exec){
                //     let allDescriptions = precomputed.get(exec);
                //     scopes.push(stream(allDescriptions))
                //     }
                // }
                const execs = model.exec;
                execs.push(...model.cond && this.checkInnerConditions(model.cond))
                    
                const descriptions = execs.map(p => this.astNodeDescriptionProvider.createDescription(p, p.name));
                return new MapScope(descriptions);
            }else {
                return super.getScope(context)
            }
            
        } else {
            return EMPTY_SCOPE
        }
    } 

    private checkInnerConditions(conds: CondTest[]) : ExecStatement[] {
        let execs: ExecStatement[]=[]
        for (let cond of conds){
            cond.exec && execs.push(...cond.exec)
            execs.push(...cond.cond ? this.checkInnerConditions(cond.cond): cond.exec);
        }
        return execs   
    }
}