package co.eco.semana2;

public class Pregunta {

    private int x, y, z;
    private String operador;
    private String [] operandos = {"+","-", "*", "/"};

    public Pregunta(){

        this.x= (int) (Math.random()*11);
        this.y= (int) (Math.random()*11);
        if (x == 0 | y == 0){
            x ++;
            y ++;
        }
        int operadorRandom= (int) (Math.random()*4);
        this.operador= operandos[operadorRandom];
    }


    public String getPregunta(){
        if (operador.equals("/")){
            z=x*y;
            return z+" "+operador+" "+x;
        }else{
            return x+" "+operador+" "+y;
        }


    }

    public int getRespuesta(){

        int ans = 0;

        switch (operador){

            case "+":
                ans=x+y;
                break;

            case "-":
                ans=x-y;
                break;

            case "":
                ans=x*y;
                break;

            case "/":
                ans=z/x;
                break;
        }

        return ans;
    }


}
