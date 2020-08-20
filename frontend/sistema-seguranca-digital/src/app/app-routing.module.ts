import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { AuthGuard } from './auth/auth.guard';
import { LoginComponent } from './auth/login/login.component';
import { PesquisarSistemaComponent } from './pages/sistema/pesquisar-sistema/pesquisar-sistema.component';
import { CadastrarSistemaComponent } from './pages/sistema/cadastrar-sistema/cadastrar-sistema.component';


const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'pesquisar-sistema', component: PesquisarSistemaComponent, canActivate: [AuthGuard] },
  { path: 'cadastrar-sistema', component: CadastrarSistemaComponent, canActivate: [AuthGuard] },
  // { path: 'manter-sistema', component: ManterSistemaComponent },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
