import { Component, OnInit } from '@angular/core';
import { Sistema } from 'src/app/models/sistema';
import { SistemaService } from 'src/app/services/sistema.service';
import { ResponseApi } from 'src/app/models/response-api';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-cadastrar-sistema',
  templateUrl: './cadastrar-sistema.component.html',
  styleUrls: ['./cadastrar-sistema.component.css']
})
export class CadastrarSistemaComponent implements OnInit {
  cadastrarSistemaForm: FormGroup;
  submitted = false;
  error = '';
  hasError = false;
  loading = false;

  constructor(private sistemaService: SistemaService, private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.createForm();
  }

  onSubmit() {
    this.loading = true;
    this.f.status.setValue('ATIVO');
    this.sistemaService.setSistema(this.cadastrarSistemaForm.value).subscribe(
      (responseApi: ResponseApi) => {
        const resp: Sistema = responseApi.data;
        this.loading = false;
        this.clearForm();
      },
      err => {
        this.submitted = false;
        this.loading = false;
        this.clearForm();
      }
    );
  }

  createForm() {
    this.cadastrarSistemaForm = this.formBuilder.group({
      descricao: [
        '',
        Validators.compose([
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(100)
        ])
      ],
      sigla: [
        '',
        Validators.compose([
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(10)
        ])
      ],
      email: [
        '',
        Validators.compose([
          Validators.required,
          Validators.email,
          Validators.minLength(1),
          Validators.maxLength(100)
        ])
      ],
      url: [
        '',
        Validators.compose([
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(50)
        ])
      ],
      status: ['ATIVO']
    });
  }

  clearForm() {
    this.cadastrarSistemaForm.reset();
    this.loading = false;
    this.submitted = false;
  }

  get f() { return this.cadastrarSistemaForm.controls; }

}
