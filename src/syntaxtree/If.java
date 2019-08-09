package syntaxtree;

import visitor.*;

public class If extends Statement {
	public Exp ex;
	public Statement st1;
	public Statement st2;

	public If(Exp e, Statement s1, Statement s2) {
		ex = e;
		st1 = s1;
		st2 = s2;
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