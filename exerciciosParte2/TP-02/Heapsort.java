import java.util.*;
import java.io.*;

class Data {
    int ano, mes, dia;

    public Data(int a, int m, int d){
        ano=a; mes=m; dia=d;
    }

    public static Data parseData(String s){
        Scanner sc = new Scanner(s);
        sc.useDelimiter("-");
        int a = sc.nextInt();
        int m = sc.nextInt();
        int d = sc.nextInt();
        sc.close();
        return new Data(a,m,d);
    }

    public int comparar(Data o){
        if(this.ano != o.ano) return this.ano - o.ano;
        if(this.mes != o.mes) return this.mes - o.mes;
        return this.dia - o.dia;
    }

    public String formatar(){
        return String.format("%02d/%02d/%04d",dia,mes,ano);
    }
}

class Restaurante{
    int id;
    String nome;
    String cidade;
    int capacidade;
    double avaliacao;
    Data data;

    public Restaurante(int id,String nome,String cidade,int cap,double av,Data d){
        this.id=id; this.nome=nome; this.cidade=cidade;
        this.capacidade=cap; this.avaliacao=av; this.data=d;
    }

    public static Restaurante parse(String s){
        Scanner sc = new Scanner(s);
        sc.useDelimiter(",");
        int id = sc.nextInt();
        String nome = sc.next();
        String cidade = sc.next();
        int cap = sc.nextInt();
        double av = sc.nextDouble();
        sc.next(); // pula cozinhas
        sc.next(); // preco
        sc.next(); // horario
        String data = sc.next();
        sc.close();

        return new Restaurante(id,nome,cidade,cap,av,Data.parseData(data));
    }

    public String formatar(){
        return "["+id+" ## "+nome+" ## "+cidade+" ## "+data.formatar()+"]";
    }
}

class Colecao{
    Restaurante[] arr;
    int n;

    public Colecao(){
        arr = new Restaurante[2000];
        n=0;
    }

    public void ler() throws Exception{
        File f1 = new File("/tmp/restaurantes.csv");
        File f2 = new File("/tmp/RESTAURANTES.CSV");
        Scanner sc = new Scanner(f1.exists()?f1:f2);

        if(sc.hasNextLine()) sc.nextLine();

        while(sc.hasNextLine()){
            arr[n++] = Restaurante.parse(sc.nextLine());
        }
        sc.close();
    }

    public Restaurante buscar(int id){
        Restaurante r=null;
        for(int i=0;i<n;i++){
            if(arr[i].id==id) r=arr[i];
        }
        return r;
    }
}

public class Heapsort{
    public static final String MATRICULA="885173";

    public static int comparar(Restaurante a, Restaurante b, long[] comp){
        comp[0]++;
        int r = a.data.comparar(b.data);

        if(r==0){
            comp[0]++;
            r = a.nome.compareTo(b.nome);
        }
        return r;
    }

    public static void heapify(Restaurante[] arr,int n,int i,long[] comp,long[] mov){
        int maior=i;
        int esq=2*i+1;
        int dir=2*i+2;

        if(esq<n && comparar(arr[esq],arr[maior],comp)>0) maior=esq;
        if(dir<n && comparar(arr[dir],arr[maior],comp)>0) maior=dir;

        if(maior!=i){
            Restaurante tmp=arr[i];
            arr[i]=arr[maior];
            arr[maior]=tmp;
            mov[0]+=3;

            heapify(arr,n,maior,comp,mov);
        }
    }

    public static void heapsort(Restaurante[] arr,int n,long[] comp,long[] mov){
        for(int i=n/2-1;i>=0;i--) heapify(arr,n,i,comp,mov);

        for(int i=n-1;i>0;i--){
            Restaurante tmp=arr[0];
            arr[0]=arr[i];
            arr[i]=tmp;
            mov[0]+=3;

            heapify(arr,i,0,comp,mov);
        }
    }

    public static void log(long comp,long mov,double tempo) throws Exception{
        FileWriter fw = new FileWriter(MATRICULA+"_heapsort.txt");
        fw.write(MATRICULA+"\t"+comp+"\t"+mov+"\t"+tempo);
        fw.close();
    }

    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);
        Colecao c = new Colecao();
        c.ler();

        Restaurante[] base = new Restaurante[1000];
        int n=0;

        int id=sc.nextInt();
        while(id!=-1){
            Restaurante r=c.buscar(id);
            if(r!=null) base[n++]=r;
            id=sc.nextInt();
        }

        long[] comp={0};
        long[] mov={0};

        long ini=System.nanoTime();
        heapsort(base,n,comp,mov);
        long fim=System.nanoTime();

        for(int i=0;i<n;i++){
            System.out.println(base[i].formatar());
        }

        double tempo=(fim-ini)/1e9;
        log(comp[0],mov[0],tempo);

        sc.close();
    }
}
