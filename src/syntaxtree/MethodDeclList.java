package syntaxtree;
import java.util.Vector;
import visitor.*;


public class MethodDeclList{
  	public Vector<MethodDecl> list;

	public MethodDeclList () {
		list = new Vector<MethodDecl>();
	}

	public void addElement(MethodDecl n) {
		list.addElement(n);
	}

	public MethodDecl elementAt(int i){
		return (MethodDecl)list.elementAt(i);
	}
	
	public int size(){
		return list.size();
	}
}