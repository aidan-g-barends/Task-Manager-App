import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TaskService } from '../../services/task';

@Component({
  selector: 'app-task-form',
  imports: [ReactiveFormsModule],
  templateUrl: './task-form.html',
  styleUrl: './task-form.css',
})
export class TaskForm implements OnInit {
  form: FormGroup;
  private taskId: number | null = null;

  constructor(
    private fb: FormBuilder,
    private taskService: TaskService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.form = this.fb.group({
      title: ['', Validators.required],
      dueDate: ['', Validators.required],
      completed: [false],
    });
  }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.taskId = Number(idParam);
      this.taskService.getById(this.taskId).subscribe((task) => {
        this.form.patchValue({
          title: task.title,
          dueDate: task.dueDate,
          completed: task.completed,
        });
      });
    }
  }

  onSubmit(): void {
    if (this.form.invalid) {
      return;
    }

    if (this.taskId) {
      this.taskService.update({ id: this.taskId, ...this.form.value }).subscribe(() => {
        this.router.navigate(['/']);
      });
    } else {
      this.taskService.create(this.form.value).subscribe(() => {
        this.form.reset({ completed: false });
        this.router.navigate(['/']);
      });
    }
  }
}