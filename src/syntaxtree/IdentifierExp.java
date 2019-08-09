package syntaxtree;

import visitor.*;

public class IdentifierExp extends Exp {
	public String str;

	public IdentifierExp(String s) {
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
	
	public Translate.Exp accept(IRVisitor v) {
		return v.visit(this);
	}
}