import { Routes } from '@angular/router';
import { TaskList } from './components/task-list/task-list';
import { TaskForm } from './components/task-form/task-form';
import { TaskDetail } from './components/task-detail/task-detail';
import { UserList } from './components/user-list/user-list';
import { UserForm } from './components/user-form/user-form';
import { Login } from './components/login/login';
import { authGuard } from './auth-guard';

export const routes: Routes = [
  { path: 'login', component: Login },
  { path: '', component: TaskList, canActivate: [authGuard] },
  { path: 'completed', component: TaskList, data: { completedOnly: true }, canActivate: [authGuard] },
  { path: 'my-tasks', component: TaskList, data: { myTasksOnly: true }, canActivate: [authGuard] },
  { path: 'tasks/new', component: TaskForm, canActivate: [authGuard] },
  { path: 'tasks/:id', component: TaskDetail, canActivate: [authGuard] },
  { path: 'tasks/:id/edit', component: TaskForm, canActivate: [authGuard] },
  { path: 'users', component: UserList, canActivate: [authGuard] },
  { path: 'users/new', component: UserForm, canActivate: [authGuard] },
  { path: 'users/:id/edit', component: UserForm, canActivate: [authGuard] },
];


//Aidan is the goat