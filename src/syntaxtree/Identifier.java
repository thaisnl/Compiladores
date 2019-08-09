package syntaxtree;

import visitor.*;

public class Identifier {
	public String str;

	public Identifier(String s) {
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