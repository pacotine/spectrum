package analyzer;

import java.util.List;
import java.util.Stack;

import analyzer.Token.AssociativeProperty;
import analyzer.Token.TokenType;

public class Parser {
	
	private String npexpr;
	
	public Parser(String npexpr) {
		this.npexpr = npexpr;
	}
	
	public Stack<Token> parse() {
		//Shunting-yard algorithm
		Tokenizer tokenizer = new Tokenizer(npexpr);
		List<Token> tokens = tokenizer.tokenize();
		System.out.println("- TOKENIZER -");
		for(Token tok : tokens) {
			System.out.println("Tok. : " + tok.getValue() + " | " + tok.getType() + " | " + tok.getAssociativeProperty());
		}
		Stack<Token> output = new Stack<Token>();
		Stack<Token> opstack = new Stack<Token>();
		int i = 0;
		
		while(i < tokens.size()) {
			Token current = tokens.get(i);
			
			switch (current.getType()) {
			case NUMBER: case VARIABLE:
				output.push(current);
				break;
			
			case FUNCTION:
				opstack.push(current);
				break;
			
			case IDENTIFIER:
				while(!opstack.lastElement().getValue().equals("(")) {
					if(opstack.lastElement().getValue().equals("(")) opstack.pop();
					else output.push(opstack.pop());
				}
				break;
			
			case OPERATOR:
				while(!opstack.isEmpty() && opstack.lastElement().getType() == TokenType.OPERATOR &&
				(((current.getAssociativeProperty() == AssociativeProperty.YES 
				|| current.getAssociativeProperty() == AssociativeProperty.LEFT)
				&& current.getStrength() <= opstack.lastElement().getStrength())
						||
				(current.getAssociativeProperty() == AssociativeProperty.RIGHT && current.getStrength() < opstack.lastElement().getStrength()))
				) {
					output.push(opstack.pop());
				}
				opstack.push(current);
				break;
			
			case PARENTHESIS:
				if(current.getValue().equals("(")) {
					opstack.push(current);
				} else {
					while(!opstack.isEmpty() && !opstack.lastElement().getValue().equals("(")) {
						output.push(opstack.pop());
					}
					if(!opstack.isEmpty() && opstack.lastElement().getValue().equals("(")) opstack.pop();
					if(!opstack.isEmpty() && opstack.lastElement().getType() == TokenType.FUNCTION) output.push(opstack.pop());
				}
				break;
			

			default:
				break;
			}
		
			i++;
		}
		while(!opstack.isEmpty()) {
			if(!opstack.lastElement().getValue().equals("(")) {
				output.push(opstack.pop());
			}
		}
		return output;
	}
}