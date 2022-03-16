package analyzer;

public class MathematicalExpression {
	
	private double value;
	
	public MathematicalExpression(double value) {
		this.value = value;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double newValue) {
		this.value = newValue;
	}
	
	@Override
	public String toString() {
		return Double.toString(value);
	}
	
	//TODO

}
