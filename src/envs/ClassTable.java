package envs;

import java.util.HashMap;
import java.util.List;

import syntaxtree.*;

public class ClassTable {
    //symbol ou string?
    String className;
    String parentClass = null;
    public HashMap<Symbol, MethodTable> mTable;
    public HashMap<Symbol, Type> vTable;
    public HashMap<Symbol, Integer> offsetVal;
    private int cont = 0;
   
    public String getClassName() {
    	return className;
    }
    
    public ClassTable(String cName){
        this.className = cName;
        this.mTable = new HashMap<Symbol, MethodTable>();
        this.vTable = new HashMap<Symbol, Type>();
    }
    
    public ClassTable(String cName, String pName){
        this.className = cName;
        this.parentClass = pName;
        this.mTable = new HashMap<Symbol, MethodTable>();
        this.vTable = new HashMap<Symbol, Type>();
    }

    public boolean addMethod(Symbol m, MethodTable mt){
        if(mTable.get(m) == null){
            mTable.put(m, mt);
            return true;
        }
        return false;
    }

    public boolean addVar(Symbol v, Type vType){
        if(vTable.get(v) == null){
            vTable.put(v, vType);
            offsetVal.put(v,cont);
            cont++;
            return true;
        }
        return false;
    }

    public Integer getOffset(Symbol var) {
    	return offsetVal.get(var);
    }
    
    public MethodTable getMethod(Symbol m){
        return mTable.get(m);
    }

    public Type getVar(Symbol v){
        return vTable.get(v);
    }

    public boolean removeMethod(Symbol m){
        if(mTable.get(m) == null){
            return false;
        }
        mTable.remove(m);
        return true;
    }

    public boolean removeVar(Symbol v){
        if(vTable.get(v) == null){
            return false;
        }
        vTable.remove(v);
        return true;
    }
}