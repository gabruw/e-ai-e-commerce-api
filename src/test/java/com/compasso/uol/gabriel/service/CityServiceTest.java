package com.compasso.uol.gabriel.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ActiveProfiles;

import com.compasso.uol.gabriel.dto.OptionDTO;
import com.compasso.uol.gabriel.dto.city.ReturnCityDTO;
import com.compasso.uol.gabriel.entity.City;
import com.compasso.uol.gabriel.entity.State;
import com.compasso.uol.gabriel.repository.CityRepository;
import com.compasso.uol.gabriel.repository.StateRepository;

@SpringBootTest
@ActiveProfiles("test")
public class CityServiceTest {

	@MockBean
	private StateRepository stateRepository;

	@MockBean
	private CityRepository cityRepository;

	@Autowired
	private CityService cityService;

	@Mock
	private City city;

	@Mock
	private State state;

	private static final Long ID = 1L;
	private static final String COUNTRY = "Brasil";
	private static final String CITY = "Juiz de Fora";
	private static final String STATE = "Minas Gerais";

	private static final Integer PAGE = 0;
	private static final String ORDER = "id";
	private static final Integer PAGE_SIZE = 8;
	private static final String DIRECTION = "DESC";

	@BeforeEach
	public void setup() {
		state = new State();
		state.setId(ID);
		state.setName(STATE);
		state.setCountry(COUNTRY);

		city = new City();
		city.setName(CITY);

		city.setState(state);
	}

	@Test
	public void findAllCities() {
		List<City> cities = new ArrayList<City>();
		cities.add(city);

		Page<City> pageCities = new PageImpl<City>(cities);
		PageRequest pageRequest = PageRequest.of(PAGE, PAGE_SIZE, Direction.valueOf(DIRECTION), ORDER);

		when(this.cityRepository.findAll(pageRequest)).thenReturn(pageCities);

		Page<ReturnCityDTO> tCities = this.cityService.findAll(pageRequest);
		Assertions.assertFalse(tCities.isEmpty());
	}

	@Test
	public void findCityById() {
		city.setId(ID);
		when(this.cityRepository.findById(ID)).thenReturn(Optional.of(city));

		Optional<City> tCity = this.cityService.findById(ID);
		Assertions.assertEquals(tCity.get(), city);
	}

	@Test
	public void findStateByName() {
		when(this.cityRepository.findByName(CITY)).thenReturn(Optional.of(city));

		Optional<City> tCity = this.cityService.findByName(CITY);
		Assertions.assertEquals(tCity.get(), city);
	}

	@Test
	public void findCitiesOptions() {
		city.setId(ID);

		List<City> cities = new ArrayList<City>();
		cities.add(city);

		state.setCities(cities);
		when(this.stateRepository.findById(ID)).thenReturn(Optional.of(state));

		List<OptionDTO<Long>> options = this.cityService.findOptions(ID);
		OptionDTO<Long> option = options.stream().findFirst().get();
		Assertions.assertTrue(option.getValue() == ID && option.getText().equals(CITY));
	}

	@Test
	public void persistCity() {
		when(this.cityRepository.save(city)).thenReturn(city);

		City tCity = this.cityService.persist(city);
		Assertions.assertEquals(tCity, city);
	}

	@Test
	public void deleteCity() {
		when(this.cityRepository.findById(ID)).thenReturn(Optional.of(city));

		this.cityService.deleteById(ID);
		verify(this.cityRepository, times(1)).deleteById(ID);
	}
}
