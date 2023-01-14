package exam.service.impl;

import exam.model.dtos.sown.TownSeedDTO;
import exam.model.dtos.sown.WrapperTownSeedDTO;
import exam.model.entities.Town;
import exam.repository.TownRepository;
import exam.service.TownService;
import exam.util.ValidationUtil;
import exam.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.stream.Collectors;

import static exam.constants.Messages.INVALID_TOWN;
import static exam.constants.Messages.SUCCESSFULLY_IMPORTED;
import static exam.constants.Paths.TOWNS_XML;

@Service
public class TownServiceImpl implements TownService {

    private TownRepository townRepository;
    private ModelMapper modelMapper;
    private XmlParser xmlParser;
    private ValidationUtil validationUtil;

    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper,
                           XmlParser xmlParser, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(TOWNS_XML);
    }

    @Override
    public String importTowns() throws JAXBException, IOException {

        WrapperTownSeedDTO townSeedDTOs =
                xmlParser.fromFile(WrapperTownSeedDTO.class, TOWNS_XML);

        return townSeedDTOs.getTowns().stream()
                .map(this::importTown)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public Optional<Town> findByName(String name) {
        return townRepository.findByName(name);
    }

    private String importTown(TownSeedDTO townSeedDTO) {

        boolean isValid = validationUtil.isValid(townSeedDTO);
        Optional<Town> optionalTown = findByName(townSeedDTO.getName());

        if(!isValid || optionalTown.isPresent()){
            return INVALID_TOWN;
        }

        Town town = modelMapper.map(townSeedDTO, Town.class);
        townRepository.save(town);

        return SUCCESSFULLY_IMPORTED + town.info();
    }


}
