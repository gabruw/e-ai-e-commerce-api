package com.compasso.uol.gabriel.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.compasso.uol.gabriel.dto.OptionDTO;
import com.compasso.uol.gabriel.dto.state.EditStateDTO;
import com.compasso.uol.gabriel.dto.state.IncludeStateDTO;
import com.compasso.uol.gabriel.dto.state.ReturnStateDTO;
import com.compasso.uol.gabriel.entity.State;
import com.compasso.uol.gabriel.enumerator.message.CommomMessage;
import com.compasso.uol.gabriel.enumerator.message.StateMessage;
import com.compasso.uol.gabriel.response.Response;
import com.compasso.uol.gabriel.service.StateService;
import com.compasso.uol.gabriel.utils.Messages;

import io.swagger.annotations.ApiOperation;
import lombok.NoArgsConstructor;

@RestController
@NoArgsConstructor
@RequestMapping("/state")
public class StateController {
	private static final Logger log = LoggerFactory.getLogger(StateController.class);

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private StateService stateService;

	@Cacheable("state")
	@GetMapping("/find-all")
	@ApiOperation(value = "Retorna todos os estados cadastrados.")
	public ResponseEntity<Response<List<ReturnStateDTO>>> findAll() throws NoSuchAlgorithmException {
		log.info("Buscando todas os estados.");
		Response<List<ReturnStateDTO>> response = new Response<List<ReturnStateDTO>>();

		List<ReturnStateDTO> states = this.stateService.findAll();
		response.setData(states);

		return ResponseEntity.ok(response);
	}

	@Cacheable("state")
	@GetMapping("/options")
	@ApiOperation(value = "Retorna todos os estados no formato de opções.")
	public ResponseEntity<Response<List<OptionDTO<Long>>>> findOptions() throws NoSuchAlgorithmException {
		log.info("Buscando as opções dos estados.");
		Response<List<OptionDTO<Long>>> response = new Response<List<OptionDTO<Long>>>();

		List<OptionDTO<Long>> states = this.stateService.findOptions();
		response.setData(states);

		return ResponseEntity.ok(response);
	}

	@Cacheable("state")
	@ApiOperation(value = "Retorna um estado pelo nome.")
	@RequestMapping(value="/find-name", params = "name", method = RequestMethod.GET)
	public ResponseEntity<Response<ReturnStateDTO>> findName(@RequestParam String name)
			throws NoSuchAlgorithmException {
		log.info("Buscando o estado com o nome: {}", name);
		Response<ReturnStateDTO> response = new Response<ReturnStateDTO>();

		Optional<State> stateOpt = this.stateService.findByName(name);
		if (!stateOpt.isPresent()) {
			log.error("O estado não foi encontrado com o nome recebido: {}", name);
			response.addError(Messages.getState(CommomMessage.NONEXISTENT.toString()));

			return ResponseEntity.badRequest().body(response);
		}

		ReturnStateDTO returnState = mapper.map(stateOpt.get(), ReturnStateDTO.class);
		response.setData(returnState);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/include")
	@ApiOperation(value = "Inclui um estado.")
	public ResponseEntity<Response<ReturnStateDTO>> include(@Valid @RequestBody IncludeStateDTO stateDTO,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Incluindo o estado: {}", stateDTO.toString());
		Response<ReturnStateDTO> response = new Response<ReturnStateDTO>();

		if (result.hasErrors()) {
			log.error("Erro validando dados para cadastro do estado: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.addFieldError(error.getDefaultMessage()));

			return ResponseEntity.badRequest().body(response);
		}

		String name = stateDTO.getName();
		Optional<State> stateOpt = this.stateService.findByName(name);
		if (stateOpt.isPresent()) {
			log.error("O nome já pertence a outro estado: {}", name);
			response.addError(Messages.getState(StateMessage.ALREADYEXISTSNAME.toString()));

			return ResponseEntity.badRequest().body(response);
		}

		State state = mapper.map(stateDTO, State.class);
		this.stateService.persist(state);

		ReturnStateDTO returnState = mapper.map(state, ReturnStateDTO.class);
		response.setData(returnState);

		return ResponseEntity.ok(response);
	}

	@PutMapping("/edit")
	@ApiOperation(value = "Atualiza os dados cadastrais de um estado.")
	public ResponseEntity<Response<ReturnStateDTO>> edit(@Valid @RequestBody EditStateDTO stateDTO,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Editando o estado: {}", stateDTO.toString());
		Response<ReturnStateDTO> response = new Response<ReturnStateDTO>();

		if (result.hasErrors()) {
			log.error("Erro validando dados para edição do estado: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.addFieldError(error.getDefaultMessage()));

			return ResponseEntity.badRequest().body(response);
		}

		String name = stateDTO.getName();
		Optional<State> stateOpt = this.stateService.findByName(name);
		if (!stateOpt.isPresent()) {
			log.error("O nome já pertence a outro estado: {}", name);
			response.addError(Messages.getState(StateMessage.ALREADYEXISTSNAME.toString()));

			return ResponseEntity.badRequest().body(response);
		}

		State state = mapper.map(stateDTO, State.class);
		this.stateService.persist(state);

		ReturnStateDTO returnState = mapper.map(state, ReturnStateDTO.class);
		response.setData(returnState);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/remove")
	@ApiOperation(value = "Remove o estado pelo Id.")
	public ResponseEntity<Response<ReturnStateDTO>> remove(@RequestParam("id") Long id)
			throws NoSuchAlgorithmException {
		log.info("Removendo o estado: {}", id);
		Response<ReturnStateDTO> response = new Response<ReturnStateDTO>();

		Optional<State> stateOpt = this.stateService.findById(id);
		if (!stateOpt.isPresent()) {
			log.info("O estado não foi encontrado com o Id recebido: {}", id);
			response.addError(Messages.getCity(CommomMessage.NONEXISTENT.toString()));

			return ResponseEntity.badRequest().body(response);
		}

		this.stateService.deleteById(id);

		ReturnStateDTO returnState = mapper.map(stateOpt.get(), ReturnStateDTO.class);
		response.setData(returnState);

		return ResponseEntity.ok(response);
	}
}
