package syntaxtree;

import visitor.*;

public class ClassDeclExtends extends ClassDecl {
	public Identifier id1;
	public Identifier id2;
	public VarDeclList vl1;
	public MethodDeclList ml1;

	public ClassDeclExtends(Identifier i, Identifier j, VarDeclList vl, MethodDeclList ml) {
		id1 = i;
		id2 = j;
		vl1 = vl;
		ml1 = ml;
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