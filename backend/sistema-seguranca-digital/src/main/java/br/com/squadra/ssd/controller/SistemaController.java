package br.com.squadra.ssd.controller;

import br.com.squadra.ssd.datatables.DataBuilder;
import br.com.squadra.ssd.datatables.DataTableResult;
import br.com.squadra.ssd.datatables.DataTablesParameters;
import br.com.squadra.ssd.model.Sistema;
import br.com.squadra.ssd.repository.SistemaRepository;
import br.com.squadra.ssd.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/sistema")
public class SistemaController {

    @Autowired
    SistemaRepository repository;

    @GetMapping("all")
    public List<Sistema> getSistemas() {
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Sistema>> create(HttpServletRequest request, @RequestBody Sistema sistema, BindingResult result) {
        ApiResponse<Sistema> response = new ApiResponse<>();

        try {
            if (result.hasErrors()) {
                result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
                return ResponseEntity.badRequest().body(response);
            }

            List<Sistema> sistemaVerify = repository.findAll(Example.of(sistema));

            for (Sistema s: sistemaVerify){
                if (s.compareTo(sistema) == 0){
                    response.getErrors().add("Registro pré-existente");
                    return ResponseEntity.badRequest().body(response);
                }
            }

            Sistema sistemaPersisted = repository.save(sistema);
            response.setData(sistemaPersisted);
        }catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("all-paginated")
    public ResponseEntity<ApiResponse<DataTableResult<Sistema>>> getSistamas(@RequestBody DataTablesParameters parameters) {
        ApiResponse<DataTableResult<Sistema>> apiResponse = new ApiResponse<>();
        DataBuilder<Sistema> dataBuilder = new DataBuilder<>(new Sistema(), parameters);

        try {
            Example<Sistema> sistemaExample = dataBuilder.getExample("url", "id", "status");
            if (sistemaExample == null)
                sistemaExample = Example.of(new Sistema());
            Page<Sistema> sistemasPage = repository.findAll(sistemaExample, dataBuilder.getPageRequest());
            apiResponse.setData(dataBuilder.getDataTableResult(sistemasPage));
        } catch (Exception e) {
            apiResponse.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(apiResponse);
        }

        return ResponseEntity.ok(apiResponse);
    }
}
