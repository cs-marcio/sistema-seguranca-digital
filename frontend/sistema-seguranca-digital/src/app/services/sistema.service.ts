import { Injectable } from '@angular/core';
import { URL_API } from '../commons';
import { HttpClient } from '@angular/common/http';
import { Sistema } from '../models/sistema';

@Injectable({
  providedIn: 'root'
})
export class SistemaService {

  constructor(private http: HttpClient) { }

  findListPaginated(dataTablesParameters: any){
    return this.http.post(URL_API + "/sistema/all-paginated", dataTablesParameters);
  }

  setSistema(sistema: Sistema) {
    return this.http.post(`${URL_API}/sistema/`, sistema);
  }
}
