package analyzer;

public class Token {
	
	private String value;
	private TokenType type;
	
	public Token(String value, TokenType type) {
		this.value = value;
		this.type = type;
	}
	
	public Token(char value, TokenType type) {
		this(""+value, type);
	}
	
	public TokenType getType() {
		return type;
	}
	
	public String getValue() {
		return value;
	}
	
	public int getStrength()
    {
        if (type == TokenType.OPERATOR)
        {
            switch (value.charAt(0))
            {
                case '+':
                case '-':
                    return 1;
                case '/':
                case '*':
                    return 2;
                case '^':
                    return 3;
                default:
                    return -1;
            }
        }
        else if (type == TokenType.PARENTHESIS)
            return 4;
        
        return -1;
    }
	
	public AssociativeProperty getAssociativeProperty() {
		if (type == TokenType.OPERATOR)
        {
            switch (value.charAt(0))
            {
                case '+':
                case '*':
                    return AssociativeProperty.YES;
                case '-':
                case '/':
                    return AssociativeProperty.LEFT;
                case '^':
                    return AssociativeProperty.RIGHT;
                default:
                    return AssociativeProperty.NO;
            }
        }
        
        return AssociativeProperty.NO;
	}
	
	enum TokenType {
		NUMBER,
		UNKNOWN,
		VARIABLE,
		OPERATOR,
		IDENTIFIER,
		PARENTHESIS,
		EQUALS,
		FUNCTION;
	}
	
	enum AssociativeProperty {
		LEFT,
		RIGHT,
		YES,
		NO;
	}

}
