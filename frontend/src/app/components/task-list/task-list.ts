import { Component, OnInit, signal } from '@angular/core';
import { TaskService, Task } from '../../services/task';

@Component({
  selector: 'app-task-list',
  imports: [],
  templateUrl: './task-list.html',
  styleUrl: './task-list.css',
})
export class TaskList implements OnInit {
  protected readonly tasks = signal<Task[]>([]);

  constructor(private taskService: TaskService) {}

  ngOnInit(): void {
    this.refresh();
  }

  refresh(): void {
    this.taskService.getAll().subscribe((data) => {
      this.tasks.set(data);
    });
  }
}