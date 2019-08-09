package syntaxtree;

import visitor.*;

public class IntegerLiteral extends Exp {
	public int integer;

	public IntegerLiteral(int i) {
		integer = i;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}

	public Type accept(TypeVisitor v) {
		return v.visit(this);
	}
	
	public Integer getValue() {
		return integer;
	}
	
	public Translate.Exp accept(IRVisitor v) {
		return v.visit(this);
	}
}