import { Component, signal, ViewChild } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { TaskList } from './components/task-list/task-list';
import { TaskForm } from './components/task-form/task-form';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, TaskList, TaskForm],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('frontend');

  @ViewChild(TaskList) taskList!: TaskList;

  onTaskCreated(): void {
    this.taskList.refresh();
  }
}