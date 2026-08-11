import { Routes } from '@angular/router';
import { TaskList } from './components/task-list/task-list';
import { TaskForm } from './components/task-form/task-form';
import { TaskDetail } from './components/task-detail/task-detail';
import { UserList } from './components/user-list/user-list';
import { UserForm } from './components/user-form/user-form';

export const routes: Routes = [
  { path: '', component: TaskList },
  { path: 'completed', component: TaskList, data: { completedOnly: true } },
  { path: 'tasks/new', component: TaskForm },
  { path: 'tasks/:id/edit', component: TaskForm },
  { path: 'tasks/:id', component: TaskDetail },
  { path: 'users', component: UserList },
  { path: 'users/new', component: UserForm },
  { path: 'users/:id/edit', component: UserForm },
];