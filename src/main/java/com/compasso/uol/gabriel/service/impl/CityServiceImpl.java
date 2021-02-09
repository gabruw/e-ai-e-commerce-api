package com.compasso.uol.gabriel.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.compasso.uol.gabriel.dto.OptionDTO;
import com.compasso.uol.gabriel.dto.city.ReturnCityDTO;
import com.compasso.uol.gabriel.entity.City;
import com.compasso.uol.gabriel.entity.State;
import com.compasso.uol.gabriel.repository.CityRepository;
import com.compasso.uol.gabriel.repository.StateRepository;
import com.compasso.uol.gabriel.service.CityService;

@Service
public class CityServiceImpl implements CityService {
	private static final Logger log = LoggerFactory.getLogger(CityServiceImpl.class);

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private CityRepository cityRepository;

	@Override
	public Page<ReturnCityDTO> findAll(PageRequest pageRequest) {
		log.info("Buscando todas as cidades.");

		Page<City> cities = this.cityRepository.findAll(pageRequest);
		return cities.map(city -> {
			ReturnCityDTO returnCity = mapper.map(city, ReturnCityDTO.class);
			returnCity.getState().setCities(null);

			return returnCity;
		});
	}

	@Override
	public Optional<City> findById(Long id) {
		log.info("Buscando uma cidade pelo Id: {}", id);
		return this.cityRepository.findById(id);
	}

	@Override
	public Optional<City> findByName(String name) {
		log.info("Buscando uma cidade pelo nome: {}", name);
		return this.cityRepository.findByName(name);
	}

	@Override
	public List<OptionDTO<Long>> findOptions(Long idState) {
		log.info("Buscando todas as opções de cidades ");

		List<OptionDTO<Long>> options = new ArrayList<OptionDTO<Long>>();
		Optional<State> optState = this.stateRepository.findById(idState);
		if (!optState.isPresent()) {
			return options;
		}

		optState.get().getCities().stream().forEach(city -> {
			OptionDTO<Long> optionDTO = new OptionDTO<Long>();
			optionDTO.setValue(city.getId());
			optionDTO.setText(city.getName());

			options.add(optionDTO);
		});
		
		return options;
	}

	@Override
	public City persist(City city) {
		log.info("Persistindo cidade: {}", city);
		return this.cityRepository.save(city);
	}

	@Override
	public void deleteById(Long id) {
		log.info("Removendo uma cidade pelo Id: {}", id);
		this.cityRepository.deleteById(id);
	}
}
