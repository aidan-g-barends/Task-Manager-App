import { Component, signal, computed, OnInit } from '@angular/core';
import { RouterOutlet, RouterLink, RouterLinkActive, Router, NavigationEnd } from '@angular/router';
import { TaskService, Task } from './services/task';

interface TaskNotification {
  task: Task;
  label: string;
}

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
  protected readonly isAuthPage = signal(false);

  private readonly daysAhead = 3;

  protected readonly notifications = computed<TaskNotification[]>(() => {
    const today = new Date();
    today.setHours(0, 0, 0, 0);

    return this.allTasks()
      .filter((t) => !t.completed)
      .map((t) => {
        const due = new Date(t.dueDate);
        due.setHours(0, 0, 0, 0);
        const diffDays = Math.round((due.getTime() - today.getTime()) / (1000 * 60 * 60 * 24));
        return { task: t, diffDays };
      })
      .filter((t) => t.diffDays <= this.daysAhead)
      .sort((a, b) => a.diffDays - b.diffDays)
      .map(({ task, diffDays }) => ({
        task,
        label: this.formatDueLabel(diffDays),
      }));
  });

  constructor(private taskService: TaskService, private router: Router) {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        this.isAuthPage.set(event.urlAfterRedirects.startsWith('/login'));
      }
    });
  }

  ngOnInit(): void {
    this.taskService.getAll().subscribe((data) => this.allTasks.set(data));
  }

  toggleNotifications(): void {
    this.showNotifications.update((v) => !v);
  }

  private formatDueLabel(diffDays: number): string {
    if (diffDays < 0) {
      return `Overdue by ${Math.abs(diffDays)} day${Math.abs(diffDays) === 1 ? '' : 's'}`;
    }
    if (diffDays === 0) {
      return 'Due today';
    }
    return `Due in ${diffDays} day${diffDays === 1 ? '' : 's'}`;
  }
}