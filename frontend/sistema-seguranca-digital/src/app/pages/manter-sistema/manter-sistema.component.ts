import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';

@Component({
  selector: 'app-manter-sistema',
  templateUrl: './manter-sistema.component.html',
  styleUrls: ['./manter-sistema.component.css']
})
export class ManterSistemaComponent implements OnInit {
  manterSistemaForm: FormGroup;
  msControls: any;
  submitted = false;

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit(): void {
    this.manterSistemaForm = this.formBuilder.group({
      email: ['', Validators.required]
    });

    this.msControls = this.manterSistemaForm.controls;
  }

  onSubmit() {}

}
