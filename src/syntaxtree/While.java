package syntaxtree;

import visitor.*;

public class While extends Statement {
	public Exp ex;
	public Statement st;

	public While(Exp e, Statement s) {
		ex = e;
		st = s;
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