package syntaxtree;

import visitor.*;

public class Formal {
	public Type t1;
	public Identifier id1;

	public Formal(Type t, Identifier i) {
		t1 = t;
		id1 = i;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

	public Type accept(TypeVisitor v) {
		return v.visit(this);
	}
	
	public Translate.Exp accept(IRVisitor v) {
		return v.visit(this);
	}

}