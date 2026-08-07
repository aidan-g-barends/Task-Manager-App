import { Component, signal, computed, OnInit } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive } from '@angular/router';
import { TaskService, Task } from './services/task';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterLink, RouterLinkActive],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  protected readonly title = signal('frontend');

  private readonly allTasks = signal<Task[]>([]);
  protected readonly showNotifications = signal(false);

  protected readonly dueOrOverdue = computed(() => {
    const today = new Date().toISOString().split('T')[0];
    return this.allTasks().filter((t) => !t.completed && t.dueDate <= today);
  });

  constructor(private taskService: TaskService) {}

  ngOnInit(): void {
    this.taskService.getAll().subscribe((data) => this.allTasks.set(data));
  }

  toggleNotifications(): void {
    this.showNotifications.update((v) => !v);
  }
}