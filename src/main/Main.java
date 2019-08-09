package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import exemplos.*;
import syntaxtree.*;
import visitor.*;

public class Main {

	public static void main(String[] args) {
		MinijavaParser parser;

		try {
			parser = new MinijavaParser(new FileInputStream("exemplos/LinkedList.java"));
			// aqui gera a árvore ->
			Program program = parser.Program();
			SymbolTableVisitor v = new SymbolTableVisitor();
			// aqui faz o visitor começar a percorrer a árvore ->
			program.accept(v);
			TypeDepthFirstVisitor t = new TypeDepthFirstVisitor(v.mainTable);
			program.accept(t);
			System.out.println("Arquivo lido com sucesso.");
		} catch (FileNotFoundException e) {
			System.out.println("Arquivo não encontrado.");
		} catch (ParseException e) {
			System.out.println("Erro no parsing.");
		} catch (TokenMgrError e) {
			System.out.println("Erro de token - análise léxica.");
		}

	}

}
