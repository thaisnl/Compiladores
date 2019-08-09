package syntaxtree;

import visitor.*;

public class Block extends Statement {
	public StatementList stl;

	public Block(StatementList sl) {
		stl = sl;
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