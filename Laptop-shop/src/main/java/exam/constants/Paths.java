package exam.constants;

import java.nio.file.Path;

public enum Paths {
    ;

    public final static Path CUSTOMERS_JSON =
            Path.of("src/main/resources/files/json/customers.json");


    public final static Path LAPTOPS_JSON =
            Path.of("src/main/resources/files/json/laptops.json");

    public final static Path SHOPS_XML =
            Path.of("src/main/resources/files/xml/shops.xml");

    public final static Path TOWNS_XML =
            Path.of("src/main/resources/files/xml/towns.xml");
}
