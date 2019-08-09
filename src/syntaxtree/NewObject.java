package syntaxtree;

import visitor.*;

public class NewObject extends Exp {
	public Identifier id;

	public NewObject(Identifier i) {
		id = i;
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