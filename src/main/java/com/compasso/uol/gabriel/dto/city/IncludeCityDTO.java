package com.compasso.uol.gabriel.dto.city;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IncludeCityDTO implements Serializable {
	private static final long serialVersionUID = 972196049084511157L;

	@NotNull(message = "O campo 'Id do Estado' é obrigatório.")
	private Long idState;

	@Size(min = 1, max = 70, message = "O campo 'Nome' deve conter entre 1 e 70 caracteres.")
	private String name;

	@Override
	public String toString() {
		return "IncludeCityDTO [idState=" + idState + ", name=" + name + "]";
	}
}
