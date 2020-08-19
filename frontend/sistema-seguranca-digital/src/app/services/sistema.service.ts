import { Injectable } from '@angular/core';
import { URL_API } from '../commons';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SistemaService {

  constructor(private http: HttpClient) { }

  findListPaginated(dataTablesParameters: any){
    return this.http.post(URL_API + "/sistema/all-paginated", dataTablesParameters);
  }
}
