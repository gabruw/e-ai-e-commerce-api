package com.compasso.uol.gabriel.controller;

import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.compasso.uol.gabriel.dto.address.ReturnAddressDTO;
import com.compasso.uol.gabriel.entity.Address;
import com.compasso.uol.gabriel.response.Response;
import com.compasso.uol.gabriel.service.AddressService;

import io.swagger.annotations.ApiOperation;
import lombok.NoArgsConstructor;

@RestController
@NoArgsConstructor
@RequestMapping("/address")
public class AddressController {
	private static final Logger log = LoggerFactory.getLogger(AddressController.class);

	@Autowired
	private AddressService addressService;

	@Value("${page.size}")
	private int PAGE_SIZE;

	@Cacheable("address")
	@GetMapping("/find-all")
	@ApiOperation(value = "Retorna todos os endereços cadastrados.")
	public ResponseEntity<Response<Page<ReturnAddressDTO>>> findAll(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "order", defaultValue = "id") String order,
			@RequestParam(value = "direction", defaultValue = "DESC") String direction)
			throws NoSuchAlgorithmException {
		log.info("Buscando todas os endereços.");
		Response<Page<ReturnAddressDTO>> response = new Response<Page<ReturnAddressDTO>>();

		PageRequest pageRequest = PageRequest.of(page, this.PAGE_SIZE, Direction.valueOf(direction), order);
		Page<ReturnAddressDTO> addresses = this.addressService.findAll(pageRequest);

		response.setData(addresses);
		return ResponseEntity.ok(response);
	}

	@Cacheable("address")
	@GetMapping(value = "/find-cep", params = "cep")
	@ApiOperation(value = "Retorna todos os endereços cadastrados com o CEP informado.")
	public ResponseEntity<Response<Page<Address>>> findCep(@RequestParam(value = "cep", required = true) String cep,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "order", defaultValue = "id") String order,
			@RequestParam(value = "direction", defaultValue = "DESC") String direction)
			throws NoSuchAlgorithmException {
		log.info("Buscando todos os endereços com o CEP: {}", cep);
		Response<Page<Address>> response = new Response<Page<Address>>();

		PageRequest pageRequest = PageRequest.of(page, this.PAGE_SIZE, Direction.valueOf(direction), order);
		Page<Address> addresses = this.addressService.findByCep(cep, pageRequest);

		response.setData(addresses);
		return ResponseEntity.ok(response);
	}
}
