------------------------
#Trabalho Final - Construção de Compiladores
------------------------
###Equipe:


**Thaís do Nascimento Lima** - 384382


**Pedro de Oliveira Lima Nunes** - 384377


**Carlos Abraão Sousa Assunção** - 401357 

------------------------
#Relatório Final - Construção de Compiladores
------------------------


##Parte I - Análise Léxica & Parser


A etapa I do trabalho foi 100% completada. O arquivo principal encontra-se no diretório syntaxtree/minijava.jj. Os tokens foram retirados da gramática presente no livro e foram utilizadas também as dicas presentes no mesmo para a elaboração do código desta etapa, como as dicas sobre identifiers, integer literals, e comentários. O parser foi testado com os programas testes do JavaCC, printando os tokens gerados, e estava 100% funcional. 
O parser foi implementado seguindo as regras da gramática do livro, também (mas depois sofreu modificações, devido a etapa da construção da árvore sintática abstrata). O problema da recursão a esquerda foi resolvido na maneira sugerida pelo livro, ou seja, transformando em recursão a direita. Essa parte foi feita pela aluna Thaís.


##Parte II - Sintaxe Abstrata & Análise Semântica


A etapa II do trabalho foi 100% completada. Inicialmente, as classes da árvore sintática abstrata foram implementada pelos 3 membros do grupo. Após isso, a construção da árvore sintática abstrata foi feita pela aluna Thaís (também encontra-se no arquivo syntaxtree/minijava.jj). Após esse início, dividimos a implementação dos visitors de tabela de símbolos e verificação de tipos de  maneira igualitária. O visitor da tabela de símbolos foi feito pela aluna Thaís e o visitor de verificação de tipos foi feito pelos alunos Carlos Abraão e Pedro. Durante a implementação do arquivo src/visitor/TypeDepthFirstVisitor.java
 As funções mais notáveis em questão de dificuldade de implementação foram as referentes ao Assign, ArrayAssign e Call, pois nas funções referentes a estas classes, foi-se necessário uma análise maior dos casos necessários para teste.
Após a finalização inicial de todas as classes, começamos a testar com os arquivos disponíveis no site do livro texto. Após diversas iterações e correções de erros, todos os exemplos foram executados com sucesso. 


##Parte III - Registros de Ativação & Tradução para Código Intermediário


A etapa III foi parcialmente concluída, cerca de 80%. O visitor responsável pela construção da árvore de linguagem intermediária (visitor/IRTreeVisitor) teve grande parte dos seus métodos implementados, mas houve uma dificuldade para entender como manipular variáveis de classe (que não eram do método, mas atributos da classe), então ficaram faltando alguns casos em certos métodos do nosso visitor.  
	Também não foram implementados os métodos visit para as classes This e ArrayLength, pois da mesma maneira ficou difícil abstrair e compreender como transformar em nós da árvore de linguagem intermediária. Não foram feitos testes para esta etapa devido à mesma estar incompleta. Essa parte foi realizada pela aluna Thaís.
	
##Parte IV - Blocos e Traços & Seleção de Instruções


A Etapa IV foi parcialmente concluída, cerca de 60%. A parte da geração de blocos e traços não foi implementada, mas implementamos a parte da ladrilhagem com o Maximal Munch. O livro foi de grande ajuda nesta etapa, visto que ofereceu muitos exemplos com a arquitetura Jouette que nos permitiram fazem adaptações para o MIPS e reutilizar algumas  coisas (a mudança essencial foi na instrução gerada, visto que eram arquiteturas diferentes. Por exemplo, algo que no Jouette era um STORE, no MIPS era um SW, que é um STORE WORD e tem uma estrutura diferente). Essa parte foi dividida entre os três integrantes de maneira igualitária. Não houveram testes também devido ao caráter incompleto desta etapa.


##Parte V - Análise de Longevidade & Alocação de Registros


A etapa V do trabalho não foi implementada por nossa equipe.
