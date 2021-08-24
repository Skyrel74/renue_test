package com.example.test;

import static java.lang.System.exit;

public class Airport {

    // Description of each field
    // https://notebook.community/amitkaps/multidim/notebooks/Air_Routes
    Integer id;
    String name;
    String city;
    String country;
    String iataCode;
    String icaoCode;
    Double latitude;
    Double longitude;
    Integer altitude;
    Double timezone;
    DST DST;
    String tz;
    String type;
    String source;

    public Airport(
            Integer id,
            String name,
            String city,
            String country,
            String iataCode,
            String icaoCode,
            Double latitude,
            Double longitude,
            Integer altitude,
            Double timezone,
            DST DST,
            String tz,
            String type,
            String source
    ) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.country = country;
        this.iataCode = iataCode;
        this.icaoCode = icaoCode;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.timezone = timezone;
        this.DST = DST;
        this.tz = tz;
        this.type = type;
        this.source = source;
    }

    public static int compareTo(Airport a1, Airport a2, Integer position) {
        Object field1 = a1.getFieldByPosition(position);
        Object field2 = a2.getFieldByPosition(position);
        if (field1.getClass() == Integer.class && field2.getClass() == Integer.class) {
            int int1 = (int) field1;
            int int2 = (int) field2;
            return Integer.compare(int1, int2);
        } else if (field1.getClass() == Double.class && field2.getClass() == Double.class) {
            double double1 = (double) field1;
            double double2 = (double) field2;
            return Double.compare(double1, double2);
        } else if (field1.getClass() == String.class && field2.getClass() == String.class) {
            String string1 = (String) field1;
            String string2 = (String) field2;
            return String.CASE_INSENSITIVE_ORDER.compare(string1, string2);
        }
        return 0;
    }

    public Object getFieldByPosition(Integer position) {
        switch (position) {
            case 0:
                return this.id;
            case 1:
                return this.name;
            case 2:
                return this.city;
            case 3:
                return this.country;
            case 4:
                return this.iataCode;
            case 5:
                return this.icaoCode;
            case 6:
                return this.latitude;
            case 7:
                return this.longitude;
            case 8:
                return this.altitude;
            case 9:
                return this.timezone;
            case 10:
                return this.DST;
            case 11:
                return this.tz;
            case 12:
                return this.type;
            case 13:
                return this.source;
            default:
                System.err.println("Обращение к несуществующему столбцу CSV файла");
                exit(-1);
                return null;
        }
    }

    @Override
    public String toString() {
        return "Название аэропорта: \"" + this.name + "\"" +
                "\n\tГород: " + this.city +
                "\n\tСтрана: " + this.country +
                "\n\tIATA: " + this.iataCode +
                "\n\tICAO: " + this.icaoCode +
                "\n\tШирота: " + this.latitude +
                "\n\tДолгота: " + this.longitude +
                "\n\tВысота: " + this.altitude +
                "\n\tЧасовой пояс(UTC): " + this.timezone +
                "\n\tПереход на летнее время: " + this.DST +
                "\n\tЧасовой пояс(tz): " + this.tz;
    }

    enum DST {
        E,
        A,
        S,
        O,
        Z,
        N,
        U
    }
}
