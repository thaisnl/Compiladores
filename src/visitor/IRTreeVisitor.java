package visitor;

import syntaxtree.*;
import Frame.*;
import Mips.MipsFrame;
import Temp.Label;
import Translate.Exp;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import envs.*;
import javafx.util.Pair;

public class IRTreeVisitor implements IRVisitor {

	public Frame currFrame;
	public ClassTable currClass;
	public MethodTable currMethod;
	public Pair<Symbol, ListAux> varDeclAccessMethods;
	public MainTable mainTable;
	public Translate.Translate translate = new Translate.Translate();

	public IRTreeVisitor(MainTable symbolTable) {
		this.mainTable = symbolTable;
	}

	// MainClass m;
	// ClassDeclList cl;
	public Translate.Exp visit(Program n) {
		n.m.accept(this);
		for (int i = 0; i < n.cl.size(); i++) {
			n.cl.elementAt(i).accept(this);
		}
		return null;
	}

	// Identifier i1,i2;
	// Statement s;
	public Translate.Exp visit(MainClass n) {
		currClass = (ClassTable) mainTable.getClass(Symbol.symbol(n.id1.getValue()));
		currMethod = currClass.getMethod(Symbol.symbol("main"));
		varDeclAccessMethods = new Pair<>(Symbol.symbol(currMethod.getMethodName()), new ListAux());

		ArrayList<Boolean> escapes = new ArrayList<Boolean>();
		escapes.add(false);
		currFrame = new Mips.MipsFrame();
		currFrame = currFrame.newFrame(Symbol.symbol("main"), escapes);

		Tree.Stm body = new Tree.EXP(n.sd.accept(this).unEx());
		Tree.Exp retExp = new Tree.CONST(0);
		
		body = new Tree.MOVE( new Tree.TEMP(currFrame.RV()) , new Tree.ESEQ(body,retExp));

		translate.procEntryExit(body, currFrame);
		return null;
	}

	// Identifier i;
	// VarDeclList vl;
	// MethodDeclList ml;
	public Translate.Exp visit(ClassDeclSimple n) {
		currClass = mainTable.getClass(Symbol.symbol(n.id.getValue()));
		currMethod = null;
		for (int i = 0; i < n.vld.size(); i++) {
			n.vld.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.mld.size(); i++) {
			n.mld.elementAt(i).accept(this);
		}
		return null;
	}

	// Identifier i;
	// Identifier j;
	// VarDeclList vl;
	// MethodDeclList ml;
	public Translate.Exp visit(ClassDeclExtends n) {
		currClass = mainTable.getClass(Symbol.symbol(n.id1.getValue()));
		currMethod = null;
		for (int i = 0; i < n.vl1.size(); i++) {
			n.vl1.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.ml1.size(); i++) {
			n.ml1.elementAt(i).accept(this);
		}
		return null;
	}

	// Type t;
	// Identifier i;
	public Translate.Exp visit(VarDecl n) {
		if (currMethod != null) {
			Access a = null;
			a = currFrame.allocLocal(false);
			Aux varAccessPair = new Aux(n.i1.getValue(), a);
			varDeclAccessMethods.getValue().addAux(varAccessPair);
		}
		return null;
	}

	// Type t;
	// Identifier i;
	// FormalList fl;
	// VarDeclList vl;
	// StatementList sl;
	// Exp e;
	public Translate.Exp visit(MethodDecl n) {
		currMethod = currClass.getMethod(Symbol.symbol(n.id.getValue()));
		currFrame = new Mips.MipsFrame();		
		varDeclAccessMethods = new Pair<>(Symbol.symbol(currMethod.getMethodName()), new ListAux());

		ArrayList<Boolean> escapes = new ArrayList<Boolean>();

		for (int i = 0; i < n.fml.size(); i++) {
			escapes.add(false);
		}
		currFrame = currFrame.newFrame(Symbol.symbol(n.id.getValue()), escapes);

		for (int i = 0; i < n.vdl.size(); i++) {
			n.vdl.elementAt(i).accept(this);
		}
		Tree.Stm body = seqBodyMethod(n.stl, 0);
		Tree.Exp retExp = n.ex.accept(this).unEx();

		body = new Tree.MOVE(new Tree.TEMP(currFrame.RV()), new Tree.ESEQ(body, retExp));

		translate.procEntryExit(body, currFrame);

		return null;
	}

	public Tree.Stm seqBodyMethod(StatementList sl, int i) {
		if (sl.size() == 0) {
			return new Tree.EXP(new Tree.CONST(0));
		}

		if (i < sl.size() - 1) {
			return new Tree.SEQ(new Tree.EXP(sl.elementAt(i).accept(this).unEx()), seqBodyMethod(sl, i + 1));
		}

		return new Tree.EXP(sl.elementAt(i).accept(this).unEx());

	}

	// Type t;
	// Identifier i;
	public Translate.Exp visit(Formal n) {
		return null;
	}

	public Translate.Exp visit(IntArrayType n) {
		return null;
	}

	public Translate.Exp visit(BooleanType n) {
		return null;
	}

	public Translate.Exp visit(IntegerType n) {
		return null;
	}

	// String s;
	public Translate.Exp visit(IdentifierType n) {
		return null;
	}

	// StatementList sl;
	public Translate.Exp visit(Block n) {
		Tree.Stm stm = seqBodyMethod(n.stl, 0);
		return new Translate.Exp(new Tree.ESEQ(stm, new Tree.CONST(0)));
	}

	// Exp e;
	// Statement s1,s2;
	public Translate.Exp visit(If n) {
		Label if_ = new Label();
		Label else_ = new Label();
		Label fim = new Label();
		Tree.Exp condition = n.ex.accept(this).unEx();
		Translate.Exp label1 = n.st1.accept(this);
		Translate.Exp label2 = n.st2.accept(this);

		Tree.Exp cx = new Tree.ESEQ(
				new Tree.SEQ(new Tree.CJUMP(Tree.CJUMP.GT, condition, new Tree.CONST(0), if_, else_),
						new Tree.SEQ(
								new Tree.SEQ(new Tree.LABEL(if_),
										new Tree.SEQ(new Tree.EXP(label1.unEx()), new Tree.JUMP(fim))),
								new Tree.SEQ(new Tree.LABEL(else_),
										new Tree.SEQ(new Tree.EXP(label2.unEx()), new Tree.LABEL(fim))))),
				new Tree.CONST(0));

		return new Translate.Exp(cx);
	}

	// Exp e; -> condicao
	// Statement s; -> stm a ser executado se a cond é > 0
	public Translate.Exp visit(While n) {
		Tree.Exp condition = n.ex.accept(this).unEx();
		Translate.Exp stm = n.st.accept(this);
		Label statement = new Label();
		Label comeco = new Label();
		Label fim = new Label();

		Tree.Exp res = new Tree.ESEQ(
						new Tree.SEQ(new Tree.SEQ(new Tree.LABEL(comeco), new Tree.CJUMP(Tree.CJUMP.GT, condition, new Tree.CONST(0), statement, fim)), new Tree.SEQ(
						new Tree.SEQ(new Tree.LABEL(statement), new Tree.EXP(stm.unEx())),
						new Tree.SEQ(new Tree.JUMP(comeco), new Tree.LABEL(fim)))),
						new Tree.CONST(0));
		return new Translate.Exp(res);
	}

	// Exp e;
	public Translate.Exp visit(Print n) {
		Tree.Exp exp = n.ex.accept(this).unEx();
        List<Tree.Exp> expList = new ArrayList<>();
        expList.add(exp);
        expList.add(null);
        return new Translate.Exp(currFrame.externalCall("print", expList));
	}

	// Identifier i;
	// Exp e;
	// id = Exp;
	public Translate.Exp visit(Assign n) {
		//ainda em duvida na quetsao das variaveis de classe
		Tree.Exp var = null;
		if (currMethod != null) {
			Access a = varDeclAccessMethods.getValue().getAccess(n.id.getValue());
			if(a != null) {
				var = a.exp(new Tree.TEMP(currFrame.FP()));
			}else {
				//???
			}
		} else if (currClass != null) {
			//???
		}

		Tree.Exp res = new Tree.ESEQ(new Tree.MOVE(var, n.ex.accept(this).unEx()),
				new Tree.CONST(0));
		return new Translate.Exp(res);
	}

	// Identifier i;
	// Exp e1,e2;
	// i[e1] = e2;
	public Translate.Exp visit(ArrayAssign n) {
		Translate.Exp i = n.id.accept(this);
		Translate.Exp e1 = n.ex1.accept(this);
		Translate.Exp e2 = n.ex2.accept(this);
		
		//multiplica o e1 pelo wordsize e soma o endereço base a esse valor
		//dai move o valor do ex2 pra esse endereço
		//" o endereço base do array é o conteúdo de uma variável ponteiro,
		//então precisa do MEM pra fazer o fetch"
		return new Translate.Exp(new Tree.ESEQ(new Tree.MOVE(new Tree.MEM
				(new Tree.BINOP(Tree.BINOP.PLUS,i.unEx(),new Tree.BINOP(Tree.BINOP.MUL,e1.unEx(),
				new Tree.CONST(currFrame.wordSize())))), e2.unEx()), new Tree.CONST(0)));
		
		//atribuir zeros??
	}

	// Exp e1,e2;
	public Translate.Exp visit(And n) {
		Tree.Exp ex_1 = n.ex1.accept(this).unEx();
		Tree.Exp ex_2 = n.ex2.accept(this).unEx();
		return new Translate.Exp(new Tree.BINOP(Tree.BINOP.AND, ex_1, ex_2));

	}

	// Exp e1,e2;
	public Translate.Exp visit(LessThan n) {
		Tree.Exp ex_1 = n.ex1.accept(this).unEx();
		Tree.Exp ex_2 = n.ex2.accept(this).unEx();
        Tree.TEMP r = new Tree.TEMP(new Temp.Temp());
        Label t = new Temp.Label();
        Label f = new Temp.Label();
        Label end = new Temp.Label();          
        //move 1 ou 0 pro temp ai retorna o temp
        return new Translate.Exp(new Tree.ESEQ(                                
                        		new Tree.SEQ(new Tree.CJUMP(Tree.CJUMP.LT,ex_1,ex_2, t, f),                        
                                new Tree.SEQ(new Tree.SEQ(new Tree.LABEL(t),new Tree.SEQ(new Tree.MOVE(r, new Tree.CONST(1)),
                                new Tree.JUMP(end))),new Tree.SEQ(new Tree.LABEL(f),
                                new Tree.SEQ(new Tree.MOVE(r, new Tree.CONST(0)),
                                new Tree.SEQ(new Tree.JUMP(end),new Tree.LABEL(end)))))), r));  
	}

	// Exp e1,e2;
	public Translate.Exp visit(Plus n) {
		Tree.Exp ex_1 = n.ex1.accept(this).unEx();
		Tree.Exp ex_2 = n.ex2.accept(this).unEx();
		return new Translate.Exp(new Tree.BINOP(Tree.BINOP.PLUS, ex_1, ex_2));
	}

	// Exp e1,e2;
	public Translate.Exp visit(Minus n) {
		Tree.Exp ex_1 = n.ex1.accept(this).unEx();
		Tree.Exp ex_2 = n.ex2.accept(this).unEx();
		return new Translate.Exp(new Tree.BINOP(Tree.BINOP.MINUS, ex_1, ex_2));
	}

	// Exp e1,e2;
	public Translate.Exp visit(Times n) {
		Tree.Exp ex_1 = n.ex1.accept(this).unEx();
		Tree.Exp ex_2 = n.ex2.accept(this).unEx();
		return new Translate.Exp(new Tree.BINOP(Tree.BINOP.MUL, ex_1, ex_2));
	}

	// Exp e1,e2;
	// Exp [ exp ]
	public Translate.Exp visit(ArrayLookup n) {
		Translate.Exp e1 = n.ex1.accept(this);
		Translate.Exp e2 = n.ex2.accept(this);
		
		return new Translate.Exp(new Tree.MEM(new Tree.BINOP(
								Tree.BINOP.PLUS, e1.unEx(),
								new Tree.BINOP(Tree.BINOP.MUL,new Tree.CONST(currFrame.wordSize()),e2.unEx()))));
	}

	// Exp e;
	// Exp.length
	public Translate.Exp visit(ArrayLength n) {
		return new Translate.Exp(new Tree.MEM(n.ex1.accept(this).unEx()));
	}

	// Exp e;
	// Identifier i;
	// ExpList el;
	// Exp.id(ExpList)
	public Translate.Exp visit(Call n) {
		Tree.ExpList expList = null;
		for (int i = n.expl.size() -1; i >= 0 ; i--) {
			expList = new Tree.ExpList(n.expl.elementAt(i).accept(this).unEx(), expList);
		}
		expList = new Tree.ExpList(n.ex.accept(this).unEx(), expList); 
		return new Translate.Exp(new Tree.CALL(new Tree.NAME(new Label(n.id.getValue())), expList));
	}
	
	// int i;
	public Translate.Exp visit(IntegerLiteral n) {
		return new Translate.Exp(new Tree.CONST(n.getValue()));
	}

	public Translate.Exp visit(True n) {
		return new Translate.Exp(new Tree.CONST(0));
	}

	public Translate.Exp visit(False n) {
		return new Translate.Exp(new Tree.CONST(1));
	}

	// String s;
	public Translate.Exp visit(IdentifierExp n) {
		Tree.Exp var = null;
		if (currMethod != null) {
			Access a = varDeclAccessMethods.getValue().getAccess(n.getValue());
			if(a != null) {
				var = a.exp(new Tree.TEMP(currFrame.FP()));
			}else {
				//variavel da classe sendo acessada no metodo
				//??
			}
		}else if(currClass != null) {
			//??
		}

		return new Translate.Exp(var);
	}

	public Translate.Exp visit(This n) {
		//??
	}

	// Exp e;
	// new int [ Exp ]
	public Translate.Exp visit(NewArray n) {
		//soma mais um ao tamanho pois iremos guardar o tamanho do array junto com o array
		Tree.Exp tamanho = new Tree.BINOP(Tree.BINOP.MUL, new Tree.BINOP(Tree.BINOP.PLUS, n.ex.accept(this).unEx(), new Tree.CONST(1)),
							new Tree.CONST(currFrame.wordSize()));
		
		ArrayList<Tree.Exp> args = new ArrayList<Tree.Exp>();
		args.add(tamanho);
		args.add(null);
		//external call p inicializar array
		Tree.Exp retorno = currFrame.externalCall("alloc", args);
		return new Translate.Exp(retorno);
	}

	// Identifier i;
	public Translate.Exp visit(NewObject n) {
		String idClasse = n.id.getValue();
        ClassTable classe = mainTable.getClass(Symbol.symbol(idClasse));

        Tree.Exp tamanho = new Tree.BINOP(Tree.BINOP.MUL,new Tree.BINOP(Tree.BINOP.PLUS,
        		new Tree.CONST(classe.vTable.size()), new Tree.CONST(1)) , new Tree.CONST(currFrame.wordSize())) ;
        ArrayList<Tree.Exp> args = new ArrayList<Tree.Exp>();
		args.add(tamanho);
		args.add(null);
        Tree.Exp retorno = currFrame.externalCall("alloc", args);
        
        return new Translate.Exp(retorno);
	}

	// Exp e;
	public Translate.Exp visit(Not n) {
		return new Translate.Exp(new Tree.BINOP(Tree.BINOP.MINUS, new Tree.CONST(1), n.ex.accept(this).unEx()));
	}

	// String s;
	public Translate.Exp visit(Identifier n) {
		Tree.Exp var = null;
		if (currMethod != null) {
			Access a = varDeclAccessMethods.getValue().getAccess(n.getValue());
			if(a != null) {
				var = a.exp(new Tree.TEMP(currFrame.FP()));
			}else {
				//??
			}
		} else if (currClass != null) {
			//??
		}
		return new Translate.Exp(var);
	}
}
