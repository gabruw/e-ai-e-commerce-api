package com.compasso.uol.gabriel.dto.address;

import java.io.Serializable;

import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditAddressDTO extends IncludeAddressDTO implements Serializable {
	private static final long serialVersionUID = 1275133672696496940L;

	@Size(min = 1, max = 11, message = "O campo 'Id' deve conter entre 1 e 11 caracteres.")
	private Long id;
}
