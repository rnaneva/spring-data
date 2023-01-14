package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.offer.AgentNameDTO;
import softuni.exam.models.dto.offer.BestOfferDTO;
import softuni.exam.models.dto.offer.OfferSeedDTO;
import softuni.exam.models.dto.offer.WrapperOfferSeedDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Offer;
import softuni.exam.models.entity.enums.ApartmentType;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XMLParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.INVALID_OFFER;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED;
import static softuni.exam.constants.Paths.OFFERS_XML;

@Service
public class OfferServiceImpl implements OfferService {

    private OfferRepository offerRepository;
    private AgentRepository agentRepository;
    private ApartmentRepository apartmentRepository;
    private ValidationUtils validationUtils;
    private ModelMapper modelMapper;
    private XMLParser xmlParser;

    public OfferServiceImpl(OfferRepository offerRepository, AgentRepository agentRepository,
                            ApartmentRepository apartmentRepository, ValidationUtils validationUtils,
                            ModelMapper modelMapper, XMLParser xmlParser) {
        this.offerRepository = offerRepository;
        this.agentRepository = agentRepository;
        this.apartmentRepository = apartmentRepository;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return offerRepository.count() >0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(OFFERS_XML);
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        WrapperOfferSeedDTO offerSeedDTOs =
                xmlParser.fromXMLFile(WrapperOfferSeedDTO.class, OFFERS_XML);

        return offerSeedDTOs.getOffers().stream()
                .map(this::importOffer)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importOffer(OfferSeedDTO offerSeedDTO) {

        boolean isValid = validationUtils.validate(offerSeedDTO);
        Optional<Agent> optionalAgent = agentRepository.findByFirstName(offerSeedDTO.getAgentName().getName());
        Optional<Apartment> optionalApartment = apartmentRepository.findById(offerSeedDTO.getApartmentId().getId());

        if(!isValid || optionalAgent.isEmpty() || optionalApartment.isEmpty()){
            return INVALID_OFFER;
        }

        Offer offer = modelMapper.map(offerSeedDTO, Offer.class);
        offer.setAgent(optionalAgent.get());
        offer.setApartment(optionalApartment.get());

        offerRepository.save(offer);

        return SUCCESSFULLY_IMPORTED + offer.info();
    }

    @Override
    public String exportOffers() {


        Optional<List<Offer>> offers = offerRepository.findByApartment_ApartmentTypeOrderByApartmentAreaDescPriceAsc(ApartmentType.three_rooms);

        if(offers.isEmpty()){
            return null;
        }


        return  offers.get().stream()
                .map(offer-> modelMapper.map(offer, BestOfferDTO.class))
                .map(BestOfferDTO::toString)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
