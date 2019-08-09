package syntaxtree;

import visitor.*;

public class And extends Exp {
	public Exp ex1;
	public Exp ex2;

	public And(Exp e1, Exp e2) {
		ex1 = e1;
		ex2 = e2;
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