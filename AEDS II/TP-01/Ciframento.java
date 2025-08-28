class Ciframento{
    public static String cifra(String str, int tamanho){
        String resultado = "";
        for (int i = 0; i < tamanho; i++) {
            char c = (char)(str.charAt(i) + 3);
            resultado += c;
        }
        return resultado;
    }

    public static void main(String[] args){
        String palavra = MyIO.readLine();

        while(!palavra.equals("FIM")){
        int tam = palavra.length();

        MyIO.println(cifra(palavra, tam));

        palavra = MyIO.readLine();
        }
    }
}