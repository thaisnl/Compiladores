package syntaxtree;

import visitor.*;

public abstract class ClassDecl {
	public abstract void accept(Visitor v);

	public abstract Type accept(TypeVisitor v);
	
	public abstract Translate.Exp accept(IRVisitor v);
}