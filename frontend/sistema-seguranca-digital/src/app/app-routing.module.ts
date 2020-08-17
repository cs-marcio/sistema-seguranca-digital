import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { AuthGuard } from './auth/auth.guard';
import { LoginComponent } from './pages/login/login.component';
import { ManterSistemaComponent } from './pages/manter-sistema/manter-sistema.component';
import { PesquisarSistemaComponent } from './pages/pesquisar-sistema/pesquisar-sistema.component';


const routes: Routes = [
  { path: '', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'pesquisar-sistema', component: PesquisarSistemaComponent },
  // { path: 'manter-sistema', component: ManterSistemaComponent },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
