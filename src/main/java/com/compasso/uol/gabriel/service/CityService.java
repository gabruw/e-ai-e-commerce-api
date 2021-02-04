package com.compasso.uol.gabriel.service;

import java.util.List;
import java.util.Optional;

import com.compasso.uol.gabriel.dto.OptionDTO;
import com.compasso.uol.gabriel.dto.city.ReturnCityDTO;
import com.compasso.uol.gabriel.entity.City;

public interface CityService {
	List<ReturnCityDTO> findAll();

	Optional<City> findById(Long id);

	Optional<City> findByName(String name);

	List<OptionDTO<Long>> findOptions();

	City persist(City city);

	void deleteById(Long id);
}
