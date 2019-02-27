package org.open.budget.costlocator.service;

import lombok.Builder;
import org.open.budget.costlocator.api.Street;
import org.open.budget.costlocator.repository.StreetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.*;

@Service
@Builder
public class StreetReaderServiceCsv {

    private static final Logger log = LoggerFactory.getLogger(StreetReaderServiceCsv.class);

    private StreetService streetService;

    public StreetReaderServiceCsv(StreetService streetService) {
        this.streetService = streetService;
    }

    @Transactional
    public void start() {
        {
            String path = StreetReaderServiceCsv.class.getResource("/houses.csv").getPath();

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
                    Street street = getStreet(csvRecord);
                    streetService.save(street);
                }
            } catch (IOException e) {
                throw new IllegalStateException("not able to read " + path, e);
            }
        }
    }

    Street getStreet(String[] csvRecord) {
        return Street.builder().region(csvRecord[0].toLowerCase())
                .city(csvRecord[2].replaceAll("^(смт)|(с\\. )|(м\\. )|(с-ще )", "").trim())
                .name(csvRecord[4].replaceAll("^(просп\\. )|(пл\\. )|(вул\\. )|(пров\\. )|(бульв\\. )|(вулиця )|" +
                        "(Вулиця)|(Проспект )|(Провулок )|(Площа )|(площа )|(тупік )|(Тупік )|(Бульвар )|(бульвар )|" +
                        "(Проїзд )|(проїзд )", "").trim()).build();
    }
}
