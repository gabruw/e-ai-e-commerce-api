package com.compasso.uol.gabriel.dto.state;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EditStateDTO extends IncludeStateDTO implements Serializable {
	private static final long serialVersionUID = 2446354366753306180L;

	@NotNull(message = "O campo 'Id' é obrigatório.")
	private Long id;
}
