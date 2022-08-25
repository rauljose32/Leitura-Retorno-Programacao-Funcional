public class Main {

    public static void main(String[] args) {

        var boletos = new ProcessarBoletos(LeituraRetorno::lerArquivoBradesco);
        boletos.processar("bradesco-1.csv");
    }

}
