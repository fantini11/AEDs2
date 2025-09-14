import java.util.Arrays;

class anagrama{
    public static void ehAnagrama(String palavra1, String palavra2){
      boolean saoIguais = true;
        // Verifica se as strings têm o mesmo tamanho, uma condição básica para serem anagramas.
        if (palavra1.length() != palavra2.length()) {
            saoIguais = false;
            MyIO.println("NÃO");
            return;
        }

        if (saoIguais) {
            // Converte as strings para arrays de caracteres para poder ordená-las.
            char[] array1 = palavra1.toCharArray();
            char[] array2 = palavra2.toCharArray();
            int tam = palavra1.length();

            // Ordena o primeiro array de caracteres em ordem decrescente (Bubble Sort).
            for (int i = 0; i < tam - 1; i++) {
                for (int j = 0; j < tam - 1 - i; j++) {
                    if (array1[j] < array1[j + 1]) {
                        char aux = array1[j];
                        array1[j] = array1[j + 1];
                        array1[j + 1] = aux;
                    }
                }
            }

            // Ordena o segundo array da mesma forma.
            for (int i = 0; i < tam - 1; i++) {
                for (int j = 0; j < tam - 1 - i; j++) {
                    if (array2[j] < array2[j + 1]) {
                        char aux = array2[j];
                        array2[j] = array2[j + 1];
                        array2[j + 1] = aux;
                    }
                }
            }

            // Compara os dois arrays ordenados, caractere por caractere.
            for (int i = 0; i < tam; i++) {
                // Se encontrar uma diferença, não são anagramas e o laço é interrompido.
                if (array1[i] != array2[i]) {
                    saoIguais = false;
                    MyIO.println("NÃO");
                    return;
                }
            }
            if (saoIguais) {
                MyIO.println("SIM");
        }
    }
        return;
    }

    public static void main(String args[]){
        String palavra1 = MyIO.readString();
        
        while(!palavra1.equals("FIM")){
        String palavra2 = MyIO.readString();

        ehAnagrama(palavra1, palavra2);

        palavra1 = MyIO.readString();
        }
    }
}