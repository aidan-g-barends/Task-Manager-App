import { Component, OnInit, signal } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { TaskService, Task } from '../../services/task';

@Component({
  selector: 'app-task-detail',
  imports: [RouterLink],
  templateUrl: './task-detail.html',
  styleUrl: './task-detail.css',
})
export class TaskDetail implements OnInit {
  protected readonly task = signal<Task | null>(null);

  constructor(
    private taskService: TaskService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.taskService.getById(id).subscribe((data) => {
      this.task.set(data);
    });
  }

  deleteTask(): void {
    const current = this.task();
    if (!current) return;

    this.taskService.delete(current.id).subscribe(() => {
      this.router.navigate(['/']);
    });
  }
}