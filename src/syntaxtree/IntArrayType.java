package syntaxtree;

import visitor.*;

public class IntArrayType extends Type {

	public void accept(Visitor v) {
		v.visit(this);
	}

	public Type accept(TypeVisitor v) {
		return v.visit(this);
	}

	public String getTypeClass(){
		return "IntArrayType";
	}
	
	public Translate.Exp accept(IRVisitor v) {
		return v.visit(this);
	}
}