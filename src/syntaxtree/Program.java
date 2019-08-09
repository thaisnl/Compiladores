package syntaxtree;

import visitor.*;

public class Program {
	public MainClass m;
	public ClassDeclList cl;

	public Program(MainClass an, ClassDeclList acl) {
		m = an;
		cl = acl;
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