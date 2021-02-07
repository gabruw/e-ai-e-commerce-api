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

import com.compasso.uol.gabriel.dto.address.ReturnAddressDTO;
import com.compasso.uol.gabriel.entity.Address;
import com.compasso.uol.gabriel.entity.City;
import com.compasso.uol.gabriel.entity.State;
import com.compasso.uol.gabriel.repository.AddressRepository;

@SpringBootTest
@ActiveProfiles("test")
public class AddressServiceTest {

	@MockBean
	private AddressRepository addressRepository;

	@Autowired
	private AddressService addressService;

	@Mock
	private City city;

	@Mock
	private State state;

	@Mock
	private Address address;

	private static final Long ID = 1L;
	private static final String CITY = "Juiz de Fora";

	private static final String COUNTRY = "Brasil";
	private static final String STATE = "Minas Gerais";

	private static final Integer NUMBER = 258;
	private static final String CEP = "36010-070";
	private static final String NEIGHBORHOOD = "Centro";
	private static final String ROAD = "Rua Santa Rita";
	private static final String COMPLEMENT = "Ao lado da loja de moveis";

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
		city.setId(ID);
		city.setName(CITY);

		city.setState(state);

		address = new Address();
		address.setCep(CEP);
		address.setRoad(ROAD);
		address.setNumber(NUMBER);
		address.setComplement(COMPLEMENT);
		address.setNeighborhood(NEIGHBORHOOD);

		address.setCity(city);
	}

	@Test
	public void findAllAddresses() {
		List<Address> addresses = new ArrayList<Address>();
		addresses.add(address);

		Page<Address> pageAddresses = new PageImpl<Address>(addresses);
		PageRequest pageRequest = PageRequest.of(PAGE, PAGE_SIZE, Direction.valueOf(DIRECTION), ORDER);

		when(this.addressRepository.findAll(pageRequest)).thenReturn(pageAddresses);

		Page<ReturnAddressDTO> tAddresses = this.addressService.findAll(pageRequest);
		Assertions.assertFalse(tAddresses.isEmpty());
	}

	@Test
	public void findAddressById() {
		address.setId(ID);
		when(this.addressRepository.findById(ID)).thenReturn(Optional.of(address));

		Optional<Address> tAddresses = this.addressService.findById(ID);
		Assertions.assertEquals(tAddresses.get(), address);
	}

	@Test
	public void findAddressByCep() {
		address.setId(ID);

		List<Address> addresses = new ArrayList<Address>();
		addresses.add(address);

		Page<Address> pageAddresses = new PageImpl<Address>(addresses);
		PageRequest pageRequest = PageRequest.of(PAGE, PAGE_SIZE, Direction.valueOf(DIRECTION), ORDER);

		when(this.addressRepository.findByCep(CEP, pageRequest)).thenReturn(pageAddresses);

		Page<Address> tAddresses = this.addressService.findByCep(CEP, pageRequest);
		Assertions.assertEquals(tAddresses, pageAddresses);
	}

	@Test
	public void persistAddress() {
		when(this.addressRepository.save(address)).thenReturn(address);

		Address tAddress = this.addressService.persist(address);
		Assertions.assertEquals(tAddress, address);
	}

	@Test
	public void deleteAddress() {
		when(this.addressRepository.findById(ID)).thenReturn(Optional.of(address));

		this.addressService.deleteById(ID);
		verify(this.addressRepository, times(1)).deleteById(ID);
	}
}
