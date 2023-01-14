package softuni.exam.constants;

import java.nio.file.Path;

public enum Paths {
    ;

    public final static Path TOWNS_JSON =
            Path.of("src/main/resources/files/json/towns.json");

    public final static Path AGENTS_JSON =
            Path.of("src/main/resources/files/json/agents.json");

    public final static Path APARTMENTS_XML =
            Path.of("src/main/resources/files/xml/apartments.xml");

    public final static Path OFFERS_XML =
            Path.of("src/main/resources/files/xml/offers.xml");

}
