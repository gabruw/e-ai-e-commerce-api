package com.compasso.uol.gabriel.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

	@Cacheable("address")
	@GetMapping("/find-all")
	@ApiOperation(value = "Retorna todos os endereços cadastrados.")
	public ResponseEntity<Response<List<ReturnAddressDTO>>> findAll() throws NoSuchAlgorithmException {
		log.info("Buscando todas os endereços.");
		Response<List<ReturnAddressDTO>> response = new Response<List<ReturnAddressDTO>>();

		List<ReturnAddressDTO> addresses = this.addressService.findAll();
		response.setData(addresses);

		return ResponseEntity.ok(response);
	}

	@Cacheable("address")
	@GetMapping(value = "/find-cep", params = "cep")
	@ApiOperation(value = "Retorna todos os endereços cadastrados com o CEP informado.")
	public ResponseEntity<Response<List<Address>>> findCep(@RequestParam String cep) throws NoSuchAlgorithmException {
		log.info("Buscando todos os endereços com o CEP: {}", cep);
		Response<List<Address>> response = new Response<List<Address>>();

		List<Address> addresses = this.addressService.findByCep(cep);
		response.setData(addresses);

		return ResponseEntity.ok(response);
	}
}
