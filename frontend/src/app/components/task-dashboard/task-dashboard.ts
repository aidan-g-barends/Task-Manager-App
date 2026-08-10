import { Component, ViewChild } from '@angular/core';
import { TaskList } from '../task-list/task-list';
import { TaskForm } from '../task-form/task-form';

@Component({
  selector: 'app-task-dashboard',
  imports: [TaskList, TaskForm],
  templateUrl: './task-dashboard.html',
  styleUrl: './task-dashboard.css',
})
export class TaskDashboard {
  @ViewChild(TaskList) taskList!: TaskList;

  onTaskCreated(): void {
    this.taskList.refresh();
  }
}