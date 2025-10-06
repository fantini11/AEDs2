import java.util.Scanner;

class somaDigitosRec{

    public static void somaDigitos(int numero, int soma) {

        if (numero == 0) {
            MyIO.println(soma);
            } 
            else {
            int digito = numero % 10;
            soma += digito;
            numero /= 10;
            somaDigitos(numero, soma);
            }
    }

    public static void main(String args[]) {
        Scanner sc= new Scanner(System.in);
        int soma = 0;

        while(sc.hasNextInt()){
        
            int numero=sc.nextInt();

            somaDigitos(numero, soma);

        }

    }
}