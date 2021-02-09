package com.compasso.uol.gabriel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.compasso.uol.gabriel.dto.OptionDTO;
import com.compasso.uol.gabriel.dto.city.ReturnCityDTO;
import com.compasso.uol.gabriel.entity.City;

public interface CityService {
	Page<ReturnCityDTO> findAll(PageRequest pageRequest);

	Optional<City> findById(Long id);

	Optional<City> findByName(String name);

	List<OptionDTO<Long>> findOptions(Long idState);

	City persist(City city);

	void deleteById(Long id);
}
