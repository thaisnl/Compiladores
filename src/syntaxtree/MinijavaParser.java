/* Generated By:JavaCC: Do not edit this line. MinijavaParser.java */
package syntaxtree;
        public class MinijavaParser implements MinijavaParserConstants {
        public static void main (String[] args) {
                MinijavaParser parser;

                    parser = new MinijavaParser(System.in);
                     try{
                        parser.Program();
                        System.out.println ("O input foi lido com sucesso.");
                      }
                      catch(ParseException e){
                        System.out.println ("Erro durante a leitura.");
                        System.out.println (e.getMessage());
                      }
                      catch(TokenMgrError e){
                        System.out.println ("Houve um erro.");
                        System.out.println (e.getMessage());
                      }
                }

//void Start() :
// {}
// {  
//	( <CLASS> | <PUBLIC> | <STATIC> | <VOID> | <MAIN> | <STRING> | <LPAR> | <RPAR> | <LBRACE> | <RBRACE> | <LBRACKET> | <RBRACKET> |
//	<IF> | <ELSE> | <EXTENDS> | <RETURN> | <WHILE> | <SYSOUT> | <LENGTH> | <NEW> | <SEMICOLON> | <COMMA> | <POINT> | <EQUALS> |
//	<TRUE> | <FALSE> | <THIS> | <NOT> | <INT_B> | <INT> | <BOOLEAN> | <ID> | <OP> | <INTEGER_LITERAL> )* <EOF> 
//}
  static final public Program Program() throws ParseException {
        MainClass mc;
        ClassDeclList cdl = new ClassDeclList();
        ClassDecl cd;
    mc = MainClass();
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CLASS:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      cd = ClassDecl();
                                             cdl.addElement(cd);
    }
    jj_consume_token(0);
          {if (true) return new Program(mc, cdl);}
    throw new Error("Missing return statement in function");
  }

  static final public MainClass MainClass() throws ParseException {
        Identifier id1;
        Identifier id2;
        Statement st;
    jj_consume_token(CLASS);
    jj_consume_token(ID);
                       id1 = new Identifier(token.image);
    jj_consume_token(LBRACE);
    jj_consume_token(PUBLIC);
    jj_consume_token(STATIC);
    jj_consume_token(VOID);
    jj_consume_token(MAIN);
    jj_consume_token(LPAR);
    jj_consume_token(STRING);
    jj_consume_token(LBRACKET);
    jj_consume_token(RBRACKET);
    jj_consume_token(ID);
         id2 = new Identifier(token.image);
    jj_consume_token(RPAR);
    jj_consume_token(LBRACE);
    st = Statement();
    jj_consume_token(RBRACE);
    jj_consume_token(RBRACE);
          {if (true) return new MainClass(id1, id2, st);}
    throw new Error("Missing return statement in function");
  }

  static final public ClassDecl ClassDecl() throws ParseException {
        Identifier id1;
        Identifier id2;
        VarDeclList vdl = new VarDeclList();
        VarDecl vd;
        MethodDeclList mdl = new MethodDeclList();
        MethodDecl md;
    if (jj_2_1(3)) {
      jj_consume_token(CLASS);
      jj_consume_token(ID);
                        id1 = new Identifier(token.image);
      jj_consume_token(LBRACE);
      label_2:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case INT:
        case BOOLEAN:
        case ID:
          ;
          break;
        default:
          jj_la1[1] = jj_gen;
          break label_2;
        }
        vd = VarDecl();
                                                                                        vdl.addElement(vd);
      }
      label_3:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case PUBLIC:
          ;
          break;
        default:
          jj_la1[2] = jj_gen;
          break label_3;
        }
        md = MethodDecl();
                                                                                                                                    mdl.addElement(md);
      }
      jj_consume_token(RBRACE);
           {if (true) return new ClassDeclSimple(id1, vdl, mdl);}
    } else {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case CLASS:
        jj_consume_token(CLASS);
        jj_consume_token(ID);
                         id1 = new Identifier(token.image);
        jj_consume_token(EXTENDS);
        jj_consume_token(ID);
                                                                               id2 = new Identifier(token.image);
        jj_consume_token(LBRACE);
        label_4:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case INT:
          case BOOLEAN:
          case ID:
            ;
            break;
          default:
            jj_la1[3] = jj_gen;
            break label_4;
          }
          vd = VarDecl();
                                                                                                                                               vdl.addElement(vd);
        }
        label_5:
        while (true) {
          switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
          case PUBLIC:
            ;
            break;
          default:
            jj_la1[4] = jj_gen;
            break label_5;
          }
          md = MethodDecl();
                                                                                                                                                                                           mdl.addElement(md);
        }
        jj_consume_token(RBRACE);
           {if (true) return new ClassDeclExtends(id1, id2, vdl, mdl);}
        break;
      default:
        jj_la1[5] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    throw new Error("Missing return statement in function");
  }

  static final public VarDecl VarDecl() throws ParseException {
        Type t;
        Identifier id;
    t = Type();
    jj_consume_token(ID);
                          id = new Identifier(token.image);
    jj_consume_token(SEMICOLON);
          {if (true) return new VarDecl(t, id);}
    throw new Error("Missing return statement in function");
  }

  static final public MethodDecl MethodDecl() throws ParseException {
        Type t;
        Identifier i;
        FormalList fl;
        VarDeclList vdl = new VarDeclList();
        VarDecl vd;
        StatementList stl = new StatementList();
        Statement st;
        Exp e;
    jj_consume_token(PUBLIC);
    t = Type();
    jj_consume_token(ID);
                                   i = new Identifier(token.image);
    jj_consume_token(LPAR);
    fl = FormalList();
    jj_consume_token(RPAR);
    jj_consume_token(LBRACE);
    label_6:
    while (true) {
      if (jj_2_2(2)) {
        ;
      } else {
        break label_6;
      }
      vd = VarDecl();
                                                                                                                                             vdl.addElement(vd);
    }
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case LBRACE:
      case IF:
      case WHILE:
      case SYSOUT:
      case ID:
        ;
        break;
      default:
        jj_la1[6] = jj_gen;
        break label_7;
      }
      st = Statement();
                                                                                                                                                                                          stl.addElement(st);
    }
    jj_consume_token(RETURN);
    e = Exp();
    jj_consume_token(SEMICOLON);
    jj_consume_token(RBRACE);
          {if (true) return new MethodDecl(t, i, fl, vdl, stl, e);}
    throw new Error("Missing return statement in function");
  }

  static final public Formal Formal() throws ParseException {
        Type t;
        Identifier i;
    t = Type();
    jj_consume_token(ID);
                          i = new Identifier(token.image);
          {if (true) return new Formal(t,i);}
    throw new Error("Missing return statement in function");
  }

  static final public FormalList FormalList() throws ParseException {
        FormalList fl = new FormalList();
        Formal f;
        Formal f1;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INT:
    case BOOLEAN:
    case ID:
      f = Formal();
                         fl.addElement(f);
      label_8:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[7] = jj_gen;
          break label_8;
        }
        f1 = FormalRest();
                                                                  fl.addElement(f1);
      }
          {if (true) return fl;}
      break;
    default:
      jj_la1[8] = jj_gen;
            {if (true) return fl;}
    }
    throw new Error("Missing return statement in function");
  }

  static final public Formal FormalRest() throws ParseException {
        Type t;
        Identifier id;
    jj_consume_token(COMMA);
    t = Type();
    jj_consume_token(ID);
                                  id = new Identifier(token.image);
          {if (true) return new Formal(t, id);}
    throw new Error("Missing return statement in function");
  }

  static final public Type Type() throws ParseException {
    if (jj_2_3(2)) {
      jj_consume_token(INT);
      jj_consume_token(LBRACKET);
      jj_consume_token(RBRACKET);
                                                   {if (true) return new IntArrayType();}
    } else {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case INT:
        jj_consume_token(INT);
                  {if (true) return new IntegerType();}
        break;
      case BOOLEAN:
        jj_consume_token(BOOLEAN);
                      {if (true) return new BooleanType();}
        break;
      case ID:
        jj_consume_token(ID);
                 {if (true) return new IdentifierType(token.image);}
        break;
      default:
        jj_la1[9] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
    throw new Error("Missing return statement in function");
  }

  static final public Statement Statement() throws ParseException {
        Statement st1;
        Statement st2;
        StatementList stl = new StatementList();
        Exp e1;
        Exp e2;
        Identifier i;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LBRACE:
      jj_consume_token(LBRACE);
      label_9:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case LBRACE:
        case IF:
        case WHILE:
        case SYSOUT:
        case ID:
          ;
          break;
        default:
          jj_la1[10] = jj_gen;
          break label_9;
        }
        st1 = Statement();
                                       stl.addElement(st1);
      }
      jj_consume_token(RBRACE);
          {if (true) return new Block(stl);}
      break;
    case IF:
      jj_consume_token(IF);
      jj_consume_token(LPAR);
                        e1 = Exp();
      jj_consume_token(RPAR);
      st1 = Statement();
      jj_consume_token(ELSE);
      st2 = Statement();
          {if (true) return new If(e1, st1, st2);}
      break;
    case WHILE:
      jj_consume_token(WHILE);
      jj_consume_token(LPAR);
      e1 = Exp();
      jj_consume_token(RPAR);
      st1 = Statement();
          {if (true) return new While(e1, st1);}
      break;
    case SYSOUT:
      jj_consume_token(SYSOUT);
      jj_consume_token(LPAR);
      e1 = Exp();
      jj_consume_token(RPAR);
      jj_consume_token(SEMICOLON);
          {if (true) return new Print(e1);}
      break;
    default:
      jj_la1[11] = jj_gen;
      if (jj_2_4(2)) {
        jj_consume_token(ID);
                              i = new Identifier(token.image);
        jj_consume_token(EQUALS);
        e1 = Exp();
        jj_consume_token(SEMICOLON);
          {if (true) return new Assign(i, e1);}
      } else {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case ID:
          jj_consume_token(ID);
                 i = new Identifier(token.image);
          jj_consume_token(LBRACKET);
          e1 = Exp();
          jj_consume_token(RBRACKET);
          jj_consume_token(EQUALS);
          e2 = Exp();
          jj_consume_token(SEMICOLON);
          {if (true) return new ArrayAssign(i, e1, e2);}
          break;
        default:
          jj_la1[12] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    }
    throw new Error("Missing return statement in function");
  }

  static final public Exp Exp() throws ParseException {
        IntegerLiteral il;
        True t;
        False f;
        IdentifierExp id;
        Identifier id2;
        This ths;
        Exp e;
        NewArray arr;
        NewObject obj;
        Not n;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INTEGER_LITERAL:
      jj_consume_token(INTEGER_LITERAL);
                            il = new IntegerLiteral(Integer.parseInt(token.image));
          {if (true) return Exp1(il);}
      break;
    case TRUE:
      jj_consume_token(TRUE);
                   t = new True();
          {if (true) return Exp1(t);}
      break;
    case FALSE:
      jj_consume_token(FALSE);
                    f = new False();
          {if (true) return Exp1(f);}
      break;
    case ID:
      jj_consume_token(ID);
                 id = new IdentifierExp(token.image);
          {if (true) return Exp1(id);}
      break;
    case THIS:
      jj_consume_token(THIS);
                   ths = new This();
          {if (true) return Exp1(ths);}
      break;
    default:
      jj_la1[13] = jj_gen;
      if (jj_2_5(2)) {
        jj_consume_token(NEW);
        jj_consume_token(INT);
        jj_consume_token(LBRACKET);
        e = Exp();
        jj_consume_token(RBRACKET);
                                                                    arr = new NewArray(e);
          {if (true) return Exp1(arr);}
      } else {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NEW:
          jj_consume_token(NEW);
          jj_consume_token(ID);
                       id2 = new Identifier(token.image);
          jj_consume_token(LPAR);
          jj_consume_token(RPAR);
                                                                            obj = new NewObject(id2);
          {if (true) return Exp1(obj);}
          break;
        case NOT:
          jj_consume_token(NOT);
          e = Exp();
                            n = new Not(e);
          {if (true) return Exp1(n);}
          break;
        case LPAR:
          jj_consume_token(LPAR);
          e = Exp();
          jj_consume_token(RPAR);
          {if (true) return Exp1(e);}
          break;
        default:
          jj_la1[14] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    }
    throw new Error("Missing return statement in function");
  }

  static final public Exp Exp1(Exp e1) throws ParseException {
        ExpList el;
        Exp e2;
        Minus m;
        Plus p;
        LessThan l;
        And a;
        Times t;
        Identifier id;
        ArrayLength al;
        ArrayLookup alookup;
        Call c;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case MINUS:
      jj_consume_token(MINUS);
      e2 = Exp();
                             m = new Minus(e1, e2);
          {if (true) return Exp1(m);}
      break;
    case PLUS:
      jj_consume_token(PLUS);
      e2 = Exp();
                              p = new Plus(e1, e2);
          {if (true) return Exp1(p);}
      break;
    case LESS_THAN:
      jj_consume_token(LESS_THAN);
      e2 = Exp();
                                   l = new LessThan(e1,e2);
          {if (true) return Exp1(l);}
      break;
    case AND:
      jj_consume_token(AND);
      e2 = Exp();
                             a = new And(e1, e2);
          {if (true) return Exp1(a);}
      break;
    case TIMES:
      jj_consume_token(TIMES);
      e2 = Exp();
                               t = new Times(e1, e2);
          {if (true) return Exp1(t);}
      break;
    case LBRACKET:
      jj_consume_token(LBRACKET);
      e2 = Exp();
      jj_consume_token(RBRACKET);
                                             alookup = new ArrayLookup(e1,e2);
          {if (true) return Exp1(alookup);}
      break;
    default:
      jj_la1[15] = jj_gen;
      if (jj_2_6(2)) {
        jj_consume_token(DOT);
        jj_consume_token(LENGTH);
                                        al = new ArrayLength(e1);
          {if (true) return Exp1(al);}
      } else {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case DOT:
          jj_consume_token(DOT);
          jj_consume_token(ID);
                       id = new Identifier(token.image);
          jj_consume_token(LPAR);
                                                                    el = ExpList();
          jj_consume_token(RPAR);
                                                                                               c = new Call(e1, id, el);
          {if (true) return Exp1(c);}
          break;
        default:
          jj_la1[16] = jj_gen;
            {if (true) return e1;}
        }
      }
    }
    throw new Error("Missing return statement in function");
  }

  static final public ExpList ExpList() throws ParseException {
        Exp e;
        Exp er;
        ExpList el = new ExpList();
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case LPAR:
    case NEW:
    case TRUE:
    case FALSE:
    case THIS:
    case NOT:
    case ID:
    case INTEGER_LITERAL:
      e = Exp();
                      el.addElement(e);
      label_10:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case COMMA:
          ;
          break;
        default:
          jj_la1[17] = jj_gen;
          break label_10;
        }
        er = ExpRest();
                                                               el.addElement(er);
      }
          {if (true) return el;}
      break;
    default:
      jj_la1[18] = jj_gen;
            {if (true) return el;}
    }
    throw new Error("Missing return statement in function");
  }

  static final public Exp ExpRest() throws ParseException {
        Exp e;
    jj_consume_token(COMMA);
    e = Exp();
          {if (true) return e;}
    throw new Error("Missing return statement in function");
  }

  static private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  static private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  static private boolean jj_2_3(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_3(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(2, xla); }
  }

  static private boolean jj_2_4(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_4(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(3, xla); }
  }

  static private boolean jj_2_5(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_5(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(4, xla); }
  }

  static private boolean jj_2_6(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_6(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(5, xla); }
  }

  static private boolean jj_3_6() {
    if (jj_scan_token(DOT)) return true;
    if (jj_scan_token(LENGTH)) return true;
    return false;
  }

  static private boolean jj_3_3() {
    if (jj_scan_token(INT)) return true;
    if (jj_scan_token(LBRACKET)) return true;
    return false;
  }

  static private boolean jj_3R_12() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_3()) {
    jj_scanpos = xsp;
    if (jj_3R_13()) {
    jj_scanpos = xsp;
    if (jj_3R_14()) {
    jj_scanpos = xsp;
    if (jj_3R_15()) return true;
    }
    }
    }
    return false;
  }

  static private boolean jj_3_1() {
    if (jj_scan_token(CLASS)) return true;
    if (jj_scan_token(ID)) return true;
    if (jj_scan_token(LBRACE)) return true;
    return false;
  }

  static private boolean jj_3_2() {
    if (jj_3R_11()) return true;
    return false;
  }

  static private boolean jj_3R_11() {
    if (jj_3R_12()) return true;
    if (jj_scan_token(ID)) return true;
    return false;
  }

  static private boolean jj_3_5() {
    if (jj_scan_token(NEW)) return true;
    if (jj_scan_token(INT)) return true;
    return false;
  }

  static private boolean jj_3R_15() {
    if (jj_scan_token(ID)) return true;
    return false;
  }

  static private boolean jj_3R_14() {
    if (jj_scan_token(BOOLEAN)) return true;
    return false;
  }

  static private boolean jj_3R_13() {
    if (jj_scan_token(INT)) return true;
    return false;
  }

  static private boolean jj_3_4() {
    if (jj_scan_token(ID)) return true;
    if (jj_scan_token(EQUALS)) return true;
    return false;
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public MinijavaParserTokenManager token_source;
  static SimpleCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private Token jj_scanpos, jj_lastpos;
  static private int jj_la;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[19];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x2,0x60000000,0x4,0x60000000,0x4,0x2,0x62200,0x400000,0x60000000,0x60000000,0x62200,0x62200,0x0,0xe000000,0x10100080,0x800,0x800000,0x400000,0x1e100080,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x4,0x0,0x4,0x0,0x0,0x4,0x0,0x4,0x4,0x4,0x0,0x4,0x104,0x0,0xf8,0x0,0x0,0x104,};
   }
  static final private JJCalls[] jj_2_rtns = new JJCalls[6];
  static private boolean jj_rescan = false;
  static private int jj_gc = 0;

  /** Constructor with InputStream. */
  public MinijavaParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public MinijavaParser(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new MinijavaParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 19; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 19; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public MinijavaParser(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new MinijavaParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 19; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 19; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public MinijavaParser(MinijavaParserTokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 19; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(MinijavaParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 19; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  static final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  static private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;
  static private int[] jj_lasttokens = new int[100];
  static private int jj_endpos;

  static private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      boolean exists = false;
      for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        exists = true;
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              exists = false;
              break;
            }
          }
          if (exists) break;
        }
      }
      if (!exists) jj_expentries.add(jj_expentry);
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[47];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 19; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 47; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

  static private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 6; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
            case 2: jj_3_3(); break;
            case 3: jj_3_4(); break;
            case 4: jj_3_5(); break;
            case 5: jj_3_6(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  static private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

        }
