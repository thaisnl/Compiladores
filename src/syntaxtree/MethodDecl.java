package syntaxtree;

import visitor.*;

public class MethodDecl {

	public Type ti;
	public Identifier id;
	public FormalList fml;
	public VarDeclList vdl;
	public StatementList stl;
	public Exp ex;

	public MethodDecl(Type t, Identifier i, FormalList fl, VarDeclList vl, StatementList sl, Exp e) {
		ti = t;
		id = i;
		fml = fl;
		vdl = vl;
		stl = sl;
		ex = e;
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