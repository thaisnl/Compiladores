package envs;

import syntaxtree.*;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class MethodTable {
    //symbol ou string?
    public String methodName;
    public Type typeMethod;
    public LinkedHashMap<Symbol, Type> pTable;
    public HashMap<Symbol, Type> vTable;

    public MethodTable(String mName, Type tMethod){
        this.methodName = mName;
        this.typeMethod = tMethod;
        this.pTable = new LinkedHashMap<Symbol, Type>();
        this.vTable = new HashMap<Symbol, Type>();
    }

    public boolean addParam(Symbol p, Type pType){
        if(pTable.get(p) == null){
            pTable.put(p, pType);
            return true;
        }
        return false;
    }

    public boolean addVar(Symbol v, Type vType){
        if(vTable.get(v) == null){
            vTable.put(v, vType);
            return true;
        }
        return false;
    }

    public Type getParam(Symbol p){
        return pTable.get(p);
    }
    
    public int getParamNum(){
    	return pTable.size();
    }  
    

    public Type getVar(Symbol v){
        return vTable.get(v);
    }
    
    public String getMethodName() {
    	return methodName;
    }
    
    public Type getReturn() {
    	return typeMethod;
    }

    public boolean removeParam(Symbol p){
        if(pTable.get(p) == null){
            return false;
        }
        pTable.remove(p);
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