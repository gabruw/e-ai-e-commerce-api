package com.compasso.uol.gabriel.dto.address;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditAddressDTO extends IncludeAddressDTO implements Serializable {
	private static final long serialVersionUID = 1275133672696496940L;

	@NotNull(message = "O campo 'Id' é obrigatório.")
	private Long id;
}
