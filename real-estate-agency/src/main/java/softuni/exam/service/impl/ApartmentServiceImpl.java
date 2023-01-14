package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.apartment.ApartmentsSeedDTO;
import softuni.exam.models.dto.apartment.WrapperApartmentSeedDTO;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XMLParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.INVALID_APARTMENT;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED;
import static softuni.exam.constants.Paths.APARTMENTS_XML;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    private ApartmentRepository apartmentRepository;
    private ModelMapper modelMapper;
    private ValidationUtils validationUtils;
    private XMLParser xmlParser;

    private TownRepository townRepository;

    @Autowired
    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, ModelMapper modelMapper,
                                ValidationUtils validationUtils,
                                XMLParser xmlParser, TownRepository townRepository) {
        this.apartmentRepository = apartmentRepository;
        this.modelMapper = modelMapper;
        this.validationUtils = validationUtils;
        this.xmlParser = xmlParser;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(APARTMENTS_XML);
    }

    @Override
    public String importApartments() throws IOException, JAXBException {

        WrapperApartmentSeedDTO apartmentSeedDTOs =
                (WrapperApartmentSeedDTO)xmlParser.fromXMLFile(WrapperApartmentSeedDTO.class,APARTMENTS_XML );


        return apartmentSeedDTOs.getApartments().stream()
                .map(this::importApartment)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importApartment(ApartmentsSeedDTO apartmentsSeedDTO) {

        boolean isValid = validationUtils.validate(apartmentsSeedDTO);

        Optional<Apartment> optionalApartment = apartmentRepository
                .findByTown_TownNameAndArea(apartmentsSeedDTO.getTownName(), apartmentsSeedDTO.getArea());

        Optional<Town> optionalTown = townRepository.findByTownName(apartmentsSeedDTO.getTownName());

        if(!isValid || optionalApartment.isPresent() || optionalTown.isEmpty()){
            return INVALID_APARTMENT;
        }

        Apartment apartment = modelMapper.map(apartmentsSeedDTO, Apartment.class);
        apartment.setTown(optionalTown.get());
        apartmentRepository.save(apartment);

        return SUCCESSFULLY_IMPORTED + apartment.info();
    }
}
