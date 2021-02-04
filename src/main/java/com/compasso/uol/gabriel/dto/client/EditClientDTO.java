package com.compasso.uol.gabriel.dto.client;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import com.compasso.uol.gabriel.dto.address.EditAddressDTO;
import com.compasso.uol.gabriel.dto.authentication.EditAuthenticationDTO;
import com.compasso.uol.gabriel.enumerator.GenderEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditClientDTO implements Serializable {
	private static final long serialVersionUID = 972196048494520957L;

	@NotNull(message = "O campo 'Id' é obrigatório.")
	private Long id;

	@Size(min = 1, max = 200, message = "O campo 'Nome' deve conter entre 1 e 200 caracteres.")
	private String name;

	@CPF
	@Size(min = 11, max = 11, message = "O campo 'CPF' deve conter 11 caracteres.")
	private String cpf;

	@Enumerated(EnumType.STRING)
	private GenderEnum gender;

	@NotNull(message = "O campo 'Data de Nascimento' é obrigatório.")
	private Date birth;

	@NotNull(message = "O dados da 'Endereço' são obrigatórios.")
	private EditAddressDTO address;

	@NotNull(message = "O dados da 'Autenticação' são obrigatórios.")
	private EditAuthenticationDTO authentication;

	@Override
	public String toString() {
		return "EditClientDTO [name=" + name + ", cpf=" + cpf + ", gender=" + gender + ", birth=" + birth + ", address="
				+ address + ", authentication=" + authentication + "]";
	}
}
