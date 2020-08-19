package br.com.squadra.ssd.datatables;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class DataTableResult<T> {
	private String draw;

	private String recordsFiltered;

	private String recordsTotal;

	List<T> data;
}