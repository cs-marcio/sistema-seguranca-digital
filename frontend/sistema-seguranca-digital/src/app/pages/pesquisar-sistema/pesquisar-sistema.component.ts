import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-pesquisar-sistema',
  templateUrl: './pesquisar-sistema.component.html',
  styleUrls: ['./pesquisar-sistema.component.css']
})
export class PesquisarSistemaComponent implements OnInit {
  manterSistemaForm: FormGroup;
  msControls: any;
  submitted = false;

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.manterSistemaForm = this.formBuilder.group({
      descricao: ['', Validators.required],
      sigla: ['', Validators.required],
      email: ['', Validators.required],
      url: ['', Validators.required]
    });

    this.msControls = this.manterSistemaForm.controls;
  }

  onSubmit() {}

}
