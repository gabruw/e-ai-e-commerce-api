package com.compasso.uol.gabriel.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.compasso.uol.gabriel.dto.OptionDTO;
import com.compasso.uol.gabriel.dto.state.ReturnStateDTO;
import com.compasso.uol.gabriel.entity.State;
import com.compasso.uol.gabriel.repository.StateRepository;
import com.compasso.uol.gabriel.service.StateService;

@Service
public class StateServiceImpl implements StateService {
	private static final Logger log = LoggerFactory.getLogger(StateServiceImpl.class);

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private StateRepository stateRepository;

	@Override
	public Page<ReturnStateDTO> findAll(PageRequest pageRequest) {
		log.info("Buscando todas os estados.");

		Page<State> states = this.stateRepository.findAll(pageRequest);
		return states.map(state -> mapper.map(state, ReturnStateDTO.class));
	}

	@Override
	public Optional<State> findById(Long id) {
		log.info("Buscando um estado pelo Id: {}", id);
		return this.stateRepository.findById(id);
	}

	@Override
	public Optional<State> findByName(String name) {
		log.info("Buscando um estado pelo nome: {}", name);
		return this.stateRepository.findByName(name);
	}

	@Override
	public List<OptionDTO<Long>> findOptions() {
		log.info("Buscando todas as opções de estados.");

		List<State> states = this.stateRepository.findAll();
		return states.stream().map(state -> {
			OptionDTO<Long> optionDTO = new OptionDTO<Long>();
			optionDTO.setValue(state.getId());
			optionDTO.setText(state.getName());

			return optionDTO;
		}).collect(Collectors.toList());
	}

	@Override
	public State persist(State state) {
		log.info("Persistindo estado: {}", state);
		return this.stateRepository.save(state);
	}

	@Override
	public void deleteById(Long id) {
		log.info("Removendo um estado pelo Id: {}", id);
		this.stateRepository.deleteById(id);
	}
}
