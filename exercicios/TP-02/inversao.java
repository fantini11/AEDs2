class inversao{
    public static String inverte(String palavra, int tam){
        String invertido = ""; 
        for(int i = tam; i >= 0;i--){
            invertido += (char)palavra.charAt(i);
        }
        return invertido;
    }

    public static void main(String args[]){
        String palavra = MyIO.readLine();

        while(!palavra.equals("FIM")){
        int tam = palavra.length() - 1;

        MyIO.println(inverte(palavra, tam));

        palavra = MyIO.readLine();
        }
    }
}