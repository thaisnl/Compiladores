package syntaxtree;

import visitor.*;

public class MainClass {
	public Identifier id1;
	public Identifier id2;
	public Statement sd;

	public MainClass(Identifier i1, Identifier i2, Statement s) {
		id1 = i1;
		id2 = i2;
		sd = s;
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