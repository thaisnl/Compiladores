package syntaxtree;

import visitor.*;

public class ArrayAssign extends Statement {
	public Identifier id;
	public Exp ex1;
	public Exp ex2;

	public ArrayAssign(Identifier i, Exp e1, Exp e2) {
		id = i;
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