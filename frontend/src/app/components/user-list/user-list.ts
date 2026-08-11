import { Component, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { UserService, User } from '../../services/user';

@Component({
  selector: 'app-user-list',
  imports: [RouterLink],
  templateUrl: './user-list.html',
  styleUrl: './user-list.css',
})
export class UserList implements OnInit {
  protected readonly users = signal<User[]>([]);

  constructor(private userService: UserService) {}

  ngOnInit(): void {
    this.refresh();
  }

  refresh(): void {
    this.userService.getAll().subscribe((data) => {
      this.users.set(data);
    });
  }

  deleteUser(id: number): void {
    this.userService.delete(id).subscribe(() => {
      this.refresh();
    });
  }
}