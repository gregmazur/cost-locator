package org.open.budget.costlocator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.open.budget.costlocator.entity.City;
import org.open.budget.costlocator.entity.District;
import org.open.budget.costlocator.entity.Region;
import org.open.budget.costlocator.entity.Street;
import org.open.budget.costlocator.service.StreetService;
import org.open.budget.costlocator.service.TenderService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Builder
@Slf4j
@AllArgsConstructor
public class StreetReaderCsv {

    public static String HOUSES_LOADING = "HOUSES_LOADING";

    private StreetService streetService;

    private TenderService tenderService;

    private String housesCsvName;

    public void start() {
        {
            String path = housesCsvName;
            tenderService.saveProp(HOUSES_LOADING, "true");

            try (
                    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "windows-1251"))
            ) {
                log.warn("ATTENTION---STARTED READ FROM HOUSES----------ATTENTION---------------ATTENTION---------------------");
                String line;
                boolean firstLine = true;
                List<Street> streets = new ArrayList<>();
                while ((line = br.readLine()) != null) {
                    if (firstLine) {
                        firstLine = false;
                        continue;
                    }

                    log.trace("Reading line");
                    String[] csvRecord = line.split(";");
                    Optional<Street> wrapper = getStreet(csvRecord);
                    if (!wrapper.isPresent())
                        continue;

                    streets.add(wrapper.get());
                    log.trace("Submitted for transaction");

                    if (streets.size() > 100) {
                        log.info("A portion sent to commit");
                        streetService.save(streets);
                        streets.clear();
                    }

                }
                streetService.save(streets);
                log.warn("------READ HAS ENDED------");
            } catch (IOException e) {
                e.printStackTrace();
            }
            File file = new File(path);
            file.delete();
            log.warn(path + " has been removed");
            tenderService.saveProp(HOUSES_LOADING, "false");
        }
    }

    Optional<Street> getStreet(String[] csvRecord) {
        if (csvRecord.length < 5)
            return Optional.empty();
        if (csvRecord[4].isEmpty())
            return Optional.empty();
        String regionFullName = csvRecord[0];
        if (regionFullName.equals("Київ"))
            regionFullName = regionFullName.replace("Київ", "Київська");

        Region region = Region.builder().name(regionFullName.toLowerCase().trim()).fullName(csvRecord[0]).build();
        String cityFullName = csvRecord[2];
        String cityName = cityFullName.replaceAll("^(смт)|(с\\. )|(м\\. )|(с-ще )", "").trim().toLowerCase();
        String districtFullName = csvRecord[1];
        String districtName = districtFullName.toLowerCase().trim();

        if (districtFullName.isEmpty()) {
            districtFullName = cityFullName;
            districtName = cityName;
        }

        District district = District.builder().fullName(districtFullName).name(districtName).region(region).build();


        City city = City.builder().district(district).name(cityName)
                .fullName(cityFullName).build();
        String index = csvRecord[3];
        String streetName = csvRecord[4].replaceAll("^(просп\\. )|(пл\\. )|(вул\\. )|(пров\\. )|(бульв\\. )|(вулиця )|" +
                "(Вулиця)|(Проспект )|(Провулок )|(Площа )|(площа )|(тупік )|(Тупік )|(Бульвар )|(бульвар )|" +
                "(Проїзд )|(проїзд )|(парк )", "").trim();
        Street street = Street.builder().name(streetName.toLowerCase()).fullName(csvRecord[4]).index(index).city(city).build();
        return Optional.of(street);
    }
}
