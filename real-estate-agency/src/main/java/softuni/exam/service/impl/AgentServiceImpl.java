package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AgentSeedDTO;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.constants.Messages.INVALID_AGENT;
import static softuni.exam.constants.Messages.SUCCESSFULLY_IMPORTED;
import static softuni.exam.constants.Paths.AGENTS_JSON;

@Service
public class AgentServiceImpl implements AgentService {

    private AgentRepository agentRepository;
    private ModelMapper modelMapper;
    private Gson gson;
    private ValidationUtils validationUtils;

    private TownRepository townRepository;

    @Autowired
    public AgentServiceImpl(AgentRepository agentRepository, ModelMapper modelMapper,
                            Gson gson, ValidationUtils validationUtils, TownRepository townRepository) {
        this.agentRepository = agentRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.townRepository = townRepository;
    }

    @Override
    public boolean areImported() {
        return agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files.readString(AGENTS_JSON);
    }

    @Override
    public String importAgents() throws IOException {
        String json = readAgentsFromFile();
        AgentSeedDTO[] agentSeedDTOs = gson.fromJson(json, AgentSeedDTO[].class);

        return Arrays.stream(agentSeedDTOs)
                .map(this::importAgent)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private String importAgent(AgentSeedDTO agentSeedDTO) {

        boolean isValid = validationUtils.validate(agentSeedDTO);

        Optional<Agent> optionalAgent = agentRepository.findByFirstName(agentSeedDTO.getFirstName());
        Optional<Town> optionalTown = townRepository.findByTownName(agentSeedDTO.getTown());
        if(!isValid || optionalAgent.isPresent() || optionalTown.isEmpty()){
            return INVALID_AGENT;
        }

        Agent agent = modelMapper.map(agentSeedDTO, Agent.class);
        agent.setTown(optionalTown.get());
        agentRepository.save(agent);

        return SUCCESSFULLY_IMPORTED + agent.info();
    }
}
