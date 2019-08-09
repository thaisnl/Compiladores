package syntaxtree;

import visitor.*;

public class Call extends Exp {
	public Exp ex;
	public Identifier id;
	public ExpList expl;

	public Call(Exp e, Identifier i, ExpList el) {
		ex = e;
		id = i;
		expl = el;
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