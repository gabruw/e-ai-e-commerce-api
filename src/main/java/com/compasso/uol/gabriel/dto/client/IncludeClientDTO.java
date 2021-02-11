package com.compasso.uol.gabriel.dto.client;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.compasso.uol.gabriel.dto.address.IncludeAddressDTO;
import com.compasso.uol.gabriel.dto.authentication.IncludeAuthenticationDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IncludeClientDTO extends ClientDTO implements Serializable {
	private static final long serialVersionUID = 972196048494520957L;

	@NotNull(message = "O dados da 'Endereço' são obrigatórios.")
	private List<IncludeAddressDTO> addresses;

	@NotNull(message = "O dados da 'Autenticação' são obrigatórios.")
	private IncludeAuthenticationDTO authentication;

	@Override
	public String toString() {
		return "IncludeClientDTO [name=" + this.getName() + ", cpf=" + this.getCpf() + ", gender=" + this.getGender()
				+ ", birth=" + this.getBirth() + ", addresses=" + addresses + ", authentication=" + authentication + "]";
	}
}
