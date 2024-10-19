
package calculator;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;



@Named
@RequestScoped
public class BeanCalculator {

    /**
     * Creates a new instance of BeanCalculator
     */
    
    private int number1;
    private int number2;
    private int result;
    private String operator;

    
    
    public String calculate(){
        if (this.operator.equals("+")){
            setResult(addition(number1, number2));
        } else if (this.operator.equals("-")){
            setResult(subtraction(number1, number2));
        } else if (this.operator.equals("/")){
            setResult(division(number1, number2));
        } else if (this.operator.equals("*")){
            setResult(multiplicaton(number1, number2));
        }
        return "index";
    }
           
           
    public BeanCalculator() {
    }

    public int getNumber1() {
        return number1;
    }

    public void setNumber1(int number1) {
        this.number1 = number1;
    }

    public int getNumber2() {
        return number2;
    }

    public void setNumber2(int number2) {
        this.number2 = number2;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
    
    
    public int addition (int number1, int number2){
            int added = (number1 + number2);
            return added;
    }
      
    public int subtraction (int number1, int number2){
            int subtracted = number1 - number2;
            return subtracted;
    }

      public int division (int number1, int number2){
            int divided = number1 / number2;
            return divided;
    }
      
        public int multiplicaton (int number1, int number2){
            int multiplicated = number1 * number2;
            return multiplicated;
    }


    
    
    
}
