package com.example.test;

import com.example.test.Airport.DST;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.exit;

@SpringBootApplication
public class AirportSearch implements CommandLineRunner {

    @Value("${csv_row}")
    private Integer csvRow;

    public static void main(String[] args) {
        SpringApplication.run(AirportSearch.class, args);
    }

    @Override
    public void run(String... args) {

        if (args.length > 0) {
            try {
                csvRow = Integer.parseInt(args[0]);
                if (csvRow > 13 || csvRow < 0) {
                    System.err.println("Аргумент " + args[0] + " должен быть от 0 до 13");
                    exit(-1);
                }
            } catch (NumberFormatException e) {
                System.err.println("Аргумент " + args[0] + " должен быть целым числом");
                exit(-1);
            }
        }

        System.out.print("Введите строку: ");
        String search = new Scanner(System.in).nextLine();

        Instant start = Instant.now();
        List<Airport> airports = getAirports(search);
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        for (Airport a : airports)
            System.out.println(a.toString());

        System.out.println("Количество найденных строк: " + airports.size());
        System.out.println("Время, затраченное на поиск: " + timeElapsed + " мс.");
    }

    private List<Airport> getAirports(String search) {
        List<Airport> airports = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new FileReader("src/main/java/com/example/test/airports.dat"))) {
            while (csvReader.iterator().hasNext()) {
                Airport airport = getAirport(csvReader.readNext());
                if (airport == null)
                    continue;
                if (airport.getFieldByPosition(csvRow).toString().contains(search))
                    airports.add(airport);
            }
        } catch (IOException | CsvValidationException e) {
            System.err.println("Ошибка чтения файла");
            exit(-1);
        }

        sort(airports);
        return airports;
    }

    private Airport getAirport(String[] line) {
        // CSVReader read null value "\N" as String "N"
        // Also some enum value in 10th column has "N" value
        for (int i = 0; i < line.length - 1; ++i) {
            if (i == 10)
                continue;
            if (line[i].equals("N")) {
                return null;
            }
        }

        try {
            Integer id = Integer.parseInt(line[0]);
            String name = line[1];
            String city = line[2];
            String country = line[3];
            String iataCode = line[4];
            String icaoCode = line[5];
            Double latitude = Double.parseDouble(line[6]);
            Double longitude = Double.parseDouble(line[7]);
            Integer altitude = Integer.parseInt(line[8]);
            Double timezone = Double.parseDouble(line[9]);
            DST dst = DST.valueOf(line[10]);
            String tz = line[11];
            String type = line[12];
            String source = line[13];

            return new Airport(id, name, city, country, iataCode, icaoCode, latitude, longitude, altitude, timezone,
                    dst, tz, type, source);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private void sort(List<Airport> airports) {
        quicksort(airports, 0, airports.size() - 1);
    }

    private void quicksort(List<Airport> airports, int start, int end) {
        if (end > start) {
            int pi = partition(airports, start, end);
            quicksort(airports, start, pi - 1);
            quicksort(airports, pi + 1, end);
        }
    }

    private int partition(List<Airport> airports, int start, int end) {
        int i = start + 1;
        int j = end;

        while (i <= j) {
            if (Airport.compareTo(airports.get(i), airports.get(start), csvRow) <= 0)
                i++;
            else if (Airport.compareTo(airports.get(j), airports.get(start), csvRow) > 0)
                j--;
            else
                Collections.swap(airports, i, j);
        }
        Collections.swap(airports, start, j);
        return j;
    }
}
