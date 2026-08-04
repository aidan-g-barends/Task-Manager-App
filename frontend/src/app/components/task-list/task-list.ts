import { Component, signal } from '@angular/core';

@Component({
  selector: 'app-task-list',
  imports: [],
  templateUrl: './task-list.html',
  styleUrl: './task-list.css',
})
export class TaskList {
  protected readonly tasks = signal([
    { title: 'Learn Angular basics', completed: false },
    { title: 'Connect frontend to backend', completed: false },
    { title: 'Build task list UI', completed: true },
  ]);
}