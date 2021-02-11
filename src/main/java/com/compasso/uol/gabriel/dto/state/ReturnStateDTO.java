package com.compasso.uol.gabriel.dto.state;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReturnStateDTO implements Serializable {
	private static final long serialVersionUID = -1887788586324751684L;

	@NotNull(message = "O campo 'Id' é obrigatório.")
	private Long id;
	
	@Size(min = 1, max = 70, message = "O campo 'Nome' deve conter entre 1 e 70 caracteres.")
	private String name;

	@Size(min = 1, max = 70, message = "O campo 'Country' deve conter entre 1 e 70 caracteres.")
	private String country;

	@Override
	public String toString() {
		return "ReturnStateDTO [name=" + name + ", country=" + country + "]";
	}
}
