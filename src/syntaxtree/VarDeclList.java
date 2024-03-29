package syntaxtree;
import java.util.Vector;
import visitor.*;

public class VarDeclList {
   private Vector<VarDecl> list;

   public VarDeclList() {
      list = new Vector<VarDecl>();
   }

   public void addElement(VarDecl n) {
      list.addElement(n);
   }

   public VarDecl elementAt(int i)  { 
      return (VarDecl)list.elementAt(i); 
   }

   public int size() { 
      return list.size(); 
   }
}