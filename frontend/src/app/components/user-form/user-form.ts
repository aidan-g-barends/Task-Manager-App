import { Component, OnInit, signal } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../services/user';

@Component({
  selector: 'app-user-form',
  imports: [ReactiveFormsModule],
  templateUrl: './user-form.html',
  styleUrl: './user-form.css',
})
export class UserForm implements OnInit {
  form: FormGroup;
  private userId: number | null = null;
  protected readonly isEditMode = signal(false);

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute
  ) {
    this.form = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: [''],
    });
  }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.userId = Number(idParam);
      this.isEditMode.set(true);
      this.userService.getById(this.userId).subscribe((user) => {
        this.form.patchValue({
          name: user.name,
          email: user.email,
        });
      });
    } else {
      this.form.get('password')?.setValidators(Validators.required);
      this.form.get('password')?.updateValueAndValidity();
    }
  }

  onSubmit(): void {
    if (this.form.invalid) {
      return;
    }

    if (this.userId) {
      const { password, ...rest } = this.form.value;
      this.userService.update({ id: this.userId, ...rest }).subscribe(() => {
        this.router.navigate(['/users']);
      });
    } else {
      this.userService.create(this.form.value).subscribe(() => {
        this.form.reset();
        this.router.navigate(['/users']);
      });
    }
  }
}