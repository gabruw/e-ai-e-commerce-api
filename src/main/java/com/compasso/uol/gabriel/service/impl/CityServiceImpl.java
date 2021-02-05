package com.compasso.uol.gabriel.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compasso.uol.gabriel.dto.OptionDTO;
import com.compasso.uol.gabriel.dto.city.ReturnCityDTO;
import com.compasso.uol.gabriel.entity.City;
import com.compasso.uol.gabriel.repository.CityRepository;
import com.compasso.uol.gabriel.service.CityService;

@Service
public class CityServiceImpl implements CityService {
	private static final Logger log = LoggerFactory.getLogger(CityServiceImpl.class);

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CityRepository cityRepository;

	@Override
	public List<ReturnCityDTO> findAll() {
		log.info("Buscando todas as cidades.");

		List<City> cities = this.cityRepository.findAll();
		return cities.stream().map(city -> {
			ReturnCityDTO returnCity = mapper.map(city, ReturnCityDTO.class);
			returnCity.getState().setCities(null);

			return returnCity;
		}).collect(Collectors.toList());
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
	public List<OptionDTO<Long>> findOptions() {
		log.info("Buscando todas as opções de cidades ");

		List<City> cities = this.cityRepository.findAll();
		return cities.stream().map(city -> {
			OptionDTO<Long> optionDTO = new OptionDTO<Long>();
			optionDTO.setValue(city.getId());
			optionDTO.setText(city.getName());

			return optionDTO;
		}).collect(Collectors.toList());
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
