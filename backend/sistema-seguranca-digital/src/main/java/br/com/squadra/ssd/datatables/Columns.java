package br.com.squadra.ssd.datatables;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @EqualsAndHashCode
public class Columns implements Serializable {
	private static final long serialVersionUID = 5180348616170340751L;
	
	private String data;
	private String name;
	private boolean orderable;
	private Search search;
	private boolean searchable;
}