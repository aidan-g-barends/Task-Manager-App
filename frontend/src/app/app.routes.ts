import { Routes } from '@angular/router';
import { TaskList } from './components/task-list/task-list';
import { TaskForm } from './components/task-form/task-form';
import { UserList } from './components/user-list/user-list';

export const routes: Routes = [
  { path: '', component: TaskList },
  { path: 'completed', component: TaskList, data: { completedOnly: true } },
  { path: 'tasks/new', component: TaskForm },
  { path: 'users', component: UserList },
];