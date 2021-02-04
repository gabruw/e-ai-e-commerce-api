package com.compasso.uol.gabriel.dto.address;

import java.io.Serializable;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IncludeAddressDTO implements Serializable {
	private static final long serialVersionUID = 9035185421614865764L;

	@Size(min = 1, max = 11, message = "O campo 'Id da Cidade' deve conter entre 1 e 11 caracteres.")
	private Long idCity;

	@Size(min = 9, max = 9, message = "O campo 'CEP' deve conter 9 caracteres.")
	private String cep;

	@Size(min = 1, max = 255, message = "O campo 'Rua' deve conter entre 1 e 255 caracteres.")
	private String road;

	@Size(min = 1, max = 255, message = "O campo 'Bairro' deve conter entre 1 e 255 caracteres.")
	private String neighborhood;

	@Size(min = 1, max = 8, message = "O campo 'Número' deve conter entre 1 e 8 caracteres.")
	private Integer number;

	@Size(min = 1, max = 255, message = "O campo 'Complemento' deve conter entre 1 e 255 caracteres.")
	private String complement;

	@Override
	public String toString() {
		return "IncludeAddressDTO [idCity=" + idCity + ", cep=" + cep + ", road=" + road + ", neighborhood="
				+ neighborhood + ", number=" + number + ", complement=" + complement + "]";
	}
}
