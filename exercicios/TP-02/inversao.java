class inversao{
    public static String inverte(String palavra, int tam - 1){
        String invertido = "";
        for(int i = tam; i >= 0;i--){
            char c = (char)palavra.charAt(i)
            invertido += c;
        }
        return invertido;
    }

    public static void main(String args[]){
        String palavra = MyIO.readLine();

        while(!palavra.equals("FIM")){
        int tam = palavra.length();

        MyIO.println(inverte(palavra, tam));

        palavra = MyIO.readLine();
        }
    }
}