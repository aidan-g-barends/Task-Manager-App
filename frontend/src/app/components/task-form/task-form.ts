import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { TaskService } from '../../services/task';

@Component({
  selector: 'app-task-form',
  imports: [ReactiveFormsModule],
  templateUrl: './task-form.html',
  styleUrl: './task-form.css',
})
export class TaskForm {
  @Output() taskCreated = new EventEmitter<void>();

  form: FormGroup;

  constructor(private fb: FormBuilder, private taskService: TaskService) {
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
      this.taskCreated.emit();
    });
  }
}