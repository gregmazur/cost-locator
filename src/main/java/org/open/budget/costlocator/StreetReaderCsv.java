package org.open.budget.costlocator;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.open.budget.costlocator.api.Street;
import org.open.budget.costlocator.service.StreetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

@Builder
@Slf4j
public class StreetReaderCsv {

    private StreetService streetService;

    public StreetReaderCsv(StreetService streetService) {
        this.streetService = streetService;
    }

    @Transactional
    public void start() {
        {
            String path = StreetReaderCsv.class.getResource("/houses.csv").getPath();

            try (
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "windows-1251"))
            ) {
                String line;
                boolean firstLine = true;
                while ((line = br.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }
                    log.info("Reading line");
                    String[] csvRecord = line.split(";");
                    Optional<Street> street = getStreet(csvRecord);
                    if (!street.isPresent())
                        continue;
                    streetService.save(street.get());
                }
                log.warn("------READ HAS ENDED------");
            } catch (IOException e) {
                throw new IllegalStateException("not able to read " + path, e);
            }
        }
    }

    Optional<Street> getStreet(String[] csvRecord) {
        if (csvRecord.length < 5)
            return Optional.empty();
        if (csvRecord[4].isEmpty())
            return Optional.empty();
        return Optional.of(Street.builder().region(csvRecord[0].toLowerCase())
                .city(csvRecord[2].replaceAll("^(смт)|(с\\. )|(м\\. )|(с-ще )", "").trim())
                .name(csvRecord[4].replaceAll("^(просп\\. )|(пл\\. )|(вул\\. )|(пров\\. )|(бульв\\. )|(вулиця )|" +
                        "(Вулиця)|(Проспект )|(Провулок )|(Площа )|(площа )|(тупік )|(Тупік )|(Бульвар )|(бульвар )|" +
                        "(Проїзд )|(проїзд )", "").trim()).build());
    }
}
