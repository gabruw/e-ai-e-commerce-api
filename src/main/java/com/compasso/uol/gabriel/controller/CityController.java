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
import com.compasso.uol.gabriel.dto.city.EditCityDTO;
import com.compasso.uol.gabriel.dto.city.IncludeCityDTO;
import com.compasso.uol.gabriel.dto.city.ReturnCityDTO;
import com.compasso.uol.gabriel.entity.City;
import com.compasso.uol.gabriel.enumerator.message.CityMessage;
import com.compasso.uol.gabriel.enumerator.message.CommomMessage;
import com.compasso.uol.gabriel.response.Response;
import com.compasso.uol.gabriel.service.CityService;
import com.compasso.uol.gabriel.utils.Messages;

import io.swagger.annotations.ApiOperation;
import lombok.NoArgsConstructor;

@RestController
@NoArgsConstructor
@RequestMapping("/city")
public class CityController {
	private static final Logger log = LoggerFactory.getLogger(CityController.class);

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CityService cityService;

	@Cacheable("city")
	@GetMapping("/find-all")
	@ApiOperation(value = "Retorna todas as cidades cadastradas.")
	public ResponseEntity<Response<List<ReturnCityDTO>>> findAll() throws NoSuchAlgorithmException {
		log.info("Buscando todas as cidades.");
		Response<List<ReturnCityDTO>> response = new Response<List<ReturnCityDTO>>();

		List<ReturnCityDTO> cities = this.cityService.findAll();
		response.setData(cities);

		return ResponseEntity.ok(response);
	}

	@Cacheable("city")
	@GetMapping("/options")
	@ApiOperation(value = "Retorna todas as cidades no formato de opções.")
	public ResponseEntity<Response<List<OptionDTO<Long>>>> findOptions() throws NoSuchAlgorithmException {
		log.info("Buscando as opções das cidades.");
		Response<List<OptionDTO<Long>>> response = new Response<List<OptionDTO<Long>>>();

		List<OptionDTO<Long>> cities = this.cityService.findOptions();
		response.setData(cities);

		return ResponseEntity.ok(response);
	}

	@Cacheable("city")
	@ApiOperation(value = "Retorna uma cidade pelo nome.")
	@RequestMapping(value="find-name", params = "name", method = RequestMethod.GET)
	public ResponseEntity<Response<ReturnCityDTO>> findName(@RequestParam String name) throws NoSuchAlgorithmException {
		log.info("Buscando a cidade com o nome: {}", name);
		Response<ReturnCityDTO> response = new Response<ReturnCityDTO>();

		Optional<City> cityOpt = this.cityService.findByName(name);
		if (!cityOpt.isPresent()) {
			log.error("A cidade não foi encontrada com o nome recebido: {}", name);
			response.addError(Messages.getCity(CommomMessage.NONEXISTENT.toString()));

			return ResponseEntity.badRequest().body(response);
		}

		ReturnCityDTO returnCity = mapper.map(cityOpt.get(), ReturnCityDTO.class);
		response.setData(returnCity);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/include")
	@ApiOperation(value = "Inclui uma cidade.")
	public ResponseEntity<Response<ReturnCityDTO>> include(@Valid @RequestBody IncludeCityDTO cityDTO,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Incluindo a cidade: {}", cityDTO.toString());
		Response<ReturnCityDTO> response = new Response<ReturnCityDTO>();

		if (result.hasErrors()) {
			log.error("Erro validando dados para cadastro do estado: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.addFieldError(error.getDefaultMessage()));

			return ResponseEntity.badRequest().body(response);
		}

		String name = cityDTO.getName();
		Optional<City> cityOpt = this.cityService.findByName(name);
		if (cityOpt.isPresent()) {
			log.error("O nome já pertence a outra cidade: {}", name);
			response.addError(Messages.getCity(CityMessage.ALREADYEXISTSNAME.toString()));

			return ResponseEntity.badRequest().body(response);
		}

		City city = mapper.map(cityDTO, City.class);
		this.cityService.persist(city);

		ReturnCityDTO returnCity = mapper.map(city, ReturnCityDTO.class);
		response.setData(returnCity);

		return ResponseEntity.ok(response);
	}

	@PutMapping("/edit")
	@ApiOperation(value = "Atualiza os dados cadastrais de uma cidade.")
	public ResponseEntity<Response<ReturnCityDTO>> edit(@Valid @RequestBody EditCityDTO cityDTO, BindingResult result)
			throws NoSuchAlgorithmException {
		log.info("Editando a cidade: {}", cityDTO.toString());
		Response<ReturnCityDTO> response = new Response<ReturnCityDTO>();

		if (result.hasErrors()) {
			log.error("Erro validando dados para edição do estado: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.addFieldError(error.getDefaultMessage()));

			return ResponseEntity.badRequest().body(response);
		}

		String name = cityDTO.getName();
		Optional<City> cityOpt = this.cityService.findByName(name);
		if (!cityOpt.isPresent()) {
			log.error("O nome já pertence a outra cidade: {}", name);
			response.addError(Messages.getCity(CityMessage.ALREADYEXISTSNAME.toString()));

			return ResponseEntity.badRequest().body(response);
		}

		City city = mapper.map(cityDTO, City.class);
		this.cityService.persist(city);

		ReturnCityDTO returnCity = mapper.map(city, ReturnCityDTO.class);
		response.setData(returnCity);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/remove")
	@ApiOperation(value = "Remove a cidade pelo Id.")
	public ResponseEntity<Response<ReturnCityDTO>> remove(@RequestParam("id") Long id) throws NoSuchAlgorithmException {
		log.info("Removendo a cidade: {}", id);
		Response<ReturnCityDTO> response = new Response<ReturnCityDTO>();

		Optional<City> cityOpt = this.cityService.findById(id);
		if (!cityOpt.isPresent()) {
			log.info("A cidade não foi encontrada com o Id recebido: {}", id);
			response.addError(Messages.getCity(CommomMessage.NONEXISTENT.toString()));

			return ResponseEntity.badRequest().body(response);
		}

		this.cityService.deleteById(id);

		ReturnCityDTO returnCity = mapper.map(cityOpt.get(), ReturnCityDTO.class);
		response.setData(returnCity);

		return ResponseEntity.ok(response);
	}
}
