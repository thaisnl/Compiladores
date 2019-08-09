package visitor;

import syntaxtree.*;

import java.util.LinkedHashMap;

import envs.*;

public class SymbolTableVisitor implements Visitor {

	public MainTable mainTable = new MainTable();
	ErrorMsg error = new ErrorMsg();
	ClassTable currClass= null;
	MethodTable currMethod = null;

	// MainClass m;
	// ClassDeclList cl;
	public void visit(Program n) {
		n.m.accept(this);
		for (int i = 0; i < n.cl.size(); i++) {
			System.out.println();
			n.cl.elementAt(i).accept(this);
		}
	}

	// Identifier i1,i2;
	// Statement s;
	public void visit(MainClass n) {
		n.id1.accept(this);
		
		// cria a tabela de classe pro main (mainclass é como se fosse uma classe seguida de um metodo
		ClassTable classTable = new ClassTable(n.id1.getValue());

		// checa se a classe já existe no maintable. se existir joga o erro, se não, entra no escopo
		if (!mainTable.addClass(Symbol.symbol(n.id1.getValue()), classTable)) {
			error.complain("A classe " + n.id1.getValue() + "já foi definida anteriormente");
		} else {
			currClass = classTable;
		}

		// cria uma methodtable para a mainclass. Parâmetro é o nome da classe e o retorno que como é void eu passo como null 
		MethodTable mTable = new MethodTable("main", null);

		// faço a checagem. se o método já existir na classe jogo o erro, se não, entro no escopo
		if (!currClass.addMethod(Symbol.symbol("main"), mTable)) {
			error.complain("O método main já foi definido");
		} else {
			currMethod = mTable;
		}

		n.id2.accept(this);
		// o id2 é um parâmetro no "método" do main. dai faço a checagem adicionando ele na
		// tabela de parametros do metodo atual. Passo o tipo como null pois é String [] que nao é uma produçao de type
		if (!currMethod.addParam(Symbol.symbol(n.id2.getValue()), null)) {
			error.complain("O parâmetro " + n.id2.getValue() + " já foi definido no método");
		}
		n.sd.accept(this);
	}

	// Identifier i;
	// VarDeclList vl;
	// MethodDeclList ml;
	public void visit(ClassDeclSimple n) {
		// aqui já temos que começar a construir a tabela de símbolos, pois temos uma declaração

		n.id.accept(this);

		// crio a tabela correspondente a classe
		ClassTable cTable = new ClassTable(n.id.getValue());

		// se ela já existir no mainTable jogo o erro, se não eu entro no escopo dela
		if (!mainTable.addClass(Symbol.symbol(n.id.getValue()), cTable)) {
			error.complain("A classe " + n.id.getValue() + "já foi definida anteriormente");
		} else {
			currClass = cTable;
			currMethod = null;
		}

		// chama o accept pro resto, e eles serão tratados em seus respectivos metodos visit
		for (int i = 0; i < n.vld.size(); i++) {
			n.vld.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.mld.size(); i++) {
			n.mld.elementAt(i).accept(this);
		}
	}

	// Identifier i;
	// Identifier j;
	// VarDeclList vl;
	// MethodDeclList ml;
	public void visit(ClassDeclExtends n) {
		n.id1.accept(this);
		n.id2.accept(this);

		ClassTable cTable = new ClassTable(n.id1.getValue(), n.id2.getValue());

		if (!mainTable.addClass(Symbol.symbol(n.id1.getValue()), cTable)) {
			error.complain("A classe " + n.id1.getValue() + "já foi definida anteriormente");
		} else {
			currClass = cTable;
			currMethod = null;
		}

		for (int i = 0; i < n.vl1.size(); i++) {
			n.vl1.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.ml1.size(); i++) {
			n.ml1.elementAt(i).accept(this);
		}
	}

	// Type t;
	// Identifier i;
	public void visit(VarDecl n) {
		n.i1.accept(this);
		n.t1.accept(this);

		// se o método atual for nulo eu to no escopo de uma classe entao tenho que add na tabela de var da classe
		if (currMethod == null) {
			if (!currClass.addVar(Symbol.symbol(n.i1.getValue()), n.t1)) {
				error.complain("A variável " + n.i1.getValue() + " já foi definida na classe atual");
			}
		}
		// se nao, to no escopo do metodo. adiciono na tabela de var do metodo
		if (currMethod != null) {
			if (!currMethod.addVar(Symbol.symbol(n.i1.getValue()), n.t1)) {
				error.complain("A variável " + n.i1.getValue() + " já foi definida neste método");
			}
		}
	}

	// Type t;
	// Identifier i;
	// FormalList fl;
	// VarDeclList vl;
	// StatementList sl;
	// Exp e;
	public void visit(MethodDecl n) {
		n.id.accept(this);
		n.ti.accept(this);

		// crio a tabela pro meu metodo
		MethodTable mTable = new MethodTable(n.id.getValue(), n.ti);
		
		// checo se ja foi definido nesta classe. se nao, entro no escopo dele
		if (!currClass.addMethod(Symbol.symbol(n.id.getValue()), mTable)) {
			error.complain("O método " + n.id.getValue() + "já foi definida anteriormente");
		} else {
			currMethod = mTable;
		}

		for (int i = 0; i < n.fml.size(); i++) {
			n.fml.elementAt(i).accept(this);

		}
		for (int i = 0; i < n.vdl.size(); i++) {
			n.vdl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.stl.size(); i++) {
			n.stl.elementAt(i).accept(this);
		}
		n.ex.accept(this);
	}

	// Type t;
	// Identifier i;
	public void visit(Formal n) {
		n.id1.accept(this);
		n.t1.accept(this);

		// aqui vou adicionar na tabela de parametros do metodo. faço a checagem de sempre
		if (!currMethod.addParam(Symbol.symbol(n.id1.getValue()), n.t1)) {
			error.complain("O parâmetro " + n.id1.getValue() + " já foi passado nesse método");
		}
	}

	public void visit(IntArrayType n) {
	}

	public void visit(BooleanType n) {
	}

	public void visit(IntegerType n) {
	}

	// String s;
	public void visit(IdentifierType n) {
	}

	// StatementList sl;
	public void visit(Block n) {
		for (int i = 0; i < n.stl.size(); i++) {
			n.stl.elementAt(i).accept(this);
		}
	}

	// Exp e;
	// Statement s1,s2;
	public void visit(If n) {
		n.ex.accept(this);
		n.st1.accept(this);
		n.st2.accept(this);
	}

	// Exp e;
	// Statement s;
	public void visit(While n) {
		n.ex.accept(this);
		n.st.accept(this);
	}

	// Exp e;
	public void visit(Print n) {
		n.ex.accept(this);
	}

	// Identifier i;
	// Exp e;
	public void visit(Assign n) {
		n.id.accept(this);
		n.ex.accept(this);
	}

	// Identifier i;
	// Exp e1,e2;
	public void visit(ArrayAssign n) {
		n.id.accept(this);
		n.ex1.accept(this);
		n.ex2.accept(this);
	}

	// Exp e1,e2;
	public void visit(And n) {
		n.ex1.accept(this);
		n.ex2.accept(this);
	}

	// Exp e1,e2;
	public void visit(LessThan n) {
		n.ex1.accept(this);
		n.ex2.accept(this);
	}

	// Exp e1,e2;
	public void visit(Plus n) {
		n.ex1.accept(this);
		n.ex2.accept(this);
	}

	// Exp e1,e2;
	public void visit(Minus n) {
		n.ex1.accept(this);
		n.ex2.accept(this);
	}

	// Exp e1,e2;
	public void visit(Times n) {
		n.ex1.accept(this);
		n.ex2.accept(this);
	}

	// Exp e1,e2;
	public void visit(ArrayLookup n) {
		n.ex1.accept(this);
		n.ex2.accept(this);
	}

	// Exp e;
	public void visit(ArrayLength n) {
		n.ex1.accept(this);
	}

	// Exp e;
	// Identifier i;
	// ExpList el;
	public void visit(Call n) {
		n.ex.accept(this);
		n.id.accept(this);
		for (int i = 0; i < n.expl.size(); i++) {
			n.expl.elementAt(i).accept(this);
		}
	}

	// int i;
	public void visit(IntegerLiteral n) {
	}

	public void visit(True n) {
	}

	public void visit(False n) {
	}

	// String s;
	public void visit(IdentifierExp n) {
	}

	public void visit(This n) {
	}

	// Exp e;
	public void visit(NewArray n) {
		n.ex.accept(this);
	}

	// Identifier i;
	public void visit(NewObject n) {
		n.id.accept(this);
	}

	// Exp e;
	public void visit(Not n) {
		n.ex.accept(this);
	}

	// String s;
	public void visit(Identifier n) {
	}
}
