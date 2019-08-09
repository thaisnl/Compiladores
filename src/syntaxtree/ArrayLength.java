package syntaxtree;

import visitor.*;

public class ArrayLength extends Exp {
	public Exp ex1;

	public ArrayLength(Exp e) {
		ex1 = e;
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