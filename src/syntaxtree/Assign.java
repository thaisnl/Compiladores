package syntaxtree;

import visitor.*;

public class Assign extends Statement {
	public Identifier id;
	public Exp ex;

	public Assign(Identifier i, Exp e) {
		id = i;
		ex = e;
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
