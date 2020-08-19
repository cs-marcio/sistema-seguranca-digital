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
  @ViewChild(DataTableDirective, { static: false })
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
  ) { }

  ngOnInit(): void {
    this.createForm();
  }

  onSubmit() {
    if (!this.submitted) {
      this.loading = true;
      this.search();
    }
  }

  search() {
    this.submitted = true;

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
      ajax: (dataTablesParameters: DataTables.AjaxDataRequest, callback) => {
        dataTablesParameters.columns.forEach(
          (column: DataTables.AjaxDataRequestColumn) => {
            switch (column.data) {
              case "descricao":
                if (this.f.descricao.value != "") {
                  console.log(column.data);
                  column.search.regex = true;
                  column.search.value = this.f.descricao.value;
                }
                break;
              case "sigla":
                if (this.f.sigla.value != "") {
                  console.log(column.data);
                  column.search.regex = true;
                  column.search.value = this.f.sigla.value;
                }
                break;
              case "email":
                if (this.f.email.value != "") {
                  console.log(column.data);
                  column.search.regex = true;
                  column.search.value = this.f.email.value;
                }
                break;
            }
          }
        );

        this.sistemaService
          .findListPaginated(dataTablesParameters)
          .subscribe((responseApi: ResponseApi) => {
            const resp: DataTablesResponse = responseApi.data;
            this.sistemas = resp.data;
            callback({
              recordsTotal: resp.recordsTotal,
              recordsFiltered: resp.recordsFiltered,
              data: resp.data
            });

            this.loading = false;
          });
      },
      columns: [
        {
          data: "descricao",
          title: "Descrição"
        },
        {
          data: "sigla",
          title: "Sigla"
        },
        {
          data: "email",
          title: "E-mail de atendimento"
        },
        {
          data: "url",
          title: "URL"
        },
        {
          data: "status",
          title: "Status",
        }
      ],
      initComplete: () => {
        this.datatableElement.dtInstance.then(
          (dtInstance: DataTables.Api) => {
            if (this.sistemas != null && this.sistemas.length <= 50) {
              this.dtOptions.paging = false;
              dtInstance.destroy();
            }
          }
        );
      }
    };
  }

  createForm() {
    this.pesquisaSistemaForm = this.formBuilder.group({
      descricao: [''],
      sigla: [''],
      email: ['']
    });
  }

  clearForm() {
    this.submitted = false;
    this.createForm();
  }

  get f() { return this.pesquisaSistemaForm.controls; }
}
