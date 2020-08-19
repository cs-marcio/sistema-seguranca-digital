package br.com.squadra.ssd.datatables;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @EqualsAndHashCode
public class Search implements Serializable {
	private static final long serialVersionUID = 7355102552984120932L;

	private String regex;
	private String value;
}