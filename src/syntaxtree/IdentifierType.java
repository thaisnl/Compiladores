package syntaxtree;

import visitor.*;

public class IdentifierType extends Type {
	public String str;

	public IdentifierType(String s) {
		str = s;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

	public Type accept(TypeVisitor v) {
		return v.visit(this);
	}
	
	public String getValue() {
		return str;
	}

	public String getTypeClass(){
		return "IdentifierType";
	}
	
	public Translate.Exp accept(IRVisitor v) {
		return v.visit(this);
	}
}
