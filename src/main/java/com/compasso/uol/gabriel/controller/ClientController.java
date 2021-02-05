package com.compasso.uol.gabriel.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.hibernate.validator.constraints.br.CPF;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.compasso.uol.gabriel.dto.address.EditAddressDTO;
import com.compasso.uol.gabriel.dto.address.IncludeAddressDTO;
import com.compasso.uol.gabriel.dto.client.EditClientDTO;
import com.compasso.uol.gabriel.dto.client.IncludeClientDTO;
import com.compasso.uol.gabriel.dto.client.ReturnClientDTO;
import com.compasso.uol.gabriel.entity.Address;
import com.compasso.uol.gabriel.entity.Authentication;
import com.compasso.uol.gabriel.entity.City;
import com.compasso.uol.gabriel.entity.Client;
import com.compasso.uol.gabriel.enumerator.message.AuthenticationMessage;
import com.compasso.uol.gabriel.enumerator.message.ClientMessage;
import com.compasso.uol.gabriel.enumerator.message.CommomMessage;
import com.compasso.uol.gabriel.response.Response;
import com.compasso.uol.gabriel.service.AddressService;
import com.compasso.uol.gabriel.service.AuthenticationService;
import com.compasso.uol.gabriel.service.CityService;
import com.compasso.uol.gabriel.service.ClientService;
import com.compasso.uol.gabriel.utils.Crypt;
import com.compasso.uol.gabriel.utils.Messages;

import io.swagger.annotations.ApiOperation;
import lombok.NoArgsConstructor;

@RestController
@NoArgsConstructor
@RequestMapping("/client")
public class ClientController {
	private static final Logger log = LoggerFactory.getLogger(ClientController.class);

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CityService cityService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private AddressService addressService;

	@Autowired
	private AuthenticationService authenticationService;

	@Cacheable("client")
	@GetMapping("/find-all")
	@ApiOperation(value = "Retorna todos os clientes cadastrados.")
	public ResponseEntity<Response<List<ReturnClientDTO>>> findAll() throws NoSuchAlgorithmException {
		log.info("Buscando todas as cidades.");
		Response<List<ReturnClientDTO>> response = new Response<List<ReturnClientDTO>>();

		List<ReturnClientDTO> clients = clientService.findAll();
		response.setData(clients);

		return ResponseEntity.ok(response);
	}

	@Cacheable("client")
	@GetMapping(value = "/find-cpf", params = "cpf")
	@ApiOperation(value = "Retorna um cliente por um CPF válido.")
	public ResponseEntity<Response<ReturnClientDTO>> findCpf(@RequestParam @CPF String cpf)
			throws NoSuchAlgorithmException {
		log.info("Buscando o cliente com o CPF: {}", cpf);
		Response<ReturnClientDTO> response = new Response<ReturnClientDTO>();

		Optional<Client> clientOpt = clientService.findByCpf(cpf);
		if (!clientOpt.isPresent()) {
			log.error("O cliente não foi encontrado com o CPF recebido: {}", cpf);
			response.addError(Messages.getClient(CommomMessage.NONEXISTENT.toString()));

			return ResponseEntity.badRequest().body(response);
		}

		ReturnClientDTO client = mapper.map(clientOpt.get(), ReturnClientDTO.class);
		response.setData(client);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/include")
	@ApiOperation(value = "Inclui um cliente.")
	public ResponseEntity<Response<Authentication>> include(@Valid @RequestBody IncludeClientDTO includeClientDTO,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Incluindo o cliente: {}", includeClientDTO.toString());
		Response<Authentication> response = new Response<Authentication>();

		if (result.hasErrors()) {
			log.error("Erro validando dados para cadastro do cliente: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.addFieldError(error.getDefaultMessage()));

			return ResponseEntity.badRequest().body(response);
		}

		String cpf = includeClientDTO.getCpf();
		Optional<Client> clientOpt = this.clientService.findByCpf(cpf);
		if (clientOpt.isPresent()) {
			log.error("O CPF já pertence a outro cliente: {}", cpf);
			response.addError(Messages.getClient(ClientMessage.ALREADYEXISTSCPF.toString()));

			return ResponseEntity.badRequest().body(response);
		}

		String email = includeClientDTO.getAuthentication().getEmail();
		Optional<Authentication> authOpt = this.authenticationService.findByEmail(email);
		if (authOpt.isPresent()) {
			log.error("O e-mail já pertence a outra autenticação: {}", email);
			response.addError(Messages.getAuthentication(AuthenticationMessage.ALREADYEXISTSEMAIL.toString()));

			return ResponseEntity.badRequest().body(response);
		}

		List<Address> addresses = new ArrayList<Address>();
		List<IncludeAddressDTO> addressesInclude = includeClientDTO.getAddresses();
		addressesInclude.stream().forEach(value -> {
			Optional<City> cityOpt = this.cityService.findById(value.getIdCity());
			if (!cityOpt.isPresent()) {
				log.error("Uma ou mais cidades informadas não foi encontrado com o Id recebido.");
				response.addError(Messages.getCity(CommomMessage.NONEXISTENT.toString()));
			}

			Address address = mapper.map(value, Address.class);
			address.setCity(mapper.map(cityOpt.get(), City.class));

			addresses.add(address);
		});

		if (response.hasErrors()) {
			return ResponseEntity.badRequest().body(response);
		}

		Client client = mapper.map(includeClientDTO, Client.class);
		client.setAddresses(null);

		String encodedPassword = Crypt.encode(includeClientDTO.getAuthentication().getPassword());
		client.getAuthentication().setPassword(encodedPassword);

		Authentication auth = mapper.map(client.getAuthentication(), Authentication.class);
		client.setAuthentication(null);
		auth.setClient(client);

		this.authenticationService.persist(auth);

		addresses.stream().forEach(value -> {
			value.setClient(auth.getClient());
			this.addressService.persist(value);
		});

		auth.setPassword(includeClientDTO.getAuthentication().getPassword());
		response.setData(auth);
		
		return ResponseEntity.ok(response);
	}

	@PutMapping("/edit")
	@ApiOperation(value = "Atualiza os dados cadastrais de um cliente.")
	public ResponseEntity<Response<Authentication>> edit(@Valid @RequestBody EditClientDTO editClientDTO,
			BindingResult result) throws NoSuchAlgorithmException {
		log.info("Editando o cliente: {}", editClientDTO.toString());
		Response<Authentication> response = new Response<Authentication>();

		if (result.hasErrors()) {
			log.error("Erro validando dados para edição do cliente: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.addFieldError(error.getDefaultMessage()));

			return ResponseEntity.badRequest().body(response);
		}

		Long idClient = editClientDTO.getId();
		Optional<Client> clientOpt = this.clientService.findById(idClient);
		if (!clientOpt.isPresent()) {
			log.error("O cliente não foi encontrado com o Id recebido: {}", idClient);
			response.addError(Messages.getClient(CommomMessage.NONEXISTENT.toString()));

			return ResponseEntity.badRequest().body(response);
		}

		String cpf = editClientDTO.getCpf();
		clientOpt = this.clientService.findByCpf(cpf);
		if (clientOpt.isPresent() && !clientOpt.get().getId().equals(idClient)) {
			log.error("O CPF já pertence a outro cliente: {}", cpf);
			response.addError(Messages.getClient(ClientMessage.ALREADYEXISTSCPF.toString()));

			return ResponseEntity.badRequest().body(response);
		}

		Long idAuth = editClientDTO.getAuthentication().getId();
		Optional<Authentication> authOpt = this.authenticationService.findById(idAuth);
		if (!authOpt.isPresent()) {
			log.error("A auteticação não foi encontrada com o Id recebido: {}", idAuth);
			response.addError(Messages.getAuthentication(CommomMessage.NONEXISTENT.toString()));

			return ResponseEntity.badRequest().body(response);
		}

		List<Address> addresses = new ArrayList<Address>();
		List<EditAddressDTO> addressesEdit = editClientDTO.getAddresses();
		addressesEdit.stream().forEach(value -> {
			Optional<Address> addressOpt = this.addressService.findById(value.getId());
			if (!addressOpt.isPresent()) {
				log.error("O endereço não foi encontrado com o Id recebido: {}", value.getId());
				response.addError(Messages.getCity(CommomMessage.NONEXISTENT.toString()));
			}

			Optional<City> cityOpt = this.cityService.findById(value.getIdCity());
			if (!cityOpt.isPresent()) {
				log.error("A cidade não foi encontrada com o Id recebido: {}", value.getIdCity());
				response.addError(Messages.getCity(CommomMessage.NONEXISTENT.toString()));
			}

			Address address = mapper.map(addressOpt.get(), Address.class);
			address.setCity(mapper.map(cityOpt.get(), City.class));

			addresses.add(address);
		});

		if (response.hasErrors()) {
			return ResponseEntity.badRequest().body(response);
		}

		Client client = mapper.map(editClientDTO, Client.class);
		client.setAddresses(null);

		String encodedPassword = Crypt.encode(editClientDTO.getAuthentication().getPassword());
		client.getAuthentication().setPassword(encodedPassword);

		Authentication auth = mapper.map(client.getAuthentication(), Authentication.class);
		client.setAuthentication(null);
		auth.setClient(client);

		this.authenticationService.persist(auth);

		addresses.stream().forEach(value -> {
			value.setClient(auth.getClient());
			this.addressService.persist(value);
		});

		auth.setPassword(editClientDTO.getAuthentication().getPassword());
		response.setData(auth);
		
		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "/remove", params = "id")
	@ApiOperation(value = "Remove o cliente pelo Id.")
	public ResponseEntity<Response<ReturnClientDTO>> remove(@RequestParam("id") Long id)
			throws NoSuchAlgorithmException {
		log.info("Removendo o cliente: {}", id);
		Response<ReturnClientDTO> response = new Response<ReturnClientDTO>();

		Optional<Client> clientOpt = this.clientService.findById(id);
		if (!clientOpt.isPresent()) {
			log.info("O cliente não foi encontrado com o Id recebido: {}", id);
			response.addError(Messages.getClient(CommomMessage.NONEXISTENT.toString()));

			return ResponseEntity.badRequest().body(response);
		}

		this.authenticationService.deleteById(clientOpt.get().getAuthentication().getId());
		this.clientService.deleteById(id);

		ReturnClientDTO client = mapper.map(clientOpt.get(), ReturnClientDTO.class);
		response.setData(client);

		return ResponseEntity.ok(response);
	}
}
