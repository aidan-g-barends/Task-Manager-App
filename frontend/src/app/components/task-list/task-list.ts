import { Component, OnInit, signal, computed } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TaskService, Task } from '../../services/task';
import { AuthService } from '../../services/auth';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-task-list',
  imports: [RouterLink],
  templateUrl: './task-list.html',
  styleUrl: './task-list.css',
})
export class TaskList implements OnInit {
  private readonly allTasks = signal<Task[]>([]);
  private readonly completedOnly = signal<boolean>(false);
  private readonly myTasksOnly = signal<boolean>(false);
  protected readonly searchTerm = signal<string>('');

  protected readonly tasks = computed(() => {
    let base = this.allTasks();

    if (this.completedOnly()) {
      base = base.filter((t) => t.completed);
    }

    if (this.myTasksOnly()) {
      const currentUser = this.authService.user();
      base = base.filter((t) => t.user?.id === currentUser?.id);
    }

    const term = this.searchTerm().toLowerCase().trim();
    if (!term) return base;

    return base.filter((t) => t.title.toLowerCase().includes(term));
  });

  protected readonly heading = computed(() => {
    if (this.myTasksOnly()) return 'My Tasks';
    if (this.completedOnly()) return 'Completed Tasks';
    return 'All Tasks';
  });

  constructor(
    private taskService: TaskService,
    private route: ActivatedRoute,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.completedOnly.set(this.route.snapshot.data['completedOnly'] === true);
    this.myTasksOnly.set(this.route.snapshot.data['myTasksOnly'] === true);
    this.refresh();
  }

  refresh(): void {
    this.taskService.getAll().subscribe((data) => {
      this.allTasks.set(data);
    });
  }

  onSearchChange(value: string): void {
    this.searchTerm.set(value);
  }

  toggleComplete(task: Task): void {
    this.taskService.update({ ...task, completed: !task.completed }).subscribe(() => {
      this.refresh();
    });
  }

  deleteTask(id: number): void {
    this.taskService.delete(id).subscribe(() => {
      this.refresh();
    });
  }
}