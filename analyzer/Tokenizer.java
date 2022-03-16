package analyzer;

import java.util.ArrayList;
import java.util.List;

import analyzer.Token.TokenType;

public class Tokenizer {
	
	private String npexpr;
	
	private static String[] OPERATORS = {"+", "-", "*", "/", "^"};
	private static String[] FUNCTIONS = {"log", "exp", "root", "sin", "cos", "tan", "cot", "sign"};
	public static Token EQUALS_TOKEN = new Token('=', TokenType.EQUALS);
			
			
	public Tokenizer(String npexpr) {
		this.npexpr = npexpr;
	}
	
	public List<Token> tokenize() {
		List<Token> tokens = new ArrayList<Token>();
		char[] chars = npexpr.toCharArray();
		int i = 0;
		while(i < chars.length) {
			if(chars[i] == ' ') i++;
			String temp = "";
			while(i < chars.length && isNumber(chars[i])) {
				temp += chars[i];
				i++;
			}
			if(!temp.isEmpty()) {
				tokens.add(new Token(temp, TokenType.NUMBER));
				temp = "";
			}
			
			if(i >= chars.length) break;
			
			if(isOperator(chars[i])) {
				tokens.add(new Token(chars[i], TokenType.OPERATOR));
			} else if(Character.isAlphabetic(chars[i])) {
				String alpha = "";
				while(i < chars.length && Character.isAlphabetic(chars[i])) {
					alpha += chars[i];
					i++;
				}
				if(isFunction(alpha)) tokens.add(new Token(alpha, TokenType.FUNCTION));
				else tokens.add(new Token(alpha, TokenType.VARIABLE));
				i--;
			} else if(chars[i] == '(' || chars[i] == ')') {
				tokens.add(new Token(chars[i], TokenType.PARENTHESIS));
			} else if(chars[i] == ',') {
				tokens.add(new Token(chars[i], TokenType.IDENTIFIER));
			} else if(chars[i] == '=') {
				tokens.add(EQUALS_TOKEN);
			} else {
				tokens.add(new Token(chars[i] + " (" + i + ")", TokenType.UNKNOWN));
			}
			
			i++;
		}
		
		
		
		return tokens;
	}
	
	private static <T> boolean contains(T[] array, T v) {
	    for (T e : array)
	        if (e == v || v != null && v.equals(e)) return true;
	    return false;
	}
	
	private static boolean isNumber(char c) {
		return Character.isDigit(c) || c == '.' || c == 'E';
	}
	
	private static boolean isOperator(char c) {
		return contains(OPERATORS, ""+c);
	}
	
	private static boolean isFunction(String st) {
		return contains(FUNCTIONS, st);
	}

}
