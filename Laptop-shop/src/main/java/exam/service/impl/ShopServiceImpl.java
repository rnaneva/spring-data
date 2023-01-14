package exam.service.impl;

import exam.model.dtos.shop.ShopSeedDTO;
import exam.model.dtos.shop.WrapperShopSeedDTO;
import exam.model.entities.Shop;
import exam.model.entities.Town;
import exam.repository.ShopRepository;
import exam.service.ShopService;
import exam.service.TownService;
import exam.util.ValidationUtil;
import exam.util.XmlParser;
import jdk.dynalink.StandardOperation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.stream.Collectors;

import static exam.constants.Messages.INVALID_SHOP;
import static exam.constants.Messages.SUCCESSFULLY_IMPORTED;
import static exam.constants.Paths.SHOPS_XML;

@Service
public class ShopServiceImpl implements ShopService {

    private ShopRepository shopRepository;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;
    private XmlParser xmlParser;
    private TownService townService;

    public ShopServiceImpl(ShopRepository shopRepository, ModelMapper modelMapper,
                           ValidationUtil validationUtil, XmlParser xmlParser,
                           TownService townService) {
        this.shopRepository = shopRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.townService = townService;
    }

    @Override
    public boolean areImported() {
        return shopRepository.count() > 0;
    }

    @Override
    public String readShopsFileContent() throws IOException {
        return Files.readString(SHOPS_XML);
    }

    @Override
    public String importShops() throws JAXBException, FileNotFoundException {
        WrapperShopSeedDTO shopSeedDTOs =
                xmlParser.fromFile(WrapperShopSeedDTO.class, SHOPS_XML);

        return shopSeedDTOs.getShops().stream()
                .map(this::importShop)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importShop(ShopSeedDTO shopSeedDTO) {

        boolean isValid = validationUtil.isValid(shopSeedDTO);
        Optional<Town> optionalTown = townService.findByName(shopSeedDTO.getTownName().getTownName());
        Optional<Shop> optionalShop = shopRepository.findByName(shopSeedDTO.getName());

        if (!isValid || optionalShop.isPresent() || optionalTown.isEmpty()){
            return INVALID_SHOP;
        }

        Shop shop = modelMapper.map(shopSeedDTO, Shop.class);
        shop.setTown(optionalTown.get());
        shopRepository.save(shop);

        return SUCCESSFULLY_IMPORTED + shop.info();
    }

    @Override
    public Optional<Shop> findByName(String name) {
        return shopRepository.findByName(name);
    }
}
