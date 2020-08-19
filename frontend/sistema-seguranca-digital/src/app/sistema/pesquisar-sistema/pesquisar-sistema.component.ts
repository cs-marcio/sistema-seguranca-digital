import { Component, OnInit, ViewChild } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { ResponseApi } from 'src/app/models/response-api';
import { DataTablesResponse } from 'src/app/models/dataTablesResponse';
import { Sistema } from 'src/app/models/sistema';
import { SistemaService } from 'src/app/services/sistema.service';
import { DataTableDirective } from 'angular-datatables';

@Component({
  selector: 'app-pesquisar-sistema',
  templateUrl: './pesquisar-sistema.component.html',
  styleUrls: ['./pesquisar-sistema.component.css']
})
export class PesquisarSistemaComponent implements OnInit {
  @ViewChild(DataTableDirective, {static: false})
  datatableElement: DataTableDirective;
  private sistemas: Sistema[];
  dtOptions: DataTables.Settings = {};
  pesquisaSistemaForm: FormGroup;
  submitted = false;
  error = '';

  loading = false;

  constructor(
    private formBuilder: FormBuilder,
    private sistemaService: SistemaService
  ) {}

  ngOnInit(): void {
    this.createForm();
  }

  onSubmit() {
    this.dtOptions = {
      pagingType: "simple_numbers",
      responsive: true,
      serverSide: true,
      processing: true,
      autoWidth: false,
      searching: false,
      ordering: false,
      pageLength: 50,
      language: {
        url:
          "https://cdn.datatables.net/plug-ins/1.10.19/i18n/Portuguese-Brasil.json"
      },
      ajax: (dataTablesParameters: any, callback) => {
        this.sistemaService
          .findListPaginated(dataTablesParameters)
          .subscribe((responseApi: ResponseApi) => {
            const resp: DataTablesResponse = responseApi.data;
            this.sistemas = resp.data;
            console.log(this.sistemas);
            callback({
              recordsTotal: resp.recordsTotal,
              recordsFiltered: resp.recordsFiltered,
              data: resp.data
            });
          });
      },
      columns: [
        {
          data: "descricao",
          title: "Descrição",
          searchable: true
        },
        {
          data: "sigla",
          title: "Sigla",
          searchable: true
        },
        {
          data: "email",
          title: "E-mail de atendimento",
          searchable: true
        },
        {
          data: "url",
          title: "URL",
          searchable: false
        }
      ],
      initComplete: () => {
        this.datatableElement.dtInstance.then(
          (dtInstance: DataTables.Api) => {
            if (this.sistemas != null && this.sistemas.length <= 50) {
              this.dtOptions.paging = false;
              dtInstance.destroy();
              //dtInstance.draw();
            }
          }
        );
      }
    };
    this.submitted = true;
  }

  search(){
    
  }

  createForm(){
    this.pesquisaSistemaForm = this.formBuilder.group({
      descricao: [''],
      sigla: [''],
      email: ['']
    });
  }

  clearForm(){
    this.createForm();
  }

  get f() { return this.pesquisaSistemaForm.controls; }
}
