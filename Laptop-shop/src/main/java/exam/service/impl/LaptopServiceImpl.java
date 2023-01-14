package exam.service.impl;

import com.google.gson.Gson;
import exam.model.dtos.laptop.LaptopExportDTO;
import exam.model.dtos.laptop.LaptopSeedDTO;
import exam.model.entities.Laptop;
import exam.model.entities.Shop;
import exam.repository.LaptopRepository;
import exam.service.LaptopService;
import exam.service.ShopService;
import exam.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static exam.constants.Messages.INVALID_LAPTOP;
import static exam.constants.Messages.SUCCESSFULLY_IMPORTED;
import static exam.constants.Paths.LAPTOPS_JSON;

@Service
public class LaptopServiceImpl implements LaptopService {

    private LaptopRepository laptopRepository;
    private ShopService shopService;
    private ModelMapper modelMapper;
    private Gson gson;
    private ValidationUtil validationUtil;

    public LaptopServiceImpl(LaptopRepository laptopRepository,
                             ShopService shopService,
                             ModelMapper modelMapper,
                             Gson gson,
                             ValidationUtil validationUtil) {
        this.laptopRepository = laptopRepository;
        this.shopService = shopService;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return laptopRepository.count() > 0;
    }

    @Override
    public String readLaptopsFileContent() throws IOException {
        return Files.readString(LAPTOPS_JSON);
    }

    @Override
    public String importLaptops() throws IOException {

        LaptopSeedDTO[] laptopSeedDTOs =
                gson.fromJson(readLaptopsFileContent(), LaptopSeedDTO[].class);

        return Arrays.stream(laptopSeedDTOs)
                .map(this::importLaptop)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importLaptop(LaptopSeedDTO laptopSeedDTO) {

        boolean isValid = validationUtil.isValid(laptopSeedDTO);
        Optional<Laptop> optionalLaptop = findByMacAddress(laptopSeedDTO.getMacAddress());
        Optional<Shop> optionalShop = shopService.findByName(laptopSeedDTO.getShop().getName());

        if(!isValid || optionalLaptop.isPresent() || optionalShop.isEmpty()){
            return INVALID_LAPTOP;
        }

        Laptop laptop = modelMapper.map(laptopSeedDTO, Laptop.class);
        laptop.setShop(optionalShop.get());
        laptopRepository.save(laptop);

        return SUCCESSFULLY_IMPORTED + laptop.info();
    }

    @Override
    public String exportBestLaptops() {

//        laptopRepository.exportBestLaptops()
//                .get().stream()
//                .map(laptop -> modelMapper.map(laptop, LaptopExportDTO.class))
//                .map(LaptopExportDTO::info)
//                .collect(Collectors.joining(System.lineSeparator()));

        return laptopRepository.exportBestLaptops2()
                .get().stream()
                .map(LaptopExportDTO::info)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public Optional<Laptop> findByMacAddress(String macAddress) {
        return laptopRepository.findByMacAddress(macAddress);
    }
}
