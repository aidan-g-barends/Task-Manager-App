import { Routes } from '@angular/router';
import { TaskList } from './components/task-list/task-list';
import { TaskForm } from './components/task-form/task-form';
import { UserList } from './components/user-list/user-list';
import { Login } from './components/login/login';

export const routes: Routes = [
  { path: 'login', component: Login },
  { path: '', component: TaskList },
  { path: 'completed', component: TaskList, data: { completedOnly: true } },
  { path: 'tasks/new', component: TaskForm },
  { path: 'users', component: UserList },
];