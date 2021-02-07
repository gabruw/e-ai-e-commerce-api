package com.compasso.uol.gabriel.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
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

import com.compasso.uol.gabriel.dto.client.ReturnClientDTO;
import com.compasso.uol.gabriel.entity.Address;
import com.compasso.uol.gabriel.entity.Authentication;
import com.compasso.uol.gabriel.entity.City;
import com.compasso.uol.gabriel.entity.Client;
import com.compasso.uol.gabriel.entity.State;
import com.compasso.uol.gabriel.enumerator.GenderEnum;
import com.compasso.uol.gabriel.enumerator.RoleEnum;
import com.compasso.uol.gabriel.repository.ClientRepository;

@SpringBootTest
@ActiveProfiles("test")
public class ClientServiceTest {

	@MockBean
	private ClientRepository clientRepository;

	@Autowired
	private ClientService clientService;

	@Mock
	private City city;

	@Mock
	private State state;

	@Mock
	private Client client;

	@Mock
	private Address address;

	@Mock
	private Authentication auth;

	private static final Long ID = 1L;

	private static final RoleEnum ROLE = RoleEnum.USER;
	private static final String PASSWORD = "teste@123";
	private static final String EMAIL = "genisvaldo@test.com";

	private static final String COUNTRY = "Brasil";
	private static final String CITY = "Juiz de Fora";
	private static final String STATE = "Minas Gerais";

	private static final Integer NUMBER = 258;
	private static final String CEP = "36010-070";
	private static final String NEIGHBORHOOD = "Centro";
	private static final String ROAD = "Rua Santa Rita";
	private static final String COMPLEMENT = "Ao lado da loja de moveis";

	private static final Date BIRTH = new Date();
	private static final String NAME = "Genisvaldo";
	private static final String CPF = "123.266.176-73";
	private static final GenderEnum GENDER = GenderEnum.MALE;

	private static final Integer PAGE = 0;
	private static final String ORDER = "id";
	private static final Integer PAGE_SIZE = 8;
	private static final String DIRECTION = "DESC";

	@BeforeEach
	public void setup() {
		auth = new Authentication();
		auth.setRole(ROLE);
		auth.setEmail(EMAIL);
		auth.setPassword(PASSWORD);

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

		client = new Client();
		client.setBirth(BIRTH);
		client.setCpf(CPF);
		client.setGender(GENDER);
		client.setName(NAME);

		List<Address> addresses = new ArrayList<>();
		addresses.add(address);

		client.setAddresses(addresses);
		client.setAuthentication(auth);
	}

	@Test
	public void findAllClients() {
		List<Client> clients = new ArrayList<Client>();
		clients.add(client);

		Page<Client> pageClients = new PageImpl<Client>(clients);
		PageRequest pageRequest = PageRequest.of(PAGE, PAGE_SIZE, Direction.valueOf(DIRECTION), ORDER);

		when(this.clientRepository.findAll(pageRequest)).thenReturn(pageClients);

		Page<ReturnClientDTO> tClients = this.clientService.findAll(pageRequest);
		Assertions.assertFalse(tClients.isEmpty());
	}

	@Test
	public void findClientById() {
		client.setId(ID);
		when(this.clientRepository.findById(ID)).thenReturn(Optional.of(client));

		Optional<Client> tClients = this.clientService.findById(ID);
		Assertions.assertEquals(tClients.get(), client);
	}

	@Test
	public void findClientByCpf() {
		client.setId(ID);
		when(this.clientRepository.findByCpf(CPF)).thenReturn(Optional.of(client));

		Optional<Client> tClient = this.clientService.findByCpf(CPF);
		Assertions.assertEquals(tClient.get(), client);
	}

	@Test
	public void persistClient() {
		when(this.clientRepository.save(client)).thenReturn(client);

		Client tClient = this.clientService.persist(client);
		Assertions.assertEquals(tClient, client);
	}

	@Test
	public void deleteClient() {
		when(this.clientRepository.findById(ID)).thenReturn(Optional.of(client));

		this.clientService.deleteById(ID);
		verify(this.clientRepository, times(1)).deleteById(ID);
	}
}
