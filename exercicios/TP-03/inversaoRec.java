import java.util.Scanner;

class inversaoRec {
    public static void inverte(String palavra, int tam){
        if (tam >= 0) {
            System.out.print(palavra.charAt(tam));
            inverte(palavra, tam - 1);
        }
    }

    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        String palavra = sc.nextLine();

        while (!palavra.equals("FIM")) {
            int tam = palavra.length() - 1;

            inverte(palavra, tam);
            System.out.println();
            palavra = sc.nextLine();
        }
        sc.close();
    }
}