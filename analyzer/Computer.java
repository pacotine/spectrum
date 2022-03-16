package analyzer;

import java.util.Stack;

public class Computer {
	
	private Stack<Token> parsedExpr;
	
	public Computer(Stack<Token> parsedExpr) {
		this.parsedExpr = parsedExpr;
	}
	
	public MathematicalExpression compute() {	
		if(parsedExpr.contains(Tokenizer.EQUALS_TOKEN)) {
			return solveEquation();
		}
		Stack<Double> stack = new Stack<Double>();
		
		for(Token element : parsedExpr) {
			System.out.println(element.getType() + " | " + element.getValue());
		}
		
		while(parsedExpr.size() > 0) {
			Token current = parsedExpr.remove(0);
			
			switch(current.getType()) {
				case NUMBER:
					stack.push(Double.parseDouble(current.getValue()));
					break;
				case VARIABLE:
					if(current.getValue().equals("e")) stack.push(Math.E);
					else if(current.getValue().equals("pi")) stack.push(Math.PI);
					break;
				case OPERATOR:
					double b = stack.pop();
					double a;
					if(stack.size() == 0) {
						throw new IllegalArgumentException("Missing token for Computer");
					} else {
						a = stack.pop();
					}
					switch (current.getValue()) {
					case "+":
						stack.push(a+b);
						break;
					case "-":
						stack.push(a-b);
						break;
					case "*":
						stack.push(a*b);
						break;		
					case "/":
						stack.push(a/b);
						break;			
					case "^":
						stack.push(Math.pow(a, b));
						break;
						
					default:
						break;
					}
					break;
				case FUNCTION:
					double arg = stack.pop();
					switch (current.getValue()) {
					case "root":
						if(stack.size() >= 1) stack.push(Math.pow(stack.pop(), 1/arg));
						else throw new IllegalArgumentException("Function 'root' takes two arguments");
						break;
					case "exp":
						stack.push(Math.exp(arg));
						break;
					case "log":
						if(stack.size() >= 1) {
							if(arg == Math.E) stack.push(Math.log(stack.pop()));
							else stack.push(Math.log10(stack.pop())/Math.log10(arg));
						} else throw new IllegalArgumentException("Function 'log' takes two arguments");
						break;
					case "sin":
						stack.push(Math.sin(arg));
						break;
					case "cos":
						stack.push(Math.cos(arg));
						break;
					case "tan":
						stack.push(Math.tan(arg));
						break;
					case "cot":
						stack.push(Math.cos(arg)/Math.sin(arg));
						break;
					case "sign":
						stack.push(Math.signum(arg));
						break;
						
					default:
						break;
					}
					break;
					
				default:
					break;
			}
			
		}
		
		return new MathematicalExpression(stack.pop());
	}
	
	private MathematicalExpression solveEquation() {
		return new MathematicalExpression(0); //TODO
	}

}
