import { Component, OnInit, signal, computed } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TaskService, Task } from '../../services/task';
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

  protected readonly tasks = computed(() =>
    this.completedOnly()
      ? this.allTasks().filter((t) => t.completed)
      : this.allTasks()
  );

  protected readonly heading = computed(() =>
    this.completedOnly() ? 'Completed Tasks' : 'All Tasks'
  );

  constructor(private taskService: TaskService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.completedOnly.set(this.route.snapshot.data['completedOnly'] === true);
    this.refresh();
  }

  refresh(): void {
    this.taskService.getAll().subscribe((data) => {
      this.allTasks.set(data);
    });
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