package com.compasso.uol.gabriel.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "cep", nullable = false)
	@Size(min = 8, max = 8, message = "O campo 'CEP' deve conter 8 caracteres.")
	private Integer cep;

	@Column(name = "road", nullable = false)
	@Size(min = 1, max = 255, message = "O campo 'Rua' deve conter entre 1 e 255 caracteres.")
	private String road;

	@Column(name = "number", nullable = false)
	@Size(min = 1, max = 8, message = "O campo 'Número' deve conter entre 1 e 8 caracteres.")
	private Integer number;

	@Column(name = "complement", nullable = false)
	@Size(min = 1, max = 255, message = "O campo 'Complemento' deve conter entre 1 e 255 caracteres.")
	private String complement;

	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull(message = "Os dados da 'Cidade' são obrigatórios.")
	private City city;

	@OneToMany(mappedBy = "address", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Client> clients;

	@Override
	public String toString() {
		return "Address [id=" + id + ", cep=" + cep + ", road=" + road + ", number=" + number + ", complement="
				+ complement + ", city=" + city + ", clients=" + clients + "]";
	}
}
