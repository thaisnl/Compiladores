package syntaxtree;

import visitor.*;

public class Not extends Exp {
	public Exp ex;

	public Not(Exp e) {
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