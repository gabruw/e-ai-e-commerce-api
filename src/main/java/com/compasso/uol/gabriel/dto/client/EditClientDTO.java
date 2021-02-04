package com.compasso.uol.gabriel.dto.client;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.compasso.uol.gabriel.dto.address.EditAddressDTO;
import com.compasso.uol.gabriel.dto.authentication.EditAuthenticationDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditClientDTO extends ClientDTO implements Serializable {
	private static final long serialVersionUID = 972196048494520957L;

	@NotNull(message = "O campo 'Id' é obrigatório.")
	private Long id;

	@NotNull(message = "O dados da 'Endereço' são obrigatórios.")
	private EditAddressDTO address;

	@NotNull(message = "O dados da 'Autenticação' são obrigatórios.")
	private EditAuthenticationDTO authentication;

	@Override
	public String toString() {
		return "IncludeClientDTO [name=" + this.getName() + ", cpf=" + this.getCpf() + ", gender=" + this.getGender()
				+ ", birth=" + this.getBirth() + ", address=" + address + ", authentication=" + authentication + "]";
	}
}
