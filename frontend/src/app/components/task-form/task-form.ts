import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TaskService } from '../../services/task';

@Component({
  selector: 'app-task-form',
  imports: [ReactiveFormsModule],
  templateUrl: './task-form.html',
  styleUrl: './task-form.css',
})
export class TaskForm {
  form: FormGroup;

  constructor(private fb: FormBuilder, private taskService: TaskService, private router: Router) {
    this.form = this.fb.group({
      title: ['', Validators.required],
      dueDate: ['', Validators.required],
      completed: [false],
    });
  }

  onSubmit(): void {
    if (this.form.invalid) {
      return;
    }

    this.taskService.create(this.form.value).subscribe(() => {
      this.form.reset({ completed: false });
      this.router.navigate(['/']);
    });
  }
}