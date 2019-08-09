// Segunda fase do type-checker, checa se o tipo da variável é correto
// Retorna o tipo da expressão, se a expressão não estiver com os tipos corretos, retorna uma mensagem de erro

package visitor;

import syntaxtree.*;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import envs.*;

public class TypeDepthFirstVisitor implements TypeVisitor{

	public ErrorMsg error = new ErrorMsg();
	public ClassTable currClass;
	public MethodTable currMethod;
	public MainTable mainTable;
	
	public TypeDepthFirstVisitor(MainTable mt) {
		this.mainTable = mt;
	}

	// MainClass m;
	// ClassDeclList cl;
	public Type visit(Program n){
		n.m.accept(this);
		for (int i = 0; i < n.cl.size(); i++) {
			n.cl.elementAt(i).accept(this);
		}
		return null;
	}

	// Identifier i1,i2;
	// Statement s;
	public Type visit(MainClass n){
		n.id1.accept(this);
		currClass = mainTable.getClass(Symbol.symbol(n.id1.getValue()));
		n.id2.accept(this);
		currMethod = currClass.getMethod(Symbol.symbol(n.id2.getValue()));
		n.sd.accept(this);
		return null;
	}

	// Identifier i;
	// VarDeclList vl;
	// MethodDeclList ml;
	public Type visit(ClassDeclSimple n){
		n.id.accept(this);
		currClass = mainTable.getClass(Symbol.symbol(n.id.getValue()));
		currMethod = null;
		for (int i = 0; i < n.vld.size(); i++ ){
			n.vld.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.mld.size(); i++ ){
			n.mld.elementAt(i).accept(this);
		}
		return null;

	}

	// Identifier i;
	// Identifier j;
	// VarDeclList vl;
	// MethodDeclList ml;
	public Type visit(ClassDeclExtends n){

		n.id1.accept(this);
		currClass = mainTable.getClass(Symbol.symbol(n.id1.getValue()));
		n.id2.accept(this);
		currMethod = null;
		for (int i = 0; i < n.vl1.size(); i++ ){
			n.vl1.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.ml1.size(); i++ ){
			n.ml1.elementAt(i).accept(this);
		}
		return null;

	}
	
	// Type t;
	// Identifier i;
	public Type visit(VarDecl n){

		n.t1.accept(this);
		n.i1.accept(this);
		return null;

	}

	// Type t;
	// Identifier i;
	// FormalList fl;
	// VarDeclList vl;
	// StatementList sl;
	// Exp e;
	public Type visit(MethodDecl n){

		n.ti.accept(this);
		n.id.accept(this);
		currMethod = currClass.getMethod(Symbol.symbol(n.id.getValue()));
		for (int i = 0; i < n.fml.size(); i++ ){
			n.fml.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.vdl.size(); i++ ){
			n.vdl.elementAt(i).accept(this);
		}
		for (int i = 0; i < n.stl.size(); i++ ){
			n.stl.elementAt(i).accept(this);
		}
		Type retorno = n.ex.accept(this); // O tipo que expressão retornou
		if (!retorno.getTypeClass().equals(n.ti.getTypeClass())){
			error.complain("Tipo de retorno da expressão não é compativel com o retorno do método");
		}
		return null;
	}

	// Type t;
	// Identifier i;
	public Type visit(Formal n){

		n.id1.accept(this);
		n.t1.accept(this);
		return null;

	}
	public Type visit(IntArrayType n){

		return new IntArrayType();

	}

	public Type visit(BooleanType n){

		return new BooleanType();

	}

	public Type visit(IntegerType n){

		return new IntegerType();

	}

	// String s;
	public Type visit(IdentifierType n) {

		return n;

	}

	// StatementList sl;
	public Type visit(Block n) {

		for ( int i = 0; i < n.stl.size(); i++ ) {
			n.stl.elementAt(i).accept(this);
		}
		return null;

	}

	// Exp e;
	// Statement s1,s2;
	public Type visit(If n){

		if (! (n.ex.accept(this) instanceof BooleanType)){
			error.complain("Declaração da condição do If deve ser do tipo Boolean");
		}
		n.st1.accept(this);
		n.st2.accept(this);
		return null;

	}

	// Exp e;
	// Statement s;
	public Type visit(While n){

		if (! (n.ex.accept(this) instanceof BooleanType)){
			error.complain("Declaração da condição do While deve ser do tipo Boolean");
		}
		n.st.accept(this);
		return null;
	}

	// Exp e;
	public Type visit(Print n){

		n.ex.accept(this);
		return null;

	}
	
	// Identifier id;
	// Exp ex;
	public Type visit(Assign n) {		
			Type tipo;
			Type exp = n.ex.accept(this);
			//id é uma variável de um metodo
			if ((currMethod != null) ){				
				if (currMethod.getVar(Symbol.symbol(n.id.getValue()))!=null) {
					tipo = currMethod.getVar(Symbol.symbol(n.id.getValue()));					
					if (exp == null || ! tipo.getTypeClass().equals(exp.getTypeClass())) {
						error.complain("Os tipos da atribuição não são compativeis");
					}					
				}				
				if (currMethod.getParam(Symbol.symbol(n.id.getValue()))!=null) {
					tipo = currMethod.getParam(Symbol.symbol(n.id.getValue()));					
					if (exp == null || ! tipo.getTypeClass().equals(exp.getTypeClass())) {
						error.complain("Os tipos da atribuição não são compativeis");
					}					
				}				
			}
			//id é uma variável de uma classe
			else if ((currClass != null) && currClass.getVar(Symbol.symbol(n.id.getValue()))!=null) {
				tipo = currClass.getVar(Symbol.symbol(n.id.getValue()));					
				if (exp == null || ! tipo.getTypeClass().equals(exp.getTypeClass())) {
					error.complain("Os tipos da atribuição não são compativeis ");
				}				
			}
			//id é uma classe
			else if (mainTable.getClass(Symbol.symbol(n.id.getValue())) != null) {
				tipo = new IdentifierType(n.id.getValue());
				IdentifierType tipoc = (IdentifierType) tipo;
				ClassTable nomeClasse = mainTable.getClass(Symbol.symbol( tipoc.getValue()));
				if (nomeClasse == null || !nomeClasse.getClassName().equals(exp.getTypeClass()))
					error.complain("Os tipos da atribuição não são compativeis");				
			}			
			return null;
	}
	//	Identifier id;
	// Exp ex1;
	// Exp ex2;
	public Type visit(ArrayAssign n) {
		Type tipo = null;		
		
		if ((currMethod != null) ){				
			if (currMethod.getVar(Symbol.symbol(n.id.getValue()))!=null) {
				tipo = currMethod.getVar(Symbol.symbol(n.id.getValue()));					
			}				
			if (currMethod.getParam(Symbol.symbol(n.id.getValue()))!=null) {
				tipo = currMethod.getParam(Symbol.symbol(n.id.getValue()));					
			}				
		}			
		if ((currClass != null) && currClass.getVar(Symbol.symbol(n.id.getValue()))!=null) {
			tipo = currClass.getVar(Symbol.symbol(n.id.getValue()));					
		}
		if (mainTable.getClass(Symbol.symbol(n.id.getValue())) != null) {
			tipo = new IdentifierType(n.id.getValue());							
		}
		
		if(tipo == null) { // 
			error.complain("O array não foi declarado");
		}
		
		//if( !(tipo instanceof IntegerType) ) {
		//	error.complain("O array precisa ser inteiro");
		//}
		
		if (! (n.ex1.accept(this) instanceof IntegerType)) {
			error.complain("A chave do array precisa ser inteiro");
		}
		
		if (! (n.ex2.accept(this) instanceof IntegerType)) {
			error.complain("O lado direito da atribuição precisa ser inteiro");
		}
		
		return null;
	}

	//Exp e1,e2;
	public Type visit(And n){

		if(! (n.ex1.accept(this) instanceof BooleanType) ){
			error.complain ("Lado esquerdo do operador && deve ser do tipo Boolean");
		}
		if(! (n.ex2.accept(this) instanceof BooleanType) ){
			error.complain ("Lado direito do operador && deve ser do tipo Boolean");
		}
		return new BooleanType();

	}

	//Exp e1,e2;
	public Type visit(LessThan n){

		if(! (n.ex1.accept(this) instanceof IntegerType) ){
			error.complain ("Lado esquerdo do operador < deve ser do tipo Integer");
		}
		if(! (n.ex2.accept(this) instanceof IntegerType) ){
			error.complain ("Lado direito do operador < deve ser do tipo Integer");
		}
		return new BooleanType();

	}

	//Exp e1,e2;
	public Type visit(Plus n){

		if(! (n.ex1.accept(this) instanceof IntegerType) ){
			error.complain ("Lado esquerdo do operador + deve ser do tipo Integer");
		}
		if(! (n.ex2.accept(this) instanceof IntegerType) ){
			error.complain ("Lado direito do operador + deve ser do tipo Integer");
		}
		return new IntegerType();

	}

	//Exp e1,e2;
	public Type visit(Minus n){

		if(! (n.ex1.accept(this) instanceof IntegerType) ){
			error.complain ("Lado esquerdo do operador - deve ser do tipo Integer");
		}
		if(! (n.ex2.accept(this) instanceof IntegerType) ){
			error.complain ("Lado direito do operador - deve ser do tipo Integer");
		}
		return new IntegerType();

	}

	//Exp e1,e2;
	public Type visit(Times n){

		if(! (n.ex1.accept(this) instanceof IntegerType) ){
			error.complain ("Lado esquerdo do operador * deve ser do tipo Integer");
		}
		if(! (n.ex2.accept(this) instanceof IntegerType) ){
			error.complain ("Lado direito do operador * deve ser do tipo Integer");
		}
		return new IntegerType();

	}


	// Exp e1,e2;
	public Type visit(ArrayLookup n) {

		if (! (n.ex1.accept(this) instanceof IntArrayType)){
			error.complain("O operador de subscript deve ser usado com um array");
		}
		if (! (n.ex2.accept(this) instanceof IntegerType)){
			error.complain("Iterador do array deve ser Integer");
		}
		return new IntegerType();

	}

	// Exp e;
	public Type visit(ArrayLength n) {

		if (! (n.ex1.accept(this) instanceof IntArrayType)){
			error.complain("O length deve ser utilizado com um array");
		}
		return new IntegerType();

	}

	public Type visit(IntegerLiteral n){

		return new IntegerType();

	}

	public Type visit(True n){

		return new BooleanType();

	}

	public Type visit(False n){

		return new BooleanType();

	}

	public Type visit(This n){
		if (currClass == null){ 
			error.complain("Classe não encontrada");
		}
		return new IdentifierType(currClass.getClassName());
	}

	// Exp e;
	public Type visit(NewArray n){
		if (! (n.ex.accept(this) instanceof IntegerType)){
			error.complain("Array deve ser Integer");	
		}
		return new IntegerType();
	}

	//Identifier i
	public Type visit(NewObject n){
		currClass = mainTable.getClass(Symbol.symbol(n.id.getValue()));
		if (currClass == null){
			error.complain("Classe usada no objeto não existe");
		}
		return new IdentifierType(currClass.getClassName());

	}
	// Exp ex;
	// Identifier id;
	// ExpList expl;
	public Type visit(Call n) {
		Type exp = n.ex.accept(this);		
		if(exp == null ||  !(exp instanceof IdentifierType) ) {
			error.complain ("Classe do objeto é inexistente.");
		}
		IdentifierType exp1 = (IdentifierType) exp;
		ClassTable classeExp = mainTable.getClass(Symbol.symbol(exp1.getValue()));
		MethodTable metodoId = classeExp.getMethod(Symbol.symbol(n.id.getValue()));
		if(metodoId == null) {
			error.complain ("Método não encontrado.");
		}
		if(n.expl.size() != metodoId.getParamNum()) {
			error.complain ("Quantidade de parametros inconsistente.");
		}
		
		Set chavesParam =  metodoId.pTable.entrySet();
		Symbol s;
		Map.Entry<Symbol, Type> me;
		Iterator it = chavesParam.iterator();
		for(int i = 0; i < n.expl.size(); i++) {
			Type tipoChamado = n.expl.elementAt(i).accept(this);
			me = (Map.Entry<Symbol, Type>) it.next();
			s = (Symbol) me.getKey(); 
			Type tipoParam = metodoId.getParam(s);			
			
			if(tipoChamado instanceof IdentifierType) {
				IdentifierType tipo1 = (IdentifierType) tipoChamado;				
				ClassTable classeChamada = mainTable.getClass(Symbol.symbol(tipo1.getValue())); 
				if(tipoParam.getTypeClass().equals("IdentifierType")) {
					if(classeChamada == null || !classeChamada.getClassName().equals( ((IdentifierType) tipoParam).getValue())) { 
						error.complain ("Tipo do argumento passado:" + classeChamada.getClassName() + "não compatível com o tipo esperado:" + tipoParam.getTypeClass());
					}
				}
				else {
					error.complain ("Tipo do argumento passado:" + classeChamada.getClassName() + "não compatível com o tipo esperado:" + tipoParam.getTypeClass());
				}
			}			
			else if (tipoChamado == null || !tipoChamado.getTypeClass().equals(tipoParam.getTypeClass()) ) {				
				error.complain ("Tipo do argumento passado:" + tipoChamado.getTypeClass() + "não compatível com o tipo esperado:" + tipoParam.getTypeClass() );
			}
		}
		
		return metodoId.getReturn();
	}
	
	//Exp ex;
	public Type visit(Not n) {
		if(! (n.ex.accept(this) instanceof BooleanType) ){
			error.complain ("Lado direito do operador deve ser do tipo Boolean");
		}
		return new BooleanType();
	}
	
	// String str;
	public Type visit(IdentifierExp n) {
		Type tipo = null;
		if ((currMethod != null) ){				
			if (currMethod.getVar(Symbol.symbol(n.str))!=null) {
				tipo = currMethod.getVar(Symbol.symbol(n.str));					
			}				
			if (currMethod.getParam(Symbol.symbol(n.str))!=null) {
				tipo =  currMethod.getParam(Symbol.symbol(n.str));					
			}				
		}			
		if ((currClass != null) && currClass.getVar(Symbol.symbol(n.str))!=null) {
			tipo = currClass.getVar(Symbol.symbol(n.str));					
		}
		if (mainTable.getClass(Symbol.symbol(n.str)) != null) {
			tipo = new IdentifierType(n.str);							
		}
		
		if(tipo == null) {
			error.complain("Identificador não encontrado");
		}
		
		return tipo;		
	}
	
	//String str
	public Type visit(Identifier n) {
		
		if ((currMethod != null) ){				
			if (currMethod.getVar(Symbol.symbol(n.str))!=null) {
				return currMethod.getVar(Symbol.symbol(n.str));					
			}				
			if (currMethod.getParam(Symbol.symbol(n.str))!=null) {
				return currMethod.getParam(Symbol.symbol(n.str));					
			}				
		}			
		if ((currClass != null) && currClass.getVar(Symbol.symbol(n.str))!=null) {
			return currClass.getVar(Symbol.symbol(n.str));					
		}
		if (mainTable.getClass(Symbol.symbol(n.str)) != null) {
			return new IdentifierType(n.str);							
		}
		
		return null;
	}
}