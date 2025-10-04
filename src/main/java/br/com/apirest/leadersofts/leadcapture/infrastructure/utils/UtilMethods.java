package br.com.apirest.leadersofts.leadcapture.infrastructure.utils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDate;
import java.util.List;

public interface UtilMethods {

    public List<LocalDate> getConvertedAndFormattedDates(String... date);

    public LocalDate getConvertedStringToDate(String dataNascimento);

    public boolean isValidDate(String data);

    public Long differenceOfDays(String registryDate);

    public String getPhoneAccordingToRegex(String telefone);

}
