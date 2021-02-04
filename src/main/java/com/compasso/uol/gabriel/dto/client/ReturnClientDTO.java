package com.compasso.uol.gabriel.dto.client;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;

import com.compasso.uol.gabriel.enumerator.GenderEnum;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReturnClientDTO implements Serializable {
	private static final long serialVersionUID = -4487488554624891683L;

	@Size(min = 1, max = 200, message = "O campo 'Nome' deve conter entre 1 e 200 caracteres.")
	private String name;

	@CPF
	@Size(min = 11, max = 11, message = "O campo 'CPF' deve conter 11 caracteres.")
	private String cpf;

	@Enumerated(EnumType.STRING)
	private GenderEnum gender;

	@NotNull(message = "O campo 'Data de Nascimento' é obrigatório.")
	private Date birth;

	@Override
	public String toString() {
		return "ReturnClientDTO [name=" + name + ", cpf=" + cpf + ", gender=" + gender + ", birth=" + birth + "]";
	}
}
