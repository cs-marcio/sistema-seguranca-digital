package br.com.squadra.ssd.datatables;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.*;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @EqualsAndHashCode
public class DataBuilder<T> {
    private T object;
    private DataTablesParameters parameters;
    private List<String> columnsNoSearchable = new ArrayList<>();

    public DataBuilder(T object, DataTablesParameters parameters) {
        this.object = object;
        this.parameters = parameters;
        if (parameters.getLength() == -1)
            parameters.setLength(50);
        this.setup();
    }

    private void setup() {
        DataTableUtility<T> reflectionUtility = new DataTableUtility<>(object);

        for (Columns column : this.parameters.getColumns()) {
            if (
                    !StringUtils.isEmpty(column.getData())
                    && column.isSearchable()
                    && (
                            !StringUtils.isEmpty(this.parameters.getSearch().getValue())
                            || !StringUtils.isEmpty(column.getSearch().getValue())
                    )
            ) {
                reflectionUtility.setValue(
                        column.getData(),
                        StringUtils.isEmpty(column.getSearch().getValue())
                                ? parameters.getSearch().getValue()
                                : column.getSearch().getValue()
                );
            } else if (!StringUtils.isEmpty(column.getData()))
                this.columnsNoSearchable.add(column.getData());
        }
        this.object = reflectionUtility.getObject();
    }

    public Example<T> getExample(String... IgnorePaths) {
        if (columnsNoSearchable.size() != parameters.getColumns().length)
            return Example.of(object,
                    ExampleMatcher.matchingAny().withIgnoreNullValues().withIgnoreCase()
                            .withIgnorePaths(ArrayUtils.addAll(IgnorePaths,
                                    columnsNoSearchable.toArray(new String[columnsNoSearchable.size()])))
                            .withStringMatcher(StringMatcher.CONTAINING));
        else
            return null;
    }

    public PageRequest getPageRequest() {
        List<Sort.Order> orderList = new ArrayList<>();
        for (Order order : parameters.getOrder()) {
            orderList.add(new Sort.Order(Sort.Direction.valueOf(order.getDir().toUpperCase()), parameters.getColumns()[order.getColumn()].getData()));
        }
        return PageRequest.of((parameters.getStart() / parameters.getLength()), parameters.getLength(), Sort.by(orderList));
    }

    public DataTableResult<T> getDataTableResult(Page<T> objectPage) {
        DataTableResult<T> result = new DataTableResult<>();
        result.setDraw(Integer.toString(parameters.getDraw()));
        result.setData(objectPage.getContent());

        if (objectPage.hasContent()) {
            result.setRecordsTotal(Long.toString(objectPage.getNumberOfElements()));
            result.setRecordsFiltered(Long.toString(objectPage.getTotalElements()));
        } else {
            result.setRecordsTotal("0");
            result.setRecordsFiltered("0");
        }

        return result;
    }
}
