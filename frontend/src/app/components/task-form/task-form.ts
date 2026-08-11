import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TaskService } from '../../services/task';
import { UserService, User } from '../../services/user';

@Component({
  selector: 'app-task-form',
  imports: [ReactiveFormsModule],
  templateUrl: './task-form.html',
  styleUrl: './task-form.css',
})
export class TaskForm implements OnInit {
  form: FormGroup;
  private taskId: number | null = null;
  protected readonly users = signal<User[]>([]);

  constructor(
    private fb: FormBuilder,
    private taskService: TaskService,
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.form = this.fb.group({
      title: ['', Validators.required],
      dueDate: ['', Validators.required],
      completed: [false],
      userId: [''],
    });
  }

  ngOnInit(): void {
    this.userService.getAll().subscribe((data) => this.users.set(data));

    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.taskId = Number(idParam);
      this.taskService.getById(this.taskId).subscribe((task) => {
        this.form.patchValue({
          title: task.title,
          dueDate: task.dueDate,
          completed: task.completed,
          userId: task.user?.id ?? '',
        });
      });
    }
  }

  onSubmit(): void {
    if (this.form.invalid) {
      return;
    }

    const { userId, ...rest } = this.form.value;
    const payload = {
      ...rest,
      user: userId ? { id: Number(userId) } : null,
    };

    if (this.taskId) {
      this.taskService.update({ id: this.taskId, ...payload }).subscribe(() => {
        this.router.navigate(['/']);
      });
    } else {
      this.taskService.create(payload).subscribe(() => {
        this.form.reset({ completed: false });
        this.router.navigate(['/']);
      });
    }
  }
}