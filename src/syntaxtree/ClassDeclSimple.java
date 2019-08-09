package syntaxtree;

import visitor.*;

public class ClassDeclSimple extends ClassDecl {
	public Identifier id;
	public VarDeclList vld;
	public MethodDeclList mld;

	public ClassDeclSimple(Identifier i, VarDeclList vl, MethodDeclList ml) {
		id = i;
		vld = vl;
		mld = ml;
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