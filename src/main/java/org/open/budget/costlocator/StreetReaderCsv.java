package org.open.budget.costlocator;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.open.budget.costlocator.entity.City;
import org.open.budget.costlocator.entity.Region;
import org.open.budget.costlocator.entity.Street;
import org.open.budget.costlocator.service.StreetService;

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
                    Optional<Wrapper> wrapper = getStreet(csvRecord);
                    if (!wrapper.isPresent())
                        continue;
                    streetService.save(wrapper.get().region, wrapper.get().city, wrapper.get().street);
                }
                log.warn("------READ HAS ENDED------");
            } catch (IOException e) {
                throw new IllegalStateException("not able to read " + path, e);
            }
        }
    }

    Optional<Wrapper> getStreet(String[] csvRecord) {
        if (csvRecord.length < 5)
            return Optional.empty();
        if (csvRecord[4].isEmpty())
            return Optional.empty();
        String regionText = csvRecord[0].toLowerCase();
        if (regionText.equals("київ")){
            regionText = regionText.replace("київ", "київська");
        }
        Region region = Region.builder().name(regionText).build();
        City city = City.builder().name(csvRecord[2].replaceAll("^(смт)|(с\\. )|(м\\. )|(с-ще )", "").trim()).build();
        String index = csvRecord[3];
        String streetName = csvRecord[4].replaceAll("^(просп\\. )|(пл\\. )|(вул\\. )|(пров\\. )|(бульв\\. )|(вулиця )|" +
                "(Вулиця)|(Проспект )|(Провулок )|(Площа )|(площа )|(тупік )|(Тупік )|(Бульвар )|(бульвар )|" +
                "(Проїзд )|(проїзд )", "").trim();
        Street street = Street.builder().name(streetName).index(index).build();
        return Optional.of(new Wrapper(region, city, street));
    }

    static class Wrapper {
        Region region;
        City city;
        Street street;

        public Wrapper(Region region, City city, Street street) {
            this.region = region;
            this.city = city;
            this.street = street;
        }
    }
}
