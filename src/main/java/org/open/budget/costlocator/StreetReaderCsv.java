package org.open.budget.costlocator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.open.budget.costlocator.entity.City;
import org.open.budget.costlocator.entity.Region;
import org.open.budget.costlocator.entity.Street;
import org.open.budget.costlocator.service.StreetService;
import org.open.budget.costlocator.service.TenderService;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;

@Builder
@Slf4j
@AllArgsConstructor
public class StreetReaderCsv {

    public static String VERSION_OF_LOADED_HOUSES = "HOUSES_VERSION";

    private StreetService streetService;

    private TenderService tenderService;

    private String housesCsvName;

    public void start() {
        {
            String path = housesCsvName;
            String version = tenderService.getProperty(VERSION_OF_LOADED_HOUSES);

            try (
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "windows-1251"))
            ) {
                log.warn("ATTENTION---STARTED READ FROM HOUSES----------ATTENTION---------------ATTENTION---------------------");
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

                if (version == null){
                    tenderService.saveProp(VERSION_OF_LOADED_HOUSES, "1");
                } else {
                    tenderService.saveProp(VERSION_OF_LOADED_HOUSES, String.valueOf(Integer.valueOf(version) + 1));
                }
            } catch (IOException e) {
                if (version == null) {
                    throw new IllegalStateException("not able to read " + path, e);
                }
            }
        }
    }

    Optional<Wrapper> getStreet(String[] csvRecord) {
        if (csvRecord.length < 5)
            return Optional.empty();
        if (csvRecord[4].isEmpty())
            return Optional.empty();
        String regionText = csvRecord[0];
        if (regionText.equals("Київ")){
            regionText = regionText.replace("Київ", "Київська");
        }
        Region region = Region.builder().name(regionText).fullName(csvRecord[0]).build();
        City city = City.builder().
                name(csvRecord[2].replaceAll("^(смт)|(с\\. )|(м\\. )|(с-ще )", ""))
                .fullName(csvRecord[2]).build();
        String index = csvRecord[3];
        String streetName = csvRecord[4].replaceAll("^(просп\\. )|(пл\\. )|(вул\\. )|(пров\\. )|(бульв\\. )|(вулиця )|" +
                "(Вулиця)|(Проспект )|(Провулок )|(Площа )|(площа )|(тупік )|(Тупік )|(Бульвар )|(бульвар )|" +
                "(Проїзд )|(проїзд )|(парк )", "");
        Street street = Street.builder().name(streetName).fullName(csvRecord[4]).index(index).build();
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
