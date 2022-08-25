import javax.swing.text.MaskFormatter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LeituraRetorno {
    private LeituraRetorno(){}

    static List<Boleto> lerArquivoBancoBrasil(String nomeArquivo) {
        System.out.println("Lendo arquivo: "+nomeArquivo);
        var listaBoleto = new ArrayList<Boleto>();
        try (var reader = Files.newBufferedReader(Paths.get(nomeArquivo))){
            String linha;
            while ((linha = reader.readLine()) != null){
                var vetor = linha.split(";");
                var boleto = new Boleto();
                boleto.setId(Integer.parseInt(vetor[0]));
                boleto.setCodBanco(vetor[1]);
                var formater = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                boleto.setDataVencimento(LocalDate.parse(vetor[2], formater));
                boleto.setDataPagamento(LocalDate.parse(vetor[3], formater).atTime(0,0));
                boleto.setCpfCliente(vetor[4]);
                boleto.setValor(Double.parseDouble(vetor[5]));
                boleto.setMulta(Double.parseDouble(vetor[6]));
                boleto.setJuros(Double.parseDouble(vetor[7]));
                listaBoleto.add(boleto);
            }

            return listaBoleto;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static List<Boleto> lerArquivoBradesco(String nomeArquivo) {
        System.out.println("Lendo arquivo: "+nomeArquivo);
        var listaBoleto = new ArrayList<Boleto>();
        try (var reader = Files.newBufferedReader(Paths.get(nomeArquivo))){
            String linha;
            while((linha = reader.readLine())!=null) {
                var vetor = linha.split(";");
                var boleto = new Boleto();
                var formaterDataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                var formater = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String cpfFormatter = "###.###.###-##";
                var maskformater = new MaskFormatter(cpfFormatter);
                maskformater.setValueContainsLiteralCharacters(false);
                boleto.setId(Integer.parseInt(vetor[0]));
                boleto.setCodBanco(vetor[1]);
                boleto.setAgencia(vetor[2]);
                boleto.setContabancaria(vetor[3]);
                boleto.setDataVencimento(LocalDate.parse(vetor[4], formater));
                boleto.setDataPagamento(LocalDateTime.parse(vetor[5], formaterDataHora));
                boleto.setCpfCliente(maskformater.valueToString(vetor[6]));
                boleto.setValor(Double.parseDouble(vetor[7]));
                boleto.setMulta(Double.parseDouble(vetor[8]));
                boleto.setJuros(Double.parseDouble(vetor[9]));
                listaBoleto.add(boleto);
            }

            return listaBoleto;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
