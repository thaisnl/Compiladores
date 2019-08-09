package syntaxtree;
import visitor.*;

public class VarDecl{
 	public Type t1;
 	public Identifier i1;
  
  	public VarDecl(Type t, Identifier i){
    	t1 = t;
    	i1 = i;
  	}
  
  	 public void accept(Visitor v){
	 	v.visit(this);
	 }

	 public Type accept(TypeVisitor v){
	 	return v.visit(this);
	 }
	 
	 public Translate.Exp accept(IRVisitor v) {
			return v.visit(this);
	}
  
}