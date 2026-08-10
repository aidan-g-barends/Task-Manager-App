import { Routes } from '@angular/router';
import { TaskDashboard } from './components/task-dashboard/task-dashboard';
import { UserList } from './components/user-list/user-list';

export const routes: Routes = [
  { path: '', component: TaskDashboard },
  { path: 'users', component: UserList },
];