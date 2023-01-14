package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TownSeedDTO;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.INVALID_TOWN;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED;
import static softuni.exam.constants.Paths.TOWNS_JSON;

@Service
public class TownServiceImpl implements TownService {

    private TownRepository townRepository;
    private ModelMapper modelMapper;
    private Gson gson;
    private ValidationUtils validationUtils;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper,
                           Gson gson, ValidationUtils validationUtils) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
    }

    @Override
    public boolean areImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files.readString(TOWNS_JSON);
    }

    @Override
    public String importTowns() throws IOException {
        String json = readTownsFileContent();

        TownSeedDTO[] townSeedDTOs = gson.fromJson(json, TownSeedDTO[].class);

        return Arrays.stream(townSeedDTOs)
                .map(this::importTown)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importTown(TownSeedDTO townSeedDTO) {

        boolean isValid = validationUtils.validate(townSeedDTO);
        Optional<Town> optionalTown = townRepository.findByTownName(townSeedDTO.getTownName());

        if(!isValid || optionalTown.isPresent()){
            return INVALID_TOWN;
        }

        Town town = modelMapper.map(townSeedDTO, Town.class);

        townRepository.save(town);

        return SUCCESSFULLY_IMPORTED + town.info();
    }
}
