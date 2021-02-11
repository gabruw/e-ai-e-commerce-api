package com.compasso.uol.gabriel.dto.address;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.compasso.uol.gabriel.entity.City;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReturnAddressDTO implements Serializable {
	private static final long serialVersionUID = 1136111035449353768L;

	@NotNull(message = "O campo 'Id' é obrigatório.")
	private Long id;
	
	@Size(min = 9, max = 9, message = "O campo 'CEP' deve conter 9 caracteres.")
	private String cep;

	@Size(min = 1, max = 255, message = "O campo 'Rua' deve conter entre 1 e 255 caracteres.")
	private String road;

	@Size(min = 1, max = 255, message = "O campo 'Bairro' deve conter entre 1 e 255 caracteres.")
	private String neighborhood;

	@Size(min = 1, max = 8, message = "O campo 'Número' deve conter entre 1 e 8 caracteres.")
	private Integer number;

	@Size(max = 255, message = "O campo 'Complemento' deve conter no máximo 255 caracteres.")
	private String complement;

	@NotNull(message = "Os dados da 'Cidade' são obrigatórios.")
	private City city;

	@Override
	public String toString() {
		return "ReturnAddressDTO [cep=" + cep + ", road=" + road + ", neighborhood=" + neighborhood + ", number="
				+ number + ", complement=" + complement + ", city=" + city + "]";
	}
}
