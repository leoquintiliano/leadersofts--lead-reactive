package br.com.apirest.leadersofts.leadcapture.infrastructure.utils.implementation;

import br.com.apirest.leadersofts.leadcapture.infrastructure.dto.LeadRecord;
import br.com.apirest.leadersofts.leadcapture.infrastructure.utils.UtilMethods;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class UtilMethodsImpl implements UtilMethods {

    @Override
    public List<LocalDate> getConvertedAndFormattedDates(String... date) {

        return Stream.of(date).map(dateValue -> {
            if (Objects.nonNull(dateValue) && !dateValue.equals("-")) {
                dateValue = (dateValue.indexOf("-") == 2 && dateValue.indexOf("-") == 5) ||
                        (dateValue.indexOf("/") == 2 || dateValue.indexOf("/") == 5) ?
                        dateValue : formatDate(dateValue);

                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                var contextParamsDate = LocalDate.parse(dateValue.replace("-", "/"), dateTimeFormatter);
                return contextParamsDate;
            }
            return LocalDate.now();
        }).collect(Collectors.toList());
    }

    @Override
    public LocalDate getConvertedStringToDate(String dataNascimento) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if(dataNascimento.indexOf("/") == 4 || dataNascimento.indexOf("-") == 4){
            String year = dataNascimento.substring(0,4);
            String month = dataNascimento.substring(5,7);
            String day = dataNascimento.substring(8,dataNascimento.length());
            dataNascimento = day.concat("/").concat(month).concat("/").concat(year);
        }
        var contextParamsDate = LocalDate.parse(dataNascimento.replace("-","/"),dateTimeFormatter);

        return contextParamsDate;
    }

    @Override
    public boolean isValidDate(String data) { //2023/11/04
        return Objects.nonNull(data) && !data.equals("") && data.length() > 7 && (data.contains("/") || data.contains("-") ) ?
                Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public Long differenceOfDays(String registryDate) {
        var currentDate = LocalDate.now();
        return this.getDifferenceOfDaysValue(registryDate, currentDate);
    }

    @Override
    public String getPhoneAccordingToRegex(String telefone) {
        telefone = telefone.replace(".","").replace("(","").replace(")","").replace("-","");
        return telefone;
    }

    public static String formatDate(String date) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if( (date.indexOf("-") == 2 || date.indexOf("-") == 5 || date.indexOf("/") == 2 || date.indexOf("-") == 5) ) {
            var year = date.substring(0,2);
            var month = date.substring(3,5);
            var day = date.substring(6,date.length());
            date = day.concat("-").concat(month).concat("-").concat(year).replace("-","/");
            return date;
        }
        if(date.length() > 4) { // 16-10-2023  2023-10-16
            var year = date.substring(0,4);
            var month = date.substring(5,7);
            var day = date.substring(8,date.length());
            date = day.concat("-").concat(month).concat("-").concat(year).replace("-","/");
            return date;
        }
        return "";
    }
    
    public LeadRecord verifyEmptyStringsAndConvertToNull(String nome, String primeiroContato, String ultimoContato, String dataNascimento, String celular, String celular2,
                                                         String telefone, String email, String endereco, String uf, String cidade, String carroInteresse1, String carroInteresse2, String carroInteresse3,
                                                         String carroAtual1, String carroAtual2, String carroAtual3, String vendedor, String status, String opcaoVeiculo, String observacoes) {

        nome = nome.equals("-") ? null : nome;
        primeiroContato = primeiroContato.equals("-") ? null : primeiroContato;
        ultimoContato = ultimoContato.equals("-") ? null : ultimoContato;
        dataNascimento = dataNascimento.equals("-") ? null : dataNascimento;
        celular = celular.equals("-") ? null : celular;
        celular2 = celular2.equals("-") ? null : celular2;
        telefone = telefone.equals("-") ? null : telefone;
        email = email.equals("-") ? null : email;
        endereco = endereco.equals("-") ? null : endereco;
        uf = uf.equals("-") ? null : uf;
        cidade = cidade.equals("-") ? null : cidade;
        carroInteresse1 = carroInteresse1.equals("-") ? null : carroInteresse1;
        carroInteresse2 = carroInteresse2.equals("-") ? null : carroInteresse2;
        carroInteresse3 = carroInteresse3.equals("-") ? null : carroInteresse3;
        carroAtual1 = carroAtual1.equals("-") ? null : carroAtual1;
        carroAtual2 = carroAtual2.equals("-") ? null : carroAtual2;
        carroAtual3 = carroAtual3.equals("-") ? null : carroAtual3;
        vendedor = vendedor.equals("-") ? null : vendedor;
        status = status.equals("-") ? null : status;
        opcaoVeiculo = opcaoVeiculo.equals("-") ? null : opcaoVeiculo;
        observacoes = observacoes.equals("-") ? null : observacoes;

        return new LeadRecord(0L,nome,primeiroContato,ultimoContato,null,celular,celular2,telefone,endereco,email,uf,cidade,carroInteresse1,carroInteresse2,carroInteresse3,carroAtual1,carroAtual2,carroAtual3,vendedor,status,opcaoVeiculo,observacoes,"",0L,0L, null, 0L);

    }

    private static Long getDifferenceOfDaysValue(String registryDate, LocalDate currentDate) {
        Long differenceOfDaysSinceRegistered = 0L;
        if(registryDate.length() == 10) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            registryDate = (registryDate.indexOf("/") == 2 || registryDate.indexOf("-") == 2) ? registryDate.substring(6,10).concat("/")
                    .concat(registryDate.substring(3,5)).concat("/").concat(registryDate.substring(0,2)) : registryDate;
            var contextParamDate = LocalDate.parse(registryDate,dateTimeFormatter);
            return contextParamDate.datesUntil(currentDate).collect(Collectors.toList()).stream().count();
        }
        return differenceOfDaysSinceRegistered;
    }

}
