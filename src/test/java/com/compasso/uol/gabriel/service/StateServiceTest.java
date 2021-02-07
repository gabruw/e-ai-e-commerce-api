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
import com.compasso.uol.gabriel.dto.state.ReturnStateDTO;
import com.compasso.uol.gabriel.entity.State;
import com.compasso.uol.gabriel.repository.StateRepository;

@SpringBootTest
@ActiveProfiles("test")
public class StateServiceTest {

	@MockBean
	private StateRepository stateRepository;

	@Autowired
	private StateService stateService;

	@Mock
	private State state;

	private static final Long ID = 1L;
	private static final String COUNTRY = "Brasil";
	private static final String NAME = "Minas Gerais";

	private static final Integer PAGE = 0;
	private static final String ORDER = "id";
	private static final Integer PAGE_SIZE = 8;
	private static final String DIRECTION = "DESC";

	@BeforeEach
	public void setup() {
		state = new State();
		state.setName(NAME);
		state.setCountry(COUNTRY);
	}

	@Test
	public void findAllStates() {
		List<State> states = new ArrayList<State>();
		states.add(state);

		Page<State> pageStates = new PageImpl<State>(states);
		PageRequest pageRequest = PageRequest.of(PAGE, PAGE_SIZE, Direction.valueOf(DIRECTION), ORDER);

		when(this.stateRepository.findAll(pageRequest)).thenReturn(pageStates);

		Page<ReturnStateDTO> tStates = this.stateService.findAll(pageRequest);
		Assertions.assertFalse(tStates.isEmpty());
	}

	@Test
	public void findStateById() {
		state.setId(ID);
		when(this.stateRepository.findById(ID)).thenReturn(Optional.of(state));

		Optional<State> tState = this.stateService.findById(ID);
		Assertions.assertEquals(tState.get(), state);
	}

	@Test
	public void findStateByName() {
		when(this.stateRepository.findByName(NAME)).thenReturn(Optional.of(state));

		Optional<State> tState = this.stateService.findByName(NAME);
		Assertions.assertEquals(tState.get(), state);
	}

	@Test
	public void findStatesOptions() {
		state.setId(ID);

		List<State> states = new ArrayList<State>();
		states.add(state);

		when(this.stateRepository.findAll()).thenReturn(states);

		List<OptionDTO<Long>> options = this.stateService.findOptions();
		OptionDTO<Long> option = options.stream().findFirst().get();
		Assertions.assertTrue(option.getValue() == ID && option.getText().equals(NAME));
	}

	@Test
	public void persistState() {
		when(this.stateRepository.save(state)).thenReturn(state);

		State tState = this.stateService.persist(state);
		Assertions.assertEquals(tState, state);
	}

	@Test
	public void deleteState() {
		when(this.stateRepository.findById(ID)).thenReturn(Optional.of(state));

		this.stateService.deleteById(ID);
		verify(this.stateRepository, times(1)).deleteById(ID);
	}
}
