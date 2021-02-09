package com.compasso.uol.gabriel.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "address")
@Entity(name = "address")
public class Address implements Serializable {
	private static final long serialVersionUID = 3259874520308167531L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "cep", nullable = false)
	@Size(min = 9, max = 9, message = "O campo 'CEP' deve conter 9 caracteres.")
	private String cep;

	@Column(name = "road", nullable = false)
	@Size(min = 1, max = 255, message = "O campo 'Rua' deve conter entre 1 e 255 caracteres.")
	private String road;

	@Column(name = "neighborhood", nullable = false)
	@Size(min = 1, max = 255, message = "O campo 'Bairro' deve conter entre 1 e 255 caracteres.")
	private String neighborhood;

	@Column(name = "number", nullable = false)
	@NotNull(message = "O campo 'Número' é obrigatório.")
	private Integer number;

	@Column(name = "complement", nullable = true)
	@Size(max = 255, message = "O campo 'Complemento' deve conter entre 1 e 255 caracteres.")
	private String complement;

	@ManyToOne(fetch = FetchType.EAGER)
	private City city;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private Client client;

	@Override
	public String toString() {
		return "Address [id=" + id + ", cep=" + cep + ", road=" + road + ", number=" + number + ", complement="
				+ complement + "]";
	}
}
