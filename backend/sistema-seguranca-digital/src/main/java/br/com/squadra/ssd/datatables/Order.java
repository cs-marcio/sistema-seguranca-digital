package br.com.squadra.ssd.datatables;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @EqualsAndHashCode
public class Order implements Serializable {
	private static final long serialVersionUID = 2769433387886805086L;
	
	private int column;
	private String dir;
}