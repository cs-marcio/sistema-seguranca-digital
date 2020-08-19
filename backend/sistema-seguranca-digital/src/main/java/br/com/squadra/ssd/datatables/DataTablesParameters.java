package br.com.squadra.ssd.datatables;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @EqualsAndHashCode
public class DataTablesParameters implements Serializable{
	private static final long serialVersionUID = 526608343488360204L;

	private Columns[]	columns;
	private int			draw;
	private int			length;
	private Order[]		order;
	private Search		search;
	private int			start;
}