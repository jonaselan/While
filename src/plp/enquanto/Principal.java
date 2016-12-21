package plp.enquanto;

import java.io.IOException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import plp.enquanto.parser.EnquantoLexer;
import plp.enquanto.parser.EnquantoParser;
import plp.enquanto.parser.MeuListener;
import static plp.enquanto.linguagem.Linguagem.*;
import static java.util.Arrays.*;

public class Principal {

	private static ParseTree parse(String programa) {
		final ANTLRInputStream input = new ANTLRInputStream(programa);
		final EnquantoLexer lexer = new EnquantoLexer(input);
		final CommonTokenStream tokens = new CommonTokenStream(lexer);
		final EnquantoParser parser = new EnquantoParser(tokens);
		return parser.programa();
	}

	public static void main(String... args) throws IOException {
		String programa = "x:=10; y:=leia ; c:= x + y; "
				+ "se 30<=c entao escreva c senao exiba \"menor\"";
		final ParseTree tree = parse(programa);
		final ParseTreeWalker walker = new ParseTreeWalker();
		final MeuListener listener = new MeuListener();
		walker.walk(listener, tree);
		Programa p1 = listener.getPrograma();
		p1.execute();

		// O parser devolve um objeto 'Programa' semelhante ao programa a seguir:
		// SE
		Programa p2 = new Programa(asList(
				new Atribuicao("x", new Inteiro(10)),                       // x := 10
				new Atribuicao("y", leia),                                  // y := leia
				new Atribuicao("c", new ExpSoma(new Id("x"), new Id("y"))), // c := x + y
				new Se(new ExpMenorIgual(new Inteiro(30), new Id("c")),     // se 30 =< c entao
						new Exiba("maior"),                         			  		// escreva "maior"
						new ExpMaiorIgual(new Inteiro(30), new Id("c")),				// senaose 30 >= c
						new Exiba("menor"),																			// escreva "menor"
						new Exiba("igual"))                                 		// senao exiba "outro"
				));
		p2.execute();

		// ENQUANTO
		// x := 5;
		// exiba "Qual a senha?";
		// y := Leia;
		// enquanto nao(x = y) {
		//   exiba "Qual a senha?";
		//   y := Leia;
		// };
		// exiba "Acertou"
//		Programa p3 = new Programa(asList(
//			new Atribuicao(new ID("x"), new Inteiro(5)),
//			new Exiba("Qual a senha?"),
//			new Atribuicao(new ID("y"), new Leia()),
//			new Enquanto(new NaoLogio(new ExpIgual(new ID("x"), new ID("y"))),
//			             new Bloco(asList(
//			               new Exiba("Qual a senha?"),
//			               new Atribuicao(new ID("y"), new Leia())
//			             )),
//			new Exiba("Acertou")
//		));
//		p3.execute();

		// PARA
		// i = 0
		// for (i; i <= 5 ; i++) System.out.println(i);
//		Programa p4 = new Programa(asList(
//				new Atribuicao("i", new Inteiro(0)),                      // i := 0
//				new Para(new Id("i"),																			// i
//								new ExpMenorIgual(new Inteiro(5), new Id("i")),   // i <= 5
//								new ExpSoma(new Id("i"), new Inteiro(1)),					// i++
//								new Exiba(new Id("i")))      											// System.out.println(i)
//				));
//		p4.execute();
	}
}
